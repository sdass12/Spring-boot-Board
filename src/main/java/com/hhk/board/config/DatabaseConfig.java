package com.hhk.board.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.hhk.board.repository")
public class DatabaseConfig {

    @Value("${driverClassName}")
    String driver;
    @Value("${url}")
    String url;
    @Value("${dbUserName}")
    String username;
    @Value("${dbPassword}")
    String password;

    @Bean
    public HikariConfig hikariConfig() {


        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return hikariConfig;
    }


    @Bean
    public DataSource datasource(){
        return new HikariDataSource(this.hikariConfig());
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(this.datasource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean =new SqlSessionFactoryBean();
        bean.setDataSource(this.datasource());
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);


        return bean.getObject();
    }

}
