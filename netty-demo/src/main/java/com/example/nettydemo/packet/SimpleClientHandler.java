package com.example.nettydemo.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author jason.yang
 * @Description 简易版客户端处理器
 * @Date 2022-07-16 16:54
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接建立成功");
        for (int i = 0; i < 10; i++) {
            ByteBuf buf = Unpooled.copiedBuffer("客户端消息：" + i, Charset.forName("utf-8"));
            ctx.writeAndFlush(buf);
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到服务端返回的消息");
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("收到客户端的信息: " + message + "\r\n客户端收到的消息数量为: " +(++count));
        super.channelRead(ctx, msg);
    }
}