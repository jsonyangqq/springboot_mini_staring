package com.example.constant;

import com.example.protocol.entity.RpcFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jason.yang
 * @Description 定义一些不可变类
 * @Date 2022-07-19 23:35
 */
public class RequestHolder {

    public static final AtomicLong REQUEST_ID=new AtomicLong();

    public static final Map<Long, RpcFuture> REQUEST_MAP=new ConcurrentHashMap<>();

}