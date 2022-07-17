package com.example.protocol;

import lombok.Data;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-17 12:07
 */
@Data
public class Header {

    // 会话id  8个字节
    private Long sessionId;

    // 消息类型 1个字节
    private byte reqType;

    // 消息长度 4个字节
    private int length;


}