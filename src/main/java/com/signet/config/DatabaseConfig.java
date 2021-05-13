package com.signet.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

    // @Bean(name = "compiere")
    // @ConfigurationProperties(prefix = "spring.datasource.compiere")
    // public DataSource dataSourceCompiere() {
    //   return DataSourceBuilder.create().build();
    // }

    // @Bean(name = "jdbcTemplateCompiere")
    // public JdbcTemplate jdbcTemplate(@Qualifier("compiere") DataSource ds) {
    //  return new JdbcTemplate(ds);
    // }

    @Bean(name = "postgres")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSource dataSourceCompiere() {
      return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplateCompiere")
    public JdbcTemplate jdbcTemplate(@Qualifier("postgres") DataSource ds) {
     return new JdbcTemplate(ds);
    }
}
