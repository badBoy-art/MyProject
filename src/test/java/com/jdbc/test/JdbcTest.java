package com.jdbc.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author xuedui.zhao
 * @create 2019-04-20
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/spring.xml"})
public class JdbcTest {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //@Value("${jdbc.url}")
    private String DB_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=true&zeroDateTimeBehavior=convertToNull";

    // 数据库的用户名与密码，需要根据自己的设置
    //@Value("${jdbc.username}")
    private String USER = "root";

    //@Value("${jdbc.password}")
    private String PASS = "123456";

    @Test
    public void testJdbc() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, age, deleteFlag FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Object deleteFlag = rs.getObject("deleteFlag");
                boolean deleboolean = rs.getBoolean("deleteFlag");
                int delint = rs.getInt("deleteFlag");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
                System.out.print(", age: " + age);
                System.out.print(", deleteFlag: " + deleteFlag);
                System.out.print(", deleboolean: " + deleboolean);
                System.out.print(", delint: " + delint);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    @Test
    public void test02() {
        try {
            HikariDataSource dataSource = getDataSource();
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT id, name, age, deleteFlag FROM user;");
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    Object deleteFlag = rs.getObject("deleteFlag");

                    // 输出数据
                    System.out.print("ID: " + id);
                    System.out.print(", name: " + name);
                    System.out.print(", age: " + age);
                    System.out.print(", deleteFlag: " + deleteFlag);
                    System.out.print("\n");
                }
            }

            if (connection != null && !connection.isClosed())
                connection.close();
            if (dataSource != null && !dataSource.isClosed())
                dataSource.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HikariDataSource getDataSource() throws SQLException {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=true&zeroDateTimeBehavior=convertToNull");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(50);

        return new HikariDataSource(config);
    }
}
