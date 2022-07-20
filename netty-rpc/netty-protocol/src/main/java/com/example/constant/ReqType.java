package com.example.constant;

/**
 * @author jason.yang
 * @Description 请求类型枚举
 * @Date 2022-07-18 11:58
 */
public enum ReqType {

    REQUEST((byte)1),
    RESPONSE((byte)2),
    HEARTBEAT((byte)3);

    private byte code;

    ReqType(byte code){
        this.code=code;
    }

    public byte getCode(){
        return this.code;
    }

    public static ReqType findByCode(int code){
        for(ReqType reqType: ReqType.values()){
            if(reqType.code==code){
                return reqType;
            }
        }
        return null;
    }

}