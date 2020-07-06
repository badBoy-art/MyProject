package com.jdbc.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.jdbc.test.mapper.UserMapper;

/**
 * @author xuedui.zhao
 * @create 2018-06-04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class myBatisTest {

    @Resource
    private UserMapper userMapper;

    @Test
    @Ignore
    public void test01() {
        try {
            // mybatis的配置文件
            String resource = "mybatis/conf.xml";
            // 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
            InputStream is = myBatisTest.class.getClassLoader().getResourceAsStream(resource);
            // 构建sqlSession的工厂
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
            is.close();
            // 使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
            // Reader reader = Resources.getResourceAsReader(resource);
            // 构建sqlSession的工厂
            // SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 创建能执行映射文件中sql的sqlSession
            SqlSession session = sessionFactory.openSession();
            /**
             * 映射sql的标识字符串， com.jdbc.test.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
             * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
             */
            String statement = "com.jdbc.test.UserMapper.getUser"; // 映射sql的标识字符串
            // 执行查询返回一个唯一user对象的sql
            //User user = session.selectOne(statement, 1);
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.updateUser(1, true, "wangwu");
            User user = userMapper.getUser(1);
            System.out.println(user.getName());
            String statementList = "com.jdbc.test.UserMapper.getUsers";
            // 执行查询返回一个唯一user对象的sql
            PageHelper.startPage(1, 1);
            List<User> userList = session.selectList(statementList);
            System.out.println(userList.size());
            System.out.println(userList.get(0));
            session.commit();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() {
        String url =
                "jdbc:mysql://10.93.34.89:3306/f_fuwu_invoice?characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull";
        int hostLabelStartIndex = url.indexOf("//");
        int hostLabelEndIndex = url.indexOf("/", hostLabelStartIndex + 2);
        System.out.println(hostLabelStartIndex);
        System.out.println(hostLabelEndIndex);
        String hosts = url.substring(hostLabelStartIndex + 2, hostLabelEndIndex);
        String[] hostSegment = hosts.split(",");
        if (hostSegment.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (String host : hostSegment) {
                if (host.split(":").length == 1) {
                    sb.append(host + ":" + 3306 + ",");
                } else {
                    sb.append(host + ",");
                }
            }
            int databaseStartTag = url.lastIndexOf("/");
            int databaseEndTag = url.indexOf("?", databaseStartTag);
            if (databaseEndTag == -1) {
                databaseEndTag = url.length();
            }
            System.out.println(sb.toString());
        } else {
            String[] hostAndPort = hostSegment[0].split(":");
            int databaseStartTag = url.lastIndexOf("/");
            int databaseEndTag = url.indexOf("?", databaseStartTag);
            if (databaseEndTag == -1) {
                databaseEndTag = url.length();
            }
            System.out.println(databaseEndTag);
        }
    }

    @Test
    public void test03() {
        PageHelper.startPage(1, 2);
        User user = userMapper.getUser(1);
        System.out.println(user);
        //System.out.println(users);
        //userMapper.updateUser(1, true, "  ");
        //System.out.println(userMapper.getAll());
    }

    @Test
    public void batchUpdate() {
        List<User> users = Lists.newArrayListWithCapacity(2);
        User user1 = User.builder().id(1).name("我自横刀向天笑").build();
        User user2 = User.builder().id(2).name("去留肝胆两昆仑").build();
        users.add(user1);
        users.add(user2);

        int result = userMapper.updateBatch(users);
    }

    @Test
    public void test04() {
        int [] ids = new int[] {1,2};

        List<User> users = userMapper.getUserByIds(ids);
        System.out.println(users);
    }


    @Test
    public void test05() {
        User users = userMapper.getUserById(12);
        System.out.println(users);
    }
}
