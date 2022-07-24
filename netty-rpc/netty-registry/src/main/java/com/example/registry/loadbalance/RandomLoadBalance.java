package com.example.registry.loadbalance;

import com.example.registry.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * @author jason.yang
 * @Description 随机访问策略
 * @Date 2022-07-24 14:27
 */
public class RandomLoadBalance extends AbstractLoadBalance{

    @Override
    protected ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> serverList) {
        Random random = new Random();
        int i = random.nextInt(serverList.size());
        return serverList.get(i);
    }

}