package doubledrg.bbinside.domain.comments.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentSaveDto
{
    private String content;
    private Long postId;
    private Long userId;
    private Timestamp createdAt;

    public CommentSaveDto(String content, Long postId, Long userId, Timestamp createdAt)
    {
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
