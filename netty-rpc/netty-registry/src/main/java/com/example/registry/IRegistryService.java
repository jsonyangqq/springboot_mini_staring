package com.example.registry;

/**
 * @author jason.yang
 * @Description 提供服务注册和服务发现的接口
 * @Date 2022-07-24 11:40
 */
public interface IRegistryService {

    /**
     * 服务注册
     * @param serviceInfo
     */
    void register(ServiceInfo serviceInfo);


    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    ServiceInfo discovery(String serviceName);

}