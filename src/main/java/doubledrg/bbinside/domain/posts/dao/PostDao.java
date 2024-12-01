package doubledrg.bbinside.domain.posts.dao;

import doubledrg.bbinside.config.ConnectionMaker;
import doubledrg.bbinside.domain.posts.Post;
import doubledrg.bbinside.domain.posts.dto.PostDetailDto;
import doubledrg.bbinside.domain.posts.dto.PostSaveDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao
{
    private final ConnectionMaker connectionMaker = new ConnectionMaker();

    public Long save(PostSaveDto dto) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "INSERT INTO POSTS(TITLE,CONTENT,USER_ID,CREATED_AT)" +
                        " VALUES(?,?,?,?)";

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
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

    public PostDetailDto findByPostId(Long id) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT" +
                        " P.id," +
                        " P.title," +
                        " P.content," +
                        " P.user_id," +
                        " P.created_at," +
                        " U.username" +
                        " FROM POSTS AS P,USERS AS U" +
                        " WHERE P.USER_ID=U.ID" +
                        " AND P.ID = ?";

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);)
        {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
                PostDetailDto post = null;
                if (rs.next())
                {
                    Long postId = rs.getLong("ID");
                    String title = rs.getString("TITLE");
                    String content = rs.getString("CONTENT");
                    Long userId = rs.getLong("USER_ID");
                    Timestamp createdAt = rs.getTimestamp("CREATED_AT");
                    String username = rs.getString("USERNAME");

                    post = new PostDetailDto(postId, title, username, content, userId, createdAt);
                }
                return post;
            }
        }
    }

    public List<PostDetailDto> findRecentPostList(Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT" +
                        " P.id," +
                        " P.title," +
                        " P.content," +
                        " P.user_id," +
                        " P.created_at," +
                        " U.username" +
                        " FROM POSTS AS P,USERS AS U" +
                        " WHERE P.USER_ID=U.ID" +
                        " ORDER BY CREATED_AT DESC" +
                        " LIMIT ? OFFSET ?";
        List<PostDetailDto> posts = new ArrayList<>();

        try (Connection conn = connectionMaker.makeConnection();
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
                    String content = rs.getString("CONTENT");
                    Long userId = rs.getLong("USER_ID");
                    Timestamp createdAt = rs.getTimestamp("CREATED_AT");
                    String username = rs.getString("USERNAME");

                    posts.add(new PostDetailDto(postId, title, username, content, userId, createdAt));
                }
            }
        }
        return posts;
    }

    public List<PostDetailDto> findRecentPostListWithUserId(Long id, Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT" +
                        " P.id," +
                        " P.title," +
                        " P.content," +
                        " P.user_id," +
                        " P.created_at," +
                        " U.username" +
                        " FROM POSTS AS P,USERS AS U" +
                        " WHERE P.USER_ID=U.ID" +
                        " AND P.USER_ID = ?" +
                        " ORDER BY CREATED_AT DESC" +
                        " LIMIT ? OFFSET ?";
        List<PostDetailDto> posts = new ArrayList<>();

        try (Connection conn = connectionMaker.makeConnection();
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
                    String content = rs.getString("CONTENT");
                    Long userId = rs.getLong("USER_ID");
                    Timestamp createdAt = rs.getTimestamp("CREATED_AT");

                    posts.add(new PostDetailDto(postId, title, username, content, userId, createdAt));
                }
            }
        }
        return posts;
    }

    public List<Post> findRecentPostListWithTitle(String inputTitle) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT *" +
                        " FROM POSTS" +
                        " WHERE TITLE=?" +
                        " ORDER BY CREATED_AT DESC";
        List<Post> posts = new ArrayList<>();

        try (Connection conn = connectionMaker.makeConnection();
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
}
