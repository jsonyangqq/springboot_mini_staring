package com.example.protocol.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jason.yang
 * @Description rpc请求体
 * @Date 2022-07-19 15:47
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 请求参数
     */
    private Object[] params;


    /**
     * 参数类型
     */
    private Class<?>[] paramterTypes;

}