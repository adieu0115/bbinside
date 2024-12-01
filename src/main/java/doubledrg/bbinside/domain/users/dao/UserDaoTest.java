package doubledrg.bbinside.domain.users.dao;

import doubledrg.bbinside.domain.users.User;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;

import java.sql.SQLException;

public class UserDaoTest
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        UserDao userDao = new UserDao();
        String username = "user_" + createRandomString(8);
        String password = createRandomString(12);
        Long saveId = userDao.save(new UserSaveDto(username, password));

        User findUser = userDao.findById(saveId);
        System.out.println("findUsername: " + findUser.getUsername());
        System.out.println("findUserPassword: " + findUser.getPassword());

        User findUser2 = userDao.findByName(findUser.getUsername());
        System.out.println("findUsername: " + findUser2.getUsername());
        System.out.println("findUserPassword: " + findUser2.getPassword());
    }

    public static String createRandomString(int length)
    {
        String seed = "123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append(seed.charAt((int) (Math.random() * (seed.length() - 1))));
        }
        return sb.toString();
    }
}
