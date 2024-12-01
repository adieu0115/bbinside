package doubledrg.bbinside.domain.posts;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Post
{
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Timestamp createdAt;
}
