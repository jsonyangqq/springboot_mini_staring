package com.example.spring.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jason.yang
 * @Description 自定义服务参数配置类
 * @Date 2022-07-22 11:01
 */
@Data
@ConfigurationProperties(prefix = "dt.rpc.service")
@Getter
@Setter
public class RpcServerProperties {

    private int serverPort;

    // 注册中心的地址
    private String registryAddress;
    // 注册中心的类型
    private byte registry;

}