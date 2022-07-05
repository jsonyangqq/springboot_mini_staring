package com.example.springbootredisclientexample.client;

/**
 * @author jason.yang
 * @Description 拼接Redis命令标识符合常量定义
 * @Date 2022-07-04
 */
public class CommandConstant {

    public static final String START="*";

    public static final String LENGTH="$";

    public static final String LINE="\r\n";

    public enum CommandEnum{
        AUTH,
        SET,
        GET
    }
}
