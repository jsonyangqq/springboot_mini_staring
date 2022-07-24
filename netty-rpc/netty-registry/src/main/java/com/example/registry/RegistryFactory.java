package com.example.registry;

import com.example.registry.zookeeper.ZookeeperRegistryService;

/**
 * @author jason.yang
 * @Description 定义RegistryFactory工厂方法类
 * @Date 2022-07-24 11:59
 */
public class RegistryFactory {

    /**
     * 根据对应的服务地址和服务类别创建对应的注册中心服务
     * @param serviceAddress
     * @param registryType
     * @return
     */
    public static IRegistryService createRegistryService(String serviceAddress, RegistryType registryType) {
        IRegistryService registryService = null;
        switch (registryType) {
            case EUREKA:
                //TODO
                break;
            case ZOOKEEPER:
                registryService = new ZookeeperRegistryService(serviceAddress);
                break;
            default:
        }
        return registryService;
    }

}