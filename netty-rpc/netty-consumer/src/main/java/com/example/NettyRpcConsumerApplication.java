package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jason.yang
 * @Description netty客户端连接类
 * @Date 2022-07-20 15:04
 */
@ComponentScan(basePackages = {"com.example.controller"})
@SpringBootApplication
public class NettyRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcConsumerApplication.class, args);
    }

}