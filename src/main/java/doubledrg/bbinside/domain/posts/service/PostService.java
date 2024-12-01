package doubledrg.bbinside.domain.posts.service;

import doubledrg.bbinside.domain.comments.dao.CommentDao;
import doubledrg.bbinside.domain.comments.dto.CommentDetailDto;
import doubledrg.bbinside.domain.posts.dao.PostDao;
import doubledrg.bbinside.domain.posts.dto.PostAndCommentsDto;
import doubledrg.bbinside.domain.posts.dto.PostDetailDto;
import doubledrg.bbinside.domain.posts.dto.PostSaveDto;

import java.sql.SQLException;
import java.util.List;

public class PostService
{
    private final PostDao postDao = new PostDao();
    private final CommentDao commentDao = new CommentDao();

    public Long savePost(PostSaveDto dto) throws SQLException, ClassNotFoundException
    {
        return postDao.save(dto);
    }

    public List<PostDetailDto> showRecentPostList(Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        return postDao.findRecentPostList(limit, offset);
    }

    public List<PostDetailDto> showRecentPostListWithUserId(Long userId, Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        return postDao.findRecentPostListWithUserId(userId, limit, offset);
    }

    public PostAndCommentsDto showPostDetail(Long postId) throws SQLException, ClassNotFoundException
    {
        //transaction begin
        PostDetailDto post = postDao.findByPostId(postId);
        List<CommentDetailDto> comments = commentDao.findByPostId(postId);
        //transaction end

        return new PostAndCommentsDto(post, comments);
    }

}
