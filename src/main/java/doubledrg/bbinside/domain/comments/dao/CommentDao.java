package doubledrg.bbinside.domain.comments.dao;

import doubledrg.bbinside.config.ConnectionMaker;
import doubledrg.bbinside.domain.comments.dto.CommentDetailDto;
import doubledrg.bbinside.domain.comments.dto.CommentSaveDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao
{
    private final ConnectionMaker connectionMaker = new ConnectionMaker();

    public Long save(CommentSaveDto dto) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "INSERT INTO COMMENTS(CONTENT,POST_ID,USER_ID,CREATED_AT)" +
                        " VALUES(?,?,?,?)";

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            pstmt.setString(1, dto.getContent());
            pstmt.setLong(2, dto.getPostId());
            pstmt.setLong(3, dto.getUserId());
            pstmt.setTimestamp(4, dto.getCreatedAt());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys())
            {
                Long savedId = null;
                if (rs.next())
                {
                    savedId = rs.getLong(1);
                }
                return savedId;
            }
        }
    }

    public List<CommentDetailDto> findByPostId(Long id) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT * FROM COMMENTS" +
                        " WHERE POST_ID = ?";

        List<CommentDetailDto> comments = new ArrayList<>();

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    Long commentId = rs.getLong("id");
                    String content = rs.getString("content");
                    Long postId = rs.getLong("post_id");
                    Long userId = rs.getLong("user_id");
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    comments.add(new CommentDetailDto(commentId, content, postId, userId, createdAt));
                }
                return comments;
            }
        }
    }

    public List<CommentDetailDto> findByUserId(Long id) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT * FROM COMMENTS" +
                        " WHERE USER_ID = ?";

        List<CommentDetailDto> comments = new ArrayList<>();

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    Long commentId = rs.getLong("id");
                    String content = rs.getString("content");
                    Long postId = rs.getLong("post_id");
                    Long userId = rs.getLong("user_id");
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    comments.add(new CommentDetailDto(commentId, content, postId, userId, createdAt));
                }
                return comments;
            }
        }
    }

}
