package com.example.serialize;

import com.example.protocol.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author jason.yang
 * @Description java序列化实现
 * @Date 2022-07-17 20:05
 */
public class JavaSerializer implements SerializerItf<Student>{

    @Override
    public byte[] serializeToFile(Student obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        return bos.toByteArray();
    }


    @Override
    public Student deserializeFromFile(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Student student = (Student) ois.readObject();
        return student;
    }
}