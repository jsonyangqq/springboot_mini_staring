package com.example.redisnetworkmodel.reactor;

import java.io.IOException;

/**
 * @author jason.yang
 * @Description 测试Reactor模型
 * @Date 2022-07-09
 */
public class ReactorMain {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8080), "Main-Thread").start();
    }

}