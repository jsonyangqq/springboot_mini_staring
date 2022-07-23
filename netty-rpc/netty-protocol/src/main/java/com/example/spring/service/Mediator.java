package com.example.spring.service;

import com.example.protocol.entity.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jason.yang
 * @Description 存储Bean对象和对象方法实例之间的关系，并通过反射调用指定方法
 * @Date 2022-07-22 10:40
 */
public class Mediator {

    public static Map<String, BeanMethod> beanMethodMap = new ConcurrentHashMap<>();

    private static volatile Mediator mediator;

    private Mediator() {

    }

    public static Mediator getInstance() {
        if(mediator == null) {
            synchronized (Mediator.class) {
                if(mediator == null) {
                    return new Mediator();
                }
            }
        }
        return mediator;
    }

    /**
     * 通过反射获取调用指定的方法
     * @param rpcRequest
     * @return
     */
    public Object process(RpcRequest rpcRequest) {
        // 因为我们存储的是类名加方法名，所以这里需要通过类名加方法名进行获取
        BeanMethod beanMethod = beanMethodMap.get(rpcRequest.getClassName() + "." + rpcRequest.getMethodName());
        if(beanMethod == null) {
            return null;
        }
        Object bean = beanMethod.getBean();
        Method method = beanMethod.getMethod();
        try {
            return method.invoke(bean, rpcRequest.getParams());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}