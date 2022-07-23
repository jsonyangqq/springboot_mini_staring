package com.example.spring.service;

import com.example.annotation.DTRemoteService;
import com.example.protocol.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @author jason.yang
 * @Description netty服务中spring需要装配的Bean定义
 * @Date 2022-07-22 10:28
 */
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private String serverAddress;
    private int serverPort;

    public SpringRpcProviderBean(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Bean初始化的时候会触发，这里主要完成Netty服务的启动
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin netty service to host:{},on port:{}", this.serverAddress, this.serverPort);
        new Thread(() -> {
            new NettyServer(this.serverAddress, this.serverPort).startServer();
        }).start();
    }

    /**
     * Bean初始化之后触发
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        // 判断类中是否有指定的注解
        if(aClass.isAnnotationPresent(DTRemoteService.class)) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                // 类名+方法名
                String key = bean.getClass().getInterfaces()[0].getName() + "." + method.getName();
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.beanMethodMap.put(key, beanMethod);
            }

        }
        return bean;
    }
}