package doubledrg.bbinside.domain.posts.dto;

import doubledrg.bbinside.domain.comments.dto.CommentDetailDto;
import lombok.Data;

import java.util.List;

@Data
public class PostAndCommentsDto
{
    private PostDetailDto post;
    private List<CommentDetailDto> comments;

    public PostAndCommentsDto(PostDetailDto post, List<CommentDetailDto> comments)
    {
        this.post = post;
        this.comments = comments;
    }
}
