package doubledrg.bbinside.domain.users.dao;

import doubledrg.bbinside.domain.users.User;
import doubledrg.bbinside.domain.users.dto.UserLoginDto;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;

import java.sql.*;

public class UserDao
{
    public Connection makeConnection() throws ClassNotFoundException, SQLException
    {
        final String url = "jdbc:mysql://localhost:3306/bbinside";
        final String username = "root";
        final String password = "endyd132!!";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    public Long save(UserSaveDto dto) throws ClassNotFoundException, SQLException
    {
        final String sql = "INSERT INTO USERS(USERNAME,PASSWORD) VALUES(?,?)";

        Connection conn = makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, dto.getUsername());
        pstmt.setString(2, dto.getPassword());
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);

        rs.close();
        pstmt.close();
        conn.close();

        return id;
    }

    public User findById(Long id) throws ClassNotFoundException, SQLException
    {
        final String sql = "SELECT * FROM USERS WHERE ID = ?";

        Connection conn = makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, id);
        ResultSet rs = pstmt.executeQuery();

        User user = new User();
        if (rs.next())
        {
            String username = rs.getString("username");
            String password = rs.getString("password");
            user.setUsername(username);
            user.setPassword(password);
        }
        else
        {
            user = null;
        }

        conn.close();
        pstmt.close();
        rs.close();

        return user;
    }

    public User findByName(String username) throws SQLException, ClassNotFoundException
    {
        final String sql = "SELECT * FROM USERS WHERE USERNAME = ?";

        Connection conn = makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        User user = new User();
        if (rs.next())
        {
            long id1 = rs.getLong("ID");
            String username1 = rs.getString("USERNAME");
            String password1 = rs.getString("PASSWORD");
            user.setId(id1);
            user.setUsername(username1);
            user.setPassword(password1);
        }
        else
        {
            user = null;
        }

        rs.close();
        pstmt.close();
        conn.close();

        return user;
    }

    public User findByNameAndPassword(UserLoginDto dto) throws SQLException, ClassNotFoundException
    {
        final String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";

        Connection conn = makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, dto.getUsername());
        pstmt.setString(2, dto.getPassword());
        ResultSet rs = pstmt.executeQuery();

        User user = new User();
        if (rs.next())
        {
            String username = rs.getString("username");
            String password = rs.getString("password");
            user.setUsername(username);
            user.setPassword(password);
        }
        else
        {
            user = null;
        }

        rs.close();
        pstmt.close();
        conn.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        UserDao userDao = new UserDao();
        String username = "user_" + userDao.createRandomString(8);
        String password = userDao.createRandomString(12);
        Long saveId = userDao.save(new UserSaveDto(username, password));

        User findUser = userDao.findById(saveId);
        System.out.println("findUsername: " + findUser.getUsername());
        System.out.println("findUserPassword: " + findUser.getPassword());

        User findUser2 = userDao.findByName(findUser.getUsername());
        System.out.println("findUsername: " + findUser2.getUsername());
        System.out.println("findUserPassword: " + findUser2.getPassword());
    }

    public String createRandomString(int length)
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
