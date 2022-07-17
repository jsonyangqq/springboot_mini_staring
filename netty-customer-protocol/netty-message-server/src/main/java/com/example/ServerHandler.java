package com.example;

import com.example.constant.OpCode;
import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 服务端消息读取
 * @Date 2022-07-17 16:11
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRecord messageRecord = (MessageRecord) msg;
        log.info("Server receive message: " + messageRecord);
        //服务端接收到消息后，然后响应给客户端
        Header header = messageRecord.getHeader();
        header.setReqType(OpCode.RES.code());
        messageRecord.setHeader(header);
        ctx.writeAndFlush(messageRecord);
    }
}