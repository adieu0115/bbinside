package doubledrg.bbinside.domain.users.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import doubledrg.bbinside.domain.users.User;
import doubledrg.bbinside.domain.users.dto.UserLoginDto;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;
import doubledrg.bbinside.domain.users.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(urlPatterns = {"/users/*"})
public class UserServlet extends HttpServlet
{
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();

        try
        {
            String requestBody = readRequestBody(request);

            if ("/signup".equals(pathInfo))
            {
                handleSignup(requestBody, response);
            }
            else if ("/login".equals(pathInfo))
            {
                handleLogin(requestBody, response);
            }
            else
            {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
            }
        }
        catch (Exception e)
        {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void handleSignup(String requestBody, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException
    {
        User user = objectMapper.readValue(requestBody, User.class);
        UserSaveDto userSaveDto = new UserSaveDto(user.getUsername(), user.getPassword());

        userService.signup(userSaveDto);
        sendSuccessResponse(response, "Signup successful");
    }

    private void handleLogin(String requestBody, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException
    {
        User user = objectMapper.readValue(requestBody, User.class);
        UserLoginDto userLoginDto = new UserLoginDto(user.getUsername(), user.getPassword());

        userService.login(userLoginDto);
        sendSuccessResponse(response, "Login successful");
    }

    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException
    {
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getWriter(),
                Map.of("status", "success", "message", message));
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException
    {
        response.setStatus(status);
        objectMapper.writeValue(response.getWriter(), Map.of("status", "error", "message", message));
    }

    private String readRequestBody(HttpServletRequest request) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader())
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
