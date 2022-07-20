package com.example.protocol.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jason.yang
 * @Description rpc协议报文组成
 * @Date 2022-07-19 15:42
 */
@Data
public class RpcProtocol<T> implements Serializable {


    private Header header;

    private T content;

}