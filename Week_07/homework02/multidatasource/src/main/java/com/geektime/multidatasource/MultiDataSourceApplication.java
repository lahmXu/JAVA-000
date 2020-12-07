package com.geektime.multidatasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
@MapperScan("com.geektime.multidatasource.dao")
public class MultiDataSourceApplication {

    public static void main(String[] args) {
		SpringApplication.run(MultiDataSourceApplication.class, args);
    }

}
