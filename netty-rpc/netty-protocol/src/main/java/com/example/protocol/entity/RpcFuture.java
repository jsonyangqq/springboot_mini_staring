package com.example.protocol.entity;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-19 23:35
 */
@Data
public class RpcFuture<T> {

    private Promise<T> promise;

    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }

}