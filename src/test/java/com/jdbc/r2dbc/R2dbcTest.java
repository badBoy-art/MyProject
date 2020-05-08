package com.jdbc.r2dbc;

import org.junit.Test;
import org.springframework.data.r2dbc.core.DatabaseClient;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.Configuration;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-05-07
 * @Description
 */
public class R2dbcTest {

    @Test
    public void test() {

        Configuration configuration = new Configuration("root", "localhost", 3306, "zhaoZ1230", "test");
        ConnectionFactory connectionFactory = new JasyncConnectionFactory(new MySQLConnectionFactory(configuration));

        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);

        Flux<Order> rows = databaseClient.execute("select id, order_id orderId, amount, create_time createTime from `order`")
                .as(Order.class)
                .fetch().all();

        System.out.println("----------");

        System.out.println("fows " + rows.blockFirst().toString());

        Flux.just(1, 2, 3, 4, 5, 6).subscribe(System.out::print);
    }

}
