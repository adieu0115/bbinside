package doubledrg.bbinside.config.database.daoTemplate;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate implements BbDaoTemplate
{
    public void update(Connection conn, String sql, StatementSetter setter)
    {
        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            setter.setArgs(pstmt);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Long updateAndReturnId(Connection conn, String sql, StatementSetter setter)
    {
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            setter.setArgs(pstmt);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys())
            {
                if (rs.next())
                {
                    return rs.getLong(1);
                }
                return null;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T query(Connection conn, String sql, StatementSetter setter, ResultMap<T> mapper)
    {
        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            setter.setArgs(pstmt);
            try (ResultSet rs = pstmt.executeQuery())
            {
                return mapper.map(rs);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface StatementSetter
    {
        void setArgs(PreparedStatement pstmt) throws RuntimeException, SQLException;
    }
}
