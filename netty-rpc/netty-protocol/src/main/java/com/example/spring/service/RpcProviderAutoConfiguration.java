package com.example.spring.service;

import com.example.registry.IRegistryService;
import com.example.registry.RegistryFactory;
import com.example.registry.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author jason.yang
 * @Description 自动装配的类
 * @Date 2022-07-22 11:04
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    /**
     * 这里将RpcServerProperties类里面的参数自动装配到对应的bean中
     * @param rpcServerProperties
     * @return
     */
    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        IRegistryService registryService = RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(), RegistryType.findByRegistryType(rpcServerProperties.getRegistry()));
        return new SpringRpcProviderBean(hostAddress, rpcServerProperties.getServerPort(), registryService);
    }

}