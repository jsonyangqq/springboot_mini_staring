package com.example.serial;

/**
 * @author jason.yang
 * @Description 序列化接口定义
 * @Date 2022-07-17 20:01
 */
public interface SerializerItf {

    /**
     * 序列化接口
     * @param t
     * @return
     */
    <T> byte[] serializeToFile(T t);

    /**
     * 反序列化接口
     * @param bytes
     * @return
     */
    <T> T deserializeFromFile(byte[] bytes, Class<T> clazz);

}