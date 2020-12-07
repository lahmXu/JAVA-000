package com.geektime.multidatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.geektime.multidatasource.service.dynamicDataSource.DataSourceSwitcher;
import com.geektime.multidatasource.service.dynamicDataSource.DataSourceType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource ds1DataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave1")
    public DataSource ds2DataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSourceSwitcher dataSource(DataSource ds1DataSource, DataSource ds2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DataSourceType.MASTER.name(), ds1DataSource);
        targetDataSources.put(DataSourceType.SLAVE.name(), ds2DataSource);
        return new DataSourceSwitcher(ds1DataSource, targetDataSources);
    }
}
