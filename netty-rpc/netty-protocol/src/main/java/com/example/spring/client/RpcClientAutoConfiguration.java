package com.example.spring.client;


import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author jason.yang
 * @Description 客户端自动配置类
 * @Date 2022-07-22 15:29
 */
@Configuration
public class RpcClientAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

//    @Bean
//    public MyBeanDefinitionRegistryPostProcessor myBeanDefinitionRegistryPostProcessor(RpcClientProperties rpcClientProperties) {
//        return new MyBeanDefinitionRegistryPostProcessor(rpcClientProperties.getServerPort());
//    }
//
//    @Bean
//    public MyImportBeanDefinitionRegister myImportBeanDefinitionRegister(RpcClientProperties rpcClientProperties) {
//        if(rpcClientProperties == null) {
//            return new MyImportBeanDefinitionRegister();
//        }
//        return new MyImportBeanDefinitionRegister(rpcClientProperties.getServerPort());
//    }

    /**
     * 由于SpringRpcReferencePostProcessor类实现了BeanFactoryPostProcessor接口，这里通过自动注入注入不进去值
     * @return
     */
    @Bean
    public SpringRpcReferencePostProcessor springRpcReferencePostProcessor() {
        RpcClientProperties rpcClientProperties = new RpcClientProperties();
        rpcClientProperties.setServerPort(Integer.parseInt(environment.getProperty("dt.rpc.client.serverPort")));
        return new SpringRpcReferencePostProcessor(rpcClientProperties.getServerPort());
    }

}

