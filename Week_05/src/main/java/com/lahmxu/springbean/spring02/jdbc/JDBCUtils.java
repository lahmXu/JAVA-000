package com.lahmxu.springbean.spring02.jdbc;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static String driverName;
    private static String url;
    private static String user;
    private static String password;

    /**
     * 通过静态代码块，初始化数据库连接配置数据，并且注册数据库驱动
     */
    static {
        try {
            Properties properties = new Properties();
            //通过读取Properties文件给属性赋值，即每次使用该工具类都会读取最新配置进行连接
            properties.load(new ClassPathResource("application.yml").getInputStream());
            driverName = properties.getProperty("driverName");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            Class.forName(driverName);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
    }
    /**
     * 获取数据库连接对象
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
        return con;
    }
    /**
     * 关闭JDBC相关资源
     * @param con
     * @param sta
     * @param rs
     */
    public static void closeResource(Connection con, Statement sta, ResultSet rs) {
        try {
            if(con!=null) {
                con.close();
            }
            if(sta!=null) {
                sta.close();
            }
            if(rs!=null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet query(Statement statement,String sql, ResultSet resultSet){
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
