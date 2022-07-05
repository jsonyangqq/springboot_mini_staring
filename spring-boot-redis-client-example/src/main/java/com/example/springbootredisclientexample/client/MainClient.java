package com.example.springbootredisclientexample.client;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-04
 */
public class MainClient {
    public static void main(String[] args) {
        CustomerRedisClient customerRedisClient=new CustomerRedisClient("192.168.235.134",6379);
        System.out.println(customerRedisClient.auth("123456"));
        System.out.println(customerRedisClient.set("name","zhangsan"));
        System.out.println(customerRedisClient.get("name"));
    }
}
