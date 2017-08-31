package com.okeydokey;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Autowired
    DataSourceProperties dataSourceProperties;

    DataSource dataSource;

    @Bean
    DataSource realDataSource() {
        this.buildDataSource();
        return this.dataSource;
    }

    @Bean
    @Primary
    DataSource dataSource() {
        this.buildDataSource();
        return new Log4jdbcProxyDataSource(this.dataSource);
    }

    private void buildDataSource() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.dataSourceProperties.getClassLoader())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword());
        this.dataSource = factory.build();
    }
}
