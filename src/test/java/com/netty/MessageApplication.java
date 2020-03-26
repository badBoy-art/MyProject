package com.netty;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-02-22
 * @Description
 */
//@SpringBootApplication
@Service
public class MessageApplication {
    @Autowired
    private NettyServer server;
    @Autowired
    private NettyClient client;

    public static void main(String[] args) {
        //SpringApplication.run(MessageApplication.class, args);
    }

    @PostConstruct
    public void initMessage() {
        server.run(9502);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.run("ws://localhost:" + 9502);
    }
}