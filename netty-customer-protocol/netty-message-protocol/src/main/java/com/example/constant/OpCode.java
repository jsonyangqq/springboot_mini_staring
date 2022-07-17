package com.example.constant;

/**
 * @author jason.yang
 * @Description 操作类型枚举
 * @Date 2022-07-17 12:06
 */
public enum OpCode {

    REQ((byte)0),
    RES((byte)1),
    PING((byte)2),
    PONG((byte)3);

    private byte code;

    OpCode(byte code){
        this.code=code;
    }

    public byte code(){
        return this.code;
    }

}