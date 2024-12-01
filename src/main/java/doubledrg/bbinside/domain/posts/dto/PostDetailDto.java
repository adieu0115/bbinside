package doubledrg.bbinside.domain.posts.dto;

import lombok.Data;
import lombok.Setter;

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
}
