package doubledrg.bbinside.domain.posts.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDetailDto
{
    private Long id;
    private String title;
    private String username;
    private String content;
    private Long userId;
    private Timestamp createdAt;

    public PostDetailDto(Long id, String title, String username, String content, Long userId, Timestamp createdAt)
    {
        this.id = id;
        this.title = title;
        this.username = username;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
