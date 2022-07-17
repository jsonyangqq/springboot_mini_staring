package com.example.serialize;

/**
 * @author jason.yang
 * @Description 序列化接口定义
 * @Date 2022-07-17 20:01
 */
public interface SerializerItf<T> {

    /**
     * 序列化接口
     * @param t
     * @return
     */
    byte[] serializeToFile(T t) throws Exception;

    /**
     * 反序列化接口
     * @param bytes
     * @return
     */
    T deserializeFromFile(byte[] bytes) throws Exception;

}