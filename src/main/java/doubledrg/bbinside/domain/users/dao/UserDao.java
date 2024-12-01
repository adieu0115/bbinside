package doubledrg.bbinside.domain.users.dao;

import doubledrg.bbinside.config.ConnectionMaker;
import doubledrg.bbinside.domain.users.User;
import doubledrg.bbinside.domain.users.dto.UserLoginDto;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;

import java.sql.*;

public class UserDao
{
    private final ConnectionMaker connectionMaker = new ConnectionMaker();

    public Long save(UserSaveDto dto) throws ClassNotFoundException, SQLException
    {
        final String sql =
                "INSERT INTO USERS(USERNAME,PASSWORD) " +
                        "VALUES(?,?)";

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setString(1, dto.getUsername());
            pstmt.setString(2, dto.getPassword());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys();)
            {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    public User findById(Long id) throws ClassNotFoundException, SQLException
    {
        final String sql =
                "SELECT *" +
                        " FROM USERS" +
                        " WHERE ID = ?";

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
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
                return user;
            }
        }
    }

    public User findByName(String username) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT *" +
                        " FROM USERS" +
                        " WHERE USERNAME = ?";

        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery())
            {
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
                return user;
            }
        }
    }

    public User findByNameAndPassword(UserLoginDto dto) throws SQLException, ClassNotFoundException
    {
        final String sql =
                "SELECT *" +
                        " FROM USERS" +
                        " WHERE USERNAME = ?" +
                        " AND PASSWORD = ?";


        try (Connection conn = connectionMaker.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);)
        {
            pstmt.setString(1, dto.getUsername());
            pstmt.setString(2, dto.getPassword());
            try (ResultSet rs = pstmt.executeQuery())
            {
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
                return user;
            }
        }
    }
}
