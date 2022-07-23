package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jason.yang
 * @Description netty客户端连接类
 * @Date 2022-07-20 15:04
 */
@ComponentScan(basePackages = {"com.example.controller", "com.example.spring.client"})
@SpringBootApplication
@Slf4j
public class NettyRpcConsumerApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcConsumerApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info(this.applicationContext.getEnvironment().getProperty("dt.rpc.client.serverPort")  + "------");
    }
}