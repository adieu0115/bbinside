package doubledrg.bbinside.config.database.daoTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface BbDaoTemplate
{
    public void update(Connection conn, String sql, JdbcTemplate.StatementSetter setter);

    public Long updateAndReturnId(Connection conn, String sql, JdbcTemplate.StatementSetter setter);

    <T> T query(Connection conn, String sql, JdbcTemplate.StatementSetter setter, ResultMap<T> mapper);

    public interface ResultMap<T>
    {
        T map(ResultSet rs) throws SQLException;
    }
}
