package com.example.nettydemo.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author jason.yang
 * @Description 服务端接收客户端请求
 * @Date 2022-07-16 15:33
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务端接收到客户端写入数据: " + message);
        //写回数据到客户端
        ByteBuf buf = Unpooled.copiedBuffer(String.join("&",UUID.randomUUID().toString(), ""+(++count)).getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(buf);
    }
}