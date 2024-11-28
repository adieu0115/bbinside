package doubledrg.bbinside.config.database.transactionManager;

import java.sql.Connection;
import java.sql.SQLException;

public interface BbTransactionManager
{
    Connection begin() throws SQLException;

    void commit(Connection conn) throws SQLException;

    void rollback(Connection conn) throws SQLException;
}
