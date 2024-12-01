package doubledrg.bbinside.domain.posts.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doubledrg.bbinside.domain.posts.dao.PostDao;
import doubledrg.bbinside.domain.posts.dto.PostDetailDto;
import doubledrg.bbinside.domain.posts.dto.PostLimitOffset;
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
        String postId = request.getParameter("id");

        try
        {
            if (postId == null)
            {
                handleShowRecentPosts(readRequestBody(request), response);
            }
            else
            {
                long id = Long.parseLong(postId);
                handlePostsWithUserId(id, response);
            }
        }
        catch (Exception e)
        {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void handlePostsWithUserId(Long id, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException
    {
        List<PostDetailDto> posts = postService.showPostsWithUserId(id);
        sendPosts(response, posts.toString());
    }

    private void handleShowRecentPosts(String requestBody, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException
    {
        PostLimitOffset range = objectMapper.readValue(requestBody, PostLimitOffset.class);
        List<PostDetailDto> posts = postService.showRecentPosts(range.getLimit(), range.getOffset());

        sendPosts(response, posts.toString());
    }

    private void sendPosts(HttpServletResponse response, String posts) throws IOException
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
