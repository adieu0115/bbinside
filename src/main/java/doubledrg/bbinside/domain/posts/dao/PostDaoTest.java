package doubledrg.bbinside.domain.posts.dao;

import doubledrg.bbinside.domain.posts.dto.PostDetailDto;

import java.sql.SQLException;
import java.util.List;

public class PostDaoTest
{
    //Test
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        PostDao postDao = new PostDao();
        Long testUserId = 10L;

//        for (int i = 0; i < 10; i++)
//        {
//            String title = postDao.createRandomString(10);
//            String content = postDao.createRandomString(10);
//            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//
//            PostSaveDto dto = new PostSaveDto(title, content, testUserId, createdAt.toLocalDateTime());
//            postDao.save(dto);
//        }

//        List<PostDetailDto> posts = postDao.findByUserId(15L, 5L, 0L);

        List<PostDetailDto> posts = postDao.findRecentPostList(15L, 0L);
        System.out.println(posts);

    }

    public String createRandomString(int length)
    {
        String seed = " 123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append(seed.charAt((int) (Math.random() * (seed.length() - 1))));
        }
        return sb.toString();
    }
}
