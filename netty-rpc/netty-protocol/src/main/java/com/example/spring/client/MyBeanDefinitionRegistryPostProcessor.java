package com.example.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author jason.yang
 * @Description 验证BeanDefinition修改能否换位置注入,想要绕开springboot中针对BeanFactoryPostProcessor自动装配失效问题
 * @Date 2022-07-23 8:58
 */
@Slf4j
@Deprecated
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{

    private int serverPort;

    public MyBeanDefinitionRegistryPostProcessor(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        log.info("MyBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry run.");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}