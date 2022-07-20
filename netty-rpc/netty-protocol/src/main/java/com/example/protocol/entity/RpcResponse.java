package com.example.protocol.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jason.yang
 * @Description rpc消息响应报文实体
 * @Date 2022-07-19 17:36
 */
@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 响应对象
     */
    private T object;

    /**
     * 响应代码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;


}