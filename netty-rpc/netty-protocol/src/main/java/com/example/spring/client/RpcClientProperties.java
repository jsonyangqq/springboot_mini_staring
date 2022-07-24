package com.example.spring.client;

import lombok.Data;

/**
 * @author jason.yang
 * @Description 自定义服务参数配置类
 * @Date 2022-07-22 11:01
 */
@Data
//@ConfigurationProperties(prefix = "dt.rpc.client")
public class RpcClientProperties {

//    private int serverPort;

    // 服务注册地址
    private String registryAddress;

    // 服务注册类型
    private byte registry;

}