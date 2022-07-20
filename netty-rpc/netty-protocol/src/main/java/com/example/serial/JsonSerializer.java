package com.example.serial;

import com.alibaba.fastjson.JSON;

/**
 * @author jason.yang
 * @Description json序列化
 * @Date 2022-07-17 20:27
 */
public class JsonSerializer implements SerializerItf{

    @Override
    public <T> byte[] serializeToFile(T t) {
        String jsonString = JSON.toJSONString(t);
        return jsonString.getBytes();
    }

    @Override
    public <T> T deserializeFromFile(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(new String(bytes), clazz);
    }
}