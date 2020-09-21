package test.rcslabs.test202001.test202001.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
public class SqliteConfiguration {

    Environment env;
    DataSource dataSource;

//    public DataSource dataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(env.getProperty("driverClassName"));
//        dataSourceBuilder.url(env.getProperty("url"));
//        return dataSourceBuilder.build();
//    }

}
