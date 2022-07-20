package com.example.protocol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jason.yang
 * @Description 协议头定义
 * @Date 2022-07-19 15:36
 */
@AllArgsConstructor
@Data
public class Header implements Serializable {

    /**
     * 魔数，2个字节
     */
    private Short magic;

    /**
     * 序列化类型 1个字节
     */
    private Byte serialType;

    /**
     * 消息类型 1个字节
     */
    private Byte reqType;


    /**
     * 请求id 8个字节
     */
    private Long requestId;

    /**
     * 消息长度 4个字节
     */
    private Integer length;


}