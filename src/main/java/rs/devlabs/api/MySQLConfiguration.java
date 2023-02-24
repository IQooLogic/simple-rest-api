package rs.devlabs.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T14:09
 */
@Configuration
@EnableJdbcRepositories
public class MySQLConfiguration extends AbstractJdbcConfiguration {
    // NamedParameterJdbcOperations is used internally to submit SQL statements to the database
    @Bean
    public NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.122.177:3306/threats?serverTimezone=UTC");
//        dataSource.setUsername("debian-sys-maint");
//        dataSource.setPassword("qT4DUEtie9xN9n26");

        dataSource.setUsername("milos");
        dataSource.setPassword("changeme");
        return dataSource;
    }
}
