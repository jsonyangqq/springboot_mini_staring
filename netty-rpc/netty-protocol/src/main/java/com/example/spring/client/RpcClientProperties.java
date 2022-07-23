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

    private int serverPort;

}