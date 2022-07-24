package com.example.registry;

import lombok.Data;

/**
 * @author jason.yang
 * @Description 服务信息类封装
 * @Date 2022-07-24 11:41
 */
@Data
public class ServiceInfo {

    private String serviceName;

    private String serviceAddress;

    private int servicePort;


}