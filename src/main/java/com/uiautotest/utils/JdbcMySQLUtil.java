package com.purang.camp.uiautotest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  该类提供 数据库常用的操作。
 */
public class JdbcMySQLUtil {
    /*连接数据库*/
    public static Connection getConnection(){
        Connection conn=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //测试环境
            conn = DriverManager.getConnection("jdbc:mysql://10.10.96.142:3306/asset", "root", "111111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    /*封装 增删改*/
    public static int operation(String sql, Object[] obj) {
        Connection conn = null;
        PreparedStatement statement = null;
        int count = -1;
        try {
            conn = getConnection();
            // 获取PreparedStatement对象
            statement = conn.prepareStatement(sql);// 预编译sql的功能，可以防止sql注入
            for (int i = 1; i <= obj.length; i++) {
                statement.setObject(i, obj[i - 1]);
            }
            // 执行查询
            count = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /*封装 数据库查询*/
    public static ResultSet query(String sql, Object[] obj) {
        ResultSet res = null;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            // 获取PreparedStatement对象
            statement = conn.prepareStatement(sql);// 预编译sql的功能，可以防止sql注入
            for (int i = 1; i <= obj.length; i++) {
                statement.setObject(i, obj[i - 1]);
            }
            // 执行查询
            res = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /*关闭数据库*/
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
