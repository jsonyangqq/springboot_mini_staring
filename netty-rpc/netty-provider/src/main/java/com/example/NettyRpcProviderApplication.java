package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jason.yang
 * @Description netty服务端启动类
 * @Date 2022-07-20 10:39
 */
// 注册spring容器相关和服务接口发布相关类
@ComponentScan(basePackages = {"com.example.spring.service","com.example.service"})
@SpringBootApplication
public class NettyRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProviderApplication.class, args);
    }

}