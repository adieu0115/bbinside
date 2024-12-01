package doubledrg.bbinside.domain.posts.dao;

import doubledrg.bbinside.domain.posts.Post;
import doubledrg.bbinside.domain.posts.dto.PostDetailDto;
import doubledrg.bbinside.domain.posts.dto.PostSaveDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao
{
    private Connection makeConnection() throws ClassNotFoundException, SQLException
    {
        final String url = "jdbc:mysql://localhost:3306/bbinside";
        final String username = "root";
        final String password = "endyd132!!";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    public Long save(PostSaveDto dto) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "INSERT INTO POSTS(TITLE,CONTENT,USER_ID,CREATED_AT)" +
                        " VALUES(?,?,?,?)";

        try (Connection conn = makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setLong(3, dto.getUserId());
            pstmt.setTimestamp(4, Timestamp.valueOf(dto.getCreatedAt()));
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys())
            {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    public List<PostDetailDto> findRecentPosts(Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT P.id, P.title, P.content, P.user_id, P.created_at, U.username" +
                        " FROM POSTS AS P,USERS AS U" +
                        " WHERE P.USER_ID=U.ID" +
                        " ORDER BY CREATED_AT DESC" +
                        " LIMIT ? OFFSET ?";
        List<PostDetailDto> posts = new ArrayList<>();

        try (Connection conn = makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);)
        {
            pstmt.setLong(1, limit);
            pstmt.setLong(2, offset);
            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    Long postId = rs.getLong("ID");
                    String title = rs.getString("TITLE");
                    String context = rs.getString("CONTENT");
                    Long userId = rs.getLong("USER_ID");
                    Timestamp createdAt = rs.getTimestamp("CREATED_AT");
                    String username = rs.getString("USERNAME");

                    PostDetailDto post = new PostDetailDto();
                    post.setId(postId);
                    post.setTitle(title);
                    post.setUsername(username);
                    post.setContent(context);
                    post.setUserId(userId);
                    post.setCreatedAt(createdAt);

                    posts.add(post);
                }
            }
        }
        return posts;
    }

    public List<PostDetailDto> findByUserId(Long id, Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT P.id, P.title, P.content, P.user_id, P.created_at, U.username" +
                        " FROM POSTS AS P,USERS AS U" +
                        " WHERE P.USER_ID=U.ID" +
                        " AND P.USER_ID = ?" +
                        " ORDER BY CREATED_AT DESC" +
                        " LIMIT ? OFFSET ?";
        List<PostDetailDto> posts = new ArrayList<>();

        try (Connection conn = makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);)
        {
            pstmt.setLong(1, id);
            pstmt.setLong(2, limit);
            pstmt.setLong(3, offset);
            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    Long postId = rs.getLong("ID");
                    String title = rs.getString("TITLE");
                    String username = rs.getString("USERNAME");
                    String context = rs.getString("CONTENT");
                    Long userId = rs.getLong("USER_ID");
                    Timestamp createdAt = rs.getTimestamp("CREATED_AT");

                    PostDetailDto post = new PostDetailDto();
                    post.setId(postId);
                    post.setTitle(title);
                    post.setUsername(username);
                    post.setContent(context);
                    post.setUserId(userId);
                    post.setCreatedAt(createdAt);

                    posts.add(post);
                }
            }
        }
        return posts;
    }

    public List<Post> findByTitle(String inputTitle) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT *" +
                        " FROM POSTS" +
                        " WHERE TITLE=?";
        List<Post> posts = new ArrayList<>();

        try (Connection conn = makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);)
        {
            pstmt.setString(1, inputTitle);
            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    Long postId = rs.getLong("ID");
                    String title = rs.getString("TITLE");
                    String context = rs.getString("CONTENT");
                    Long userId = rs.getLong("USER_ID");
                    Timestamp createdAt = rs.getTimestamp("CREATED_AT");

                    Post post = new Post();
                    post.setId(postId);
                    post.setTitle(title);
                    post.setContent(context);
                    post.setUserId(userId);
                    post.setCreatedAt(createdAt);

                    posts.add(post);
                }
            }
        }
        return posts;
    }

    //Test
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        PostDao postDao = new PostDao();
        Long testUserId = 10L;

//        for (int i = 0; i < 10; i++)
//        {
//            String title = postDao.createRandomString(10);
//            String content = postDao.createRandomString(10);
//            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//
//            PostSaveDto dto = new PostSaveDto(title, content, testUserId, createdAt.toLocalDateTime());
//            postDao.save(dto);
//        }

//        List<PostDetailDto> posts = postDao.findByUserId(15L, 5L, 0L);

        List<PostDetailDto> posts = postDao.findRecentPosts(15L, 0L);
        System.out.println(posts);

    }

    public String createRandomString(int length)
    {
        String seed = " 123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append(seed.charAt((int) (Math.random() * (seed.length() - 1))));
        }
        return sb.toString();
    }
}
