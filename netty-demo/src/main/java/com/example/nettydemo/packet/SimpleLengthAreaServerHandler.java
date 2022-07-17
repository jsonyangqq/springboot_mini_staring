package com.example.nettydemo.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author jason.yang
 * @Description 简单长度域服务处理器
 * @Date 2022-07-17 10:35
 */
public class SimpleLengthAreaServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务端接收到客户端写入数据: " + message);
    }

}