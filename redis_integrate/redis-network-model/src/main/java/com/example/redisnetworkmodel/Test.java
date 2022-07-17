package com.example.redisnetworkmodel;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-12 9:52
 */
public class Test {

    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 400; i++) {
            stringBuilder.append("-").append(i);
        }
        System.out.println(stringBuilder.length());

    }

}