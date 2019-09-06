package com.purang.camp.uiautotest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/*import java.sql.*;
import java.sql.Statement;*/
import java.util.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
//import com.mysql.jdbc.Driver;

public class JdbcUtilByJwh {
    static Connection conn = null;
    public static String driverClassName = "com.mysql.jdbc.Driver";
    public static String jdbcUrl = "jdbc:mysql://";
    public static String username = "root";
    public static String password = "111111";
    public static String sqlResult = null;
    public static java.sql.Statement stmt = null;


    /**
     * @do    excute sql statement
     * @param数据库配置连接
     * @paramsql语句
     * @return
     */
    /*连接数据库*/
    public static Connection getConnection(){
        Connection conn=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //测试环境
            conn = DriverManager.getConnection("jdbc:mysql://", "root", "111111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static String getDataList(String jdbcUrl, String sql){
        try {
            // 注册 JDBC 驱动
            Class.forName(driverClassName);
            // 打开链接

            conn = (Connection) DriverManager.getConnection(jdbcUrl, username, password);
            // 执行查询
            stmt = conn.createStatement();
            ResultSet rs = null;
            System.out.println("jdbcUrl==="+jdbcUrl);
            System.out.println("sql==="+sql);
            rs = stmt.executeQuery(sql);
//          System.out.println("rs==="+rs);
            String columns[] = {"code"};
//           展开结果集数据库
            while (rs.next()) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    sqlResult = rs.getString(columns[i]);
                    map.put(columns[i], sqlResult);
                }
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se) {
            // 处理 JDBC 异常
            System.out.println("处理 JDBC 错误!");
            se.printStackTrace();
        }catch (Exception e) {
            // 处理 Class.forName 异常
            System.out.println("处理 Class.forName 错误");
            e.printStackTrace();
        }finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("sqlResult===" +sqlResult);
        return sqlResult;
    }
    public static String dbDataMethod(String phoneNum){
        String sql = "SELECT code FROM mobile_validate where mobile = "+phoneNum +";";
        String result = getDataList(jdbcUrl, sql);
        System.out.println("result===" +result);
        return result;
    }
    public void delData(String phoneNum){
        String sql = "DELETE FROM `asset`.`user` WHERE `login_name` = "+phoneNum +";";
        try {
            // 注册 JDBC 驱动
            Class.forName(driverClassName);
            // 打开链接
            conn = (Connection) DriverManager.getConnection(jdbcUrl, username, password);
            // 执行查询
            stmt = conn.createStatement();
            System.out.println("jdbcUrl==="+jdbcUrl);
            System.out.println("sql==="+sql);
            stmt.execute(sql);
            stmt.close();
            conn.close();
        }catch (SQLException se) {
            // 处理 JDBC 异常
            System.out.println("处理 JDBC 错误!");
            se.printStackTrace();
        }catch (Exception e) {
            // 处理 Class.forName 异常
            System.out.println("处理 Class.forName 错误");
            e.printStackTrace();
        }finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
