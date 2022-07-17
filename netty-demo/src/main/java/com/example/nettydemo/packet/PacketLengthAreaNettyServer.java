package com.example.nettydemo.packet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author jason.yang
 * @Description 基于长度域的解码器服务端
 * @Date 2022-07-17 10:06
 */
public class PacketLengthAreaNettyServer {

    public static void main(String[] args) {

        EventLoopGroup masterGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 这里的4表示用4个字节长度来表示“报文体”长度
                                .addLast(new LengthFieldBasedFrameDecoder(
                                1024 * 1024, 0,
                                        4, 0, 0))
                                .addLast(new SimpleLengthAreaServerHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}