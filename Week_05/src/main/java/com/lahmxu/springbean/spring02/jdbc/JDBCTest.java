package com.lahmxu.springbean.spring02.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 1. JDBC Statement 和 PreparedStatement
 * 2. HikariCP
 */
public class JDBCTest {
    public static void main(String[] args) throws SQLException, IOException {

        String sqlStatement = "SELECT * FROM mint where id = ?";
        ResultSet resultSet = null;
//        // 步骤一：创建 statement 对象
//        Connection connection = JDBCUtils.getConnection();
//        Statement statement = connection.createStatement();
//        print(JDBCUtils.query(statement,sqlStatement,resultSet));
//        JDBCUtils.closeResource(connection, statement, resultSet);

//        // 步骤二：创建 preparedStatement 对象
//        Connection connection = JDBCUtils.getConnection();
//        PreparedStatement statement = connection.prepareStatement(sqlStatement);
//        statement.setInt(1,1);
//        resultSet = statement.executeQuery();
//        print(resultSet);
//        JDBCUtils.closeResource(connection, statement, resultSet);

//        // 步骤三：使用HikariCP连接数据库
//        Properties properties = new Properties();
//        properties.load(new ClassPathResource("application.yml").getInputStream());
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(properties.getProperty("url"));
//        hikariConfig.setDriverClassName(properties.getProperty("driverName"));
//        hikariConfig.setUsername(properties.getProperty("user"));
//        hikariConfig.setPassword(properties.getProperty("password"));
//        hikariConfig.addDataSourceProperty("connection-timeout", 30000);
//        hikariConfig.addDataSourceProperty("minimum-idle", 10);
//        hikariConfig.addDataSourceProperty("maximum-pool-size", 65);
//        hikariConfig.addDataSourceProperty("idle-timeout", 60000);
//        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
//        Connection connection = dataSource.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
//        preparedStatement.setInt(1,1);
//        print(preparedStatement.executeQuery());
//        connection.close();

    }

    private static void print(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }
}
