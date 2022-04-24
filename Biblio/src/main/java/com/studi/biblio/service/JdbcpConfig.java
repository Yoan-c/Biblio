package com.studi.biblio.service;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

public class JdbcpConfig {
    @Bean
    @SneakyThrows
    public DataSource MysqlDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/biblio");
        ds.setUsername("root");
        return ds;
    }
}
