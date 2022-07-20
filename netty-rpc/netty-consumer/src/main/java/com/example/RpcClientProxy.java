package com.example;

import java.lang.reflect.Proxy;

/**
 * @author jason.yang
 * @Description 客户端代理类
 * @Date 2022-07-20 10:46
 */
public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> interfaceClass, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvokerProxy(host, port));
    }

}