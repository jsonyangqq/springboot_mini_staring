package com.example.spring.client;

import com.example.registry.IRegistryService;
import com.example.registry.RegistryFactory;
import com.example.registry.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author jason.yang
 * @Description 使用工厂Bean注册动态代理
 * @Date 2022-07-22 14:44
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;

    private Class<?> interfaceClass;

    private String registryAddress;

    private byte registry;

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    /**
     * 动态生成代理类
     */
    public void init() {
        IRegistryService registryService = RegistryFactory.createRegistryService(this.registryAddress, RegistryType.findByRegistryType(this.registry));
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new RpcInvokerProxy(registryService));
    }


    public void setObject(Object object) {
        this.object = object;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }


    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistry(byte registry) {
        this.registry = registry;
    }
}