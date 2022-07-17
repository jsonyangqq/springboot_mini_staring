package com.example.nettydemo.basicopr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author jason.yang
 * @Description netty实现主从Reactor模型
 * @Date 2022-07-13 15:33
 */
public class NettySingleReactorExample {


    public static void main(String[] args) {
        // 主线程
        EventLoopGroup masterGroup = new NioEventLoopGroup();
        // 工作线程组
//        EventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(workGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new NormalMessageHandler())
                                ;
                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(8080).sync();
            System.out.println("Netty Server Started Success: listener port: 8080");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            workGroup.shutdownGracefully();
            masterGroup.shutdownGracefully();
        }


    }

}