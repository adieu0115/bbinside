package doubledrg.bbinside.domain.posts.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import doubledrg.bbinside.domain.posts.dto.PostDetailDto;
import doubledrg.bbinside.domain.posts.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet
{
    private final PostService postService = new PostService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pathInfo = request.getPathInfo();

        try
        {
            if (pathInfo == null || "/".equals(pathInfo))
            {
                handleShowRecentPosts(request, response);
            }
            else if ("/user".equals(pathInfo))
            {
                handlePostsWithUserId(request, response);
            }
            else
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void handlePostsWithUserId(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException
    {
        long userId = Long.parseLong(request.getParameter("id"));
        long limit = Long.parseLong(request.getParameter("limit"));
        long offset = Long.parseLong(request.getParameter("offset"));
        List<PostDetailDto> posts = postService.showPostsWithUserId(userId, limit, offset);
        sendPosts(response, posts);
    }

    private void handleShowRecentPosts(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException
    {
        long limit = Long.parseLong(request.getParameter("limit"));
        long offset = Long.parseLong(request.getParameter("offset"));
        List<PostDetailDto> posts = postService.showRecentPosts(limit, offset);
        sendPosts(response, posts);
    }

    private void sendPosts(HttpServletResponse response, List<PostDetailDto> posts) throws IOException
    {
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getWriter(),
                Map.of("status", "success", "posts", posts));
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
