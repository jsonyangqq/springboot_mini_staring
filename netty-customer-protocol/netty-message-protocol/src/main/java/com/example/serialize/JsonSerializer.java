package com.example.serialize;

import com.alibaba.fastjson.JSON;
import com.example.protocol.Student;

import java.nio.charset.StandardCharsets;

/**
 * @author jason.yang
 * @Description json序列化
 * @Date 2022-07-17 20:27
 */
public class JsonSerializer implements SerializerItf<Student>{


    @Override
    public byte[] serializeToFile(Student obj) throws Exception {
        return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Student deserializeFromFile(byte[] bytes) throws Exception {
        return JSON.parseObject(new String(bytes), Student.class);
    }

}