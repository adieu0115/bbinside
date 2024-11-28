package doubledrg.bbinside.domain.users.dao;

import doubledrg.bbinside.config.database.daoTemplate.BbDaoTemplate;
import doubledrg.bbinside.config.database.transactionManager.BbTransactionManager;
import doubledrg.bbinside.domain.users.Users;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserDao
{
    private final BbTransactionManager transactionManager;
    private final BbDaoTemplate daoTemplate;

    public Users save(UserSaveDto users)
    {
        try
        {
            Connection conn = transactionManager.begin();
            String sql = "INSERT INTO USERS(USERNAME,PASSWORD) VALUES(?,?)";
            Long id = daoTemplate.updateAndReturnId(conn, sql, pstmt ->
            {
                pstmt.setString(1, users.getUsername());
                pstmt.setString(2, users.getPassword());
            });

            sql = "SELECT * FROM USERS WHERE ID = ?";
            Users user1 = daoTemplate.query(conn, sql,
                    pstmt ->
                    {
                        pstmt.setLong(1, id);
                    },

                    rs ->
                    {
                        if (rs.next())
                        {
                            Long userId = rs.getLong("ID");
                            String username = rs.getString("USERNAME");
                            String password = rs.getString("PASSWORD");
                            return new Users(userId, username, password);
                        }
                        return null;
                    });

            transactionManager.commit(conn);
            return user1;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
