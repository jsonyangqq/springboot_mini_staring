package com.example.nettydemo.packet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * @author jason.yang
 * @Description 定义netty服务端，用于测试拆包粘包
 * @Date 2022-07-16 15:19
 */
public class PacketNettyServer {

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
//                                .addLast(new LengthFieldBasedFrameDecoder(
//                                1024 * 1024,
//                                0, 2,
//                                0, 2))
//                                .addLast(new StringDecoder())
                                .addLast(new FixedLengthFrameDecoder(19))
//                                .addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, ByteBufAllocator.DEFAULT.buffer().writeBytes("客户端消息".getBytes(StandardCharsets.UTF_8))))
                                .addLast(new SimpleServerHandler());
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