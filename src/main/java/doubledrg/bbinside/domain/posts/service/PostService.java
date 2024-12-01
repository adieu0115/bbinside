package doubledrg.bbinside.domain.posts.service;

import doubledrg.bbinside.domain.posts.dao.PostDao;
import doubledrg.bbinside.domain.posts.dto.PostDetailDto;
import doubledrg.bbinside.domain.posts.dto.PostSaveDto;

import java.sql.SQLException;
import java.util.List;

public class PostService
{
    private final PostDao postDao = new PostDao();

    public Long savePost(PostSaveDto dto) throws SQLException, ClassNotFoundException
    {
        return postDao.save(dto);
    }

    public List<PostDetailDto> showRecentPosts(Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        return postDao.findRecentPosts(limit, offset);
    }

    public List<PostDetailDto> showPostsWithUserId(Long userId, Long limit, Long offset) throws SQLException, ClassNotFoundException
    {
        return postDao.findByUserId(userId, limit, offset);
    }

}
