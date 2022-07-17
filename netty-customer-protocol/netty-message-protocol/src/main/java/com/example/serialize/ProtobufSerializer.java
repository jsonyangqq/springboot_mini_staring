package com.example.serialize;

import com.example.serialize.domain.StudentProtos;

/**
 * @author jason.yang
 * @Description protobuf序列化
 * @Date 2022-07-17 22:16
 */
public class ProtobufSerializer implements SerializerItf<StudentProtos.Student>{

    @Override
    public byte[] serializeToFile(StudentProtos.Student student) throws Exception {
        StudentProtos.Student wrapperStudent = StudentProtos.Student.newBuilder().setName(student.getName()).setAge(student.getAge()).build();
        return wrapperStudent.toByteArray();
    }

    @Override
    public StudentProtos.Student deserializeFromFile(byte[] bytes) throws Exception {
        StudentProtos.Student student = StudentProtos.Student.parseFrom(bytes);
        return student;
    }
}