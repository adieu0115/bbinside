package doubledrg.bbinside.config.database.transactionManager;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class JdbcTransactionManager implements BbTransactionManager
{
    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final DataSource dataSource;

    public Connection begin() throws SQLException
    {
        Connection conn = connectionHolder.get();
        if (conn == null)
        {
            conn = dataSource.getConnection();
            connectionHolder.set(conn);
        }
        conn.setAutoCommit(false);
        return conn;
    }

    public void commit(Connection conn) throws SQLException
    {
        try (conn)
        {
            conn.commit();
            conn.setAutoCommit(true);
            connectionHolder.remove();
        }
    }

    public void rollback(Connection conn) throws SQLException
    {
        try (conn)
        {
            conn.rollback();
            conn.setAutoCommit(true);
            connectionHolder.remove();
        }
    }
}
