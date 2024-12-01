package doubledrg.bbinside.domain.comments.dao;

import doubledrg.bbinside.domain.comments.dto.CommentDetailDto;
import doubledrg.bbinside.domain.comments.dto.CommentSaveDto;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CommentDaoTest
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        CommentDao dao = new CommentDao();
        String content = createRandomString(100);
        Long userId = 10L;
        Long postId = 10L;
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Long saveId = dao.save(new CommentSaveDto(content, postId, userId, createdAt));

        System.out.println("saveId = " + saveId);
        List<CommentDetailDto> posts1 = dao.findByPostId(postId);
        System.out.println(posts1);

        List<CommentDetailDto> posts2 = dao.findByUserId(userId);
        System.out.println(posts2);
    }

    public static String createRandomString(int length)
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
