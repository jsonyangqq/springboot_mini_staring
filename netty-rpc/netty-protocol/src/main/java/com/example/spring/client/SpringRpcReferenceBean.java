package com.example.spring.client;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author jason.yang
 * @Description 使用工厂Bean注册动态代理
 * @Date 2022-07-22 14:44
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;

    private Class<?> interfaceClass;

    private String serverAddress;

    private int serverPort;

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    /**
     * 动态生成代理类
     */
    public void init() {
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new RpcInvokerProxy(this.serverAddress, this.serverPort));
    }


    public void setObject(Object object) {
        this.object = object;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
}