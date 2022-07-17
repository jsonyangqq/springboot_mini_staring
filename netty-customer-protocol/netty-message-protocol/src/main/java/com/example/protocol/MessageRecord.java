package com.example.protocol;

import lombok.Data;

/**
 * @author jason.yang
 * @Description 真正的消息报文体
 * @Date 2022-07-17 13:30
 */
@Data
public class MessageRecord<T> {

    private Header header;

    private T body;

}