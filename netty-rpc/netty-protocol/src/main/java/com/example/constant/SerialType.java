package com.example.constant;

/**
 * @author jason.yang
 * @Description 序列化类型
 * @Date 2022-07-19 15:25
 */
public enum SerialType {

    JSON_SERIAL((byte)1),
    JAVA_SERIAL((byte)2);


    private byte code;

    SerialType(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return this.code;
    }

}