package com.example.serial;

import com.example.constant.SerialType;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jason.yang
 * @Description 序列化机制的管理类
 * @Date 2022-07-19 15:59
 */
public class SerializerManager {

    /**
     * 存储序列化类型和对应的实际序列化它们之间的关系
     */
    private final static ConcurrentHashMap<Byte, SerializerItf> serializers = new ConcurrentHashMap<>();

    static {
        SerializerItf jsonSerializer = new JsonSerializer();
        SerializerItf javaSerializer = new JavaSerializer();
        serializers.put(SerialType.JSON_SERIAL.getCode(), jsonSerializer);
        serializers.put(SerialType.JAVA_SERIAL.getCode(), javaSerializer);
    }

    /**
     * 根据序列化类型获取指定的序列化方式
     * @param serialType
     * @return
     */
    public static SerializerItf getSerializer(Byte serialType) {
        SerializerItf serializerItf = serializers.get(serialType);
        if(serializerItf == null) {
            return new JsonSerializer();
        }
        return serializerItf;
    }

}