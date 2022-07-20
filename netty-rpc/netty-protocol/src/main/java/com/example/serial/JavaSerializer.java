package com.example.serial;

import java.io.*;

/**
 * @author jason.yang
 * @Description java序列化实现
 * @Date 2022-07-17 20:05
 */
public class JavaSerializer implements SerializerItf{

    @Override
    public <T> byte[] serializeToFile(T t) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserializeFromFile(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return (T)o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}