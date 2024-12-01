package doubledrg.bbinside.domain.posts.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSaveDto
{
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

    public PostSaveDto(String title, String content, Long userId, LocalDateTime createdAt)
    {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
