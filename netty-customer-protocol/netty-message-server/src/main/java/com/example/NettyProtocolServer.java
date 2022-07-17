package com.example;

import com.example.codec.MessageRecordDecord;
import com.example.codec.MessageRecordEncord;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author jason.yang
 * @Description 自定义我们的协议的服务启动端
 * @Date 2022-07-17 16:00
 */
public class NettyProtocolServer {

    public static void main(String[] args) {

        EventLoopGroup masterGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(masterGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                // 这里的9 表示偏移量为9，好比我有一个值“0123456789abc”，那么就是会从字符9开始往右边
                                .addLast(new LengthFieldBasedFrameDecoder(
                                        Integer.MAX_VALUE,
                                        9,
                                        4,
                                        0,
                                        0))
                                .addLast(new MessageRecordDecord())
                                .addLast(new MessageRecordEncord())
                                .addLast(new ServerHandler());
                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}