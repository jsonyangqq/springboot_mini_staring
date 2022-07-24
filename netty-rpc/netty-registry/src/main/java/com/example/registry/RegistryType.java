package com.example.registry;

/**
 * @author jason.yang
 * @Description 定义一个注册类型枚举类
 * @Date 2022-07-24 11:53
 */
public enum RegistryType {
    EUREKA((byte)0),
    ZOOKEEPER((byte)1)
    ;

    private byte registryType;

    RegistryType(byte registryType) {
        this.registryType = registryType;
    }

    public byte getRegistryType() {
        return registryType;
    }

    public static RegistryType findByRegistryType(byte registryType) {
        for (RegistryType value : RegistryType.values()) {
            if(value.getRegistryType() == registryType) {
                return value;
            }
        }
        return null;
    }

}