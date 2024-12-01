package doubledrg.bbinside.domain.comments.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDetailDto
{
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private Timestamp createdAt;

    public CommentDetailDto(Long id, String content, Long postId, Long userId, Timestamp createdAt)
    {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
