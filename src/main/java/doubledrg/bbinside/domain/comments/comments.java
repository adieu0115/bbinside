package doubledrg.bbinside.domain.comments;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class comments
{
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private Timestamp createdAt;
}
