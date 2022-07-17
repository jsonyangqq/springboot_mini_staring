package com.example;

import com.example.codec.MessageRecordDecord;
import com.example.codec.MessageRecordEncord;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author jason.yang
 * @Description 自定义我们协议通信的客户端
 * @Date 2022-07-17 16:21
 */
public class NettyProtocolClient {

    public static void main(String[] args) {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        // 客户端这里不一样，这里就直接是一个SocketChannel了
        bootstrap.channel(NioSocketChannel.class)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(
                                        Integer.MAX_VALUE,
                                        9,
                                        4,
                                        0,
                                        0))
                                .addLast(new MessageRecordEncord())
                                .addLast(new MessageRecordDecord())
                                .addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}