package com.example.registry.loadbalance;

import com.example.registry.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author jason.yang
 * @Description 抽象的算法策略类
 * @Date 2022-07-24 14:24
 */
public abstract class AbstractLoadBalance implements ILoadBalance<ServiceInstance<ServiceInfo>>{

    @Override
    public ServiceInstance<ServiceInfo> select(List<ServiceInstance<ServiceInfo>> serverList) {
        if(serverList == null || serverList.size() == 0) {
            return null;
        }
        if(serverList.size() == 1) {
            return serverList.get(0);
        }
        return doSelect(serverList);
    }

    protected abstract ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> serverList);

}