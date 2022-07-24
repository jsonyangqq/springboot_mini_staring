package com.example.registry.zookeeper;

import com.example.registry.IRegistryService;
import com.example.registry.ServiceInfo;
import com.example.registry.loadbalance.ILoadBalance;
import com.example.registry.loadbalance.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-24 14:20
 */
@Slf4j
public class ZookeeperRegistryService implements IRegistryService {

    // 注册路径
    private static final String REGISTRY_PATH = "/registry";

    // curator服务注册和发现的封装
    private final ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private ILoadBalance<ServiceInstance<ServiceInfo>> loadBalance;

    public ZookeeperRegistryService(String registryAddress) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(registryAddress, new ExponentialBackoffRetry(1000, 3));
        client.start();
        JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .client(client)
                .serializer(serializer)
                .basePath(REGISTRY_PATH)
                .build();
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public void register(ServiceInfo serviceInfo) {
        log.info("begin register serviceInfo to zookeeper server.");
        try {
            ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
                    .name(serviceInfo.getServiceName())
                    .address(serviceInfo.getServiceAddress())
                    .port(serviceInfo.getServicePort())
                    .payload(serviceInfo)
                    .build();
            this.serviceDiscovery.registerService(serviceInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceInfo discovery(String serviceName) {
        log.info("begin discovery serviceInfo from zookeeper server.");
        Collection<ServiceInstance<ServiceInfo>> serviceInstances =
                null;
        try {
            serviceInstances = this.serviceDiscovery.queryForInstances(serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 动态路由
        ServiceInstance<ServiceInfo> serviceInstance = this.loadBalance.select((List<ServiceInstance<ServiceInfo>>) serviceInstances);
        if(serviceInstance == null) {
            return null;
        }
        return serviceInstance.getPayload();
    }

}