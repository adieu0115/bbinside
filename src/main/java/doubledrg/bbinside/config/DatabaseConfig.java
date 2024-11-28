package doubledrg.bbinside.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import doubledrg.bbinside.config.database.daoTemplate.BbDaoTemplate;
import doubledrg.bbinside.config.database.daoTemplate.JdbcTemplate;
import doubledrg.bbinside.config.database.transactionManager.BbTransactionManager;
import doubledrg.bbinside.config.database.transactionManager.JdbcTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig
{
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Profile("hikari")
    public DataSource hikariDataSource()
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    @Bean
    @Profile("driverManager")
    public DataSource driverManagerDataSource()
    {
        return new DriverManagerDataSource(url, username, password);
    }

    @Bean
    @Profile("jdbc")
    public BbTransactionManager bbTransactionManager(DataSource dataSource)
    {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    @Profile("jdbc")
    public BbDaoTemplate bbDaoTemplate()
    {
        return new JdbcTemplate();
    }
}
