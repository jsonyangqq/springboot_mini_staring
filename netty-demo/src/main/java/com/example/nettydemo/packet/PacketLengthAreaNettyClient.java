package com.example.nettydemo.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @author jason.yang
 * @Description 基于长度域的解码器客户端
 * @Date 2022-07-16 15:37
 */
public class PacketLengthAreaNettyClient {

    public static void main(String[] args) {

        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors);
        bootstrap.channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 表示在传输数据的时候，在消息报文中增加4个字节的length
//                                .addLast(new LengthFieldPrepender(2, 0,false))
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        ByteBuf bufLength = ByteBufAllocator.DEFAULT.buffer();
                                        bufLength.writeInt(11);
                                        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,World".getBytes(StandardCharsets.UTF_8));
                                        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
                                        ctx.writeAndFlush(compositeByteBuf.addComponents(true, bufLength, byteBuf));
                                    }
                                });
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventExecutors.shutdownGracefully();
        }

    }

}