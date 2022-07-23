package com.example.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * @author jason.yang
 * @Description 测试看能否让自动装配的相关配置优先于BeanFactoryPostProcessor接口提前执行，以达到可以在他之前获取到配置值（实际不行）
 * @Date 2022-07-23 6:54
 */
@Slf4j
@Deprecated
public class MyImportBeanDefinitionRegister implements BeanFactoryPostProcessor, PriorityOrdered {

    private int serverPort;

    public MyImportBeanDefinitionRegister() {
    }

    public MyImportBeanDefinitionRegister(int serverPort){
        log.info("registry MyImportBeanDefinitionRegister construct,in port:{} .", this.serverPort);
        this.serverPort = serverPort;
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
//        Map<String, BeanDefinition> beanDefinitionMap = springRpcReferencePostProcessor.beanDefinitionMap;
//        beanDefinitionMap.forEach((key, beanDefinition) -> {
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SpringRpcReferenceBean.class);
//            builder.addPropertyValue("serverAddress", hostAddress);
//            builder.addPropertyValue("serverPort", serverPort);
//        });
    }

    public int getServerPort() {
        return serverPort;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}