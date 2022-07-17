package com.example.nettydemo.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

/**
 * @author jason.yang
 * @Description netty实现客户端发送消息
 * @Date 2022-07-16 15:37
 */
public class PacketNettyClient {

    public static void main(String[] args) {

        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors);
        bootstrap.channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                /**
                                 * 因为服务端那么我们是按照19个字节进行分割数据包的；
                                 * 19 * 9 = 171
                                 * lengthFieldLength为3的含义表示，每个包会少三个字节传输，可能会多几个包出来
                                 * 16 * 10 + 11(表示会多出来一个包)
                                 */
//                                .addLast(new LengthFieldPrepender(3, 0, false))
//                                .addLast(new FixedLengthFrameDecoder(36))
                                .addLast(new DelimiterBasedFrameDecoder(37, true, true, Unpooled.copiedBuffer("&".getBytes(StandardCharsets.UTF_8))))
                                .addLast(new SimpleClientHandler());
//                                .addLast(new LengthFieldPrepender(2,0,false))
//                                .addLast(new StringEncoder())
//                                .addLast(new ChannelInboundHandlerAdapter(){
//                                    @Override
//                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                        ctx.writeAndFlush("I am send first request.");
//                                        ctx.writeAndFlush("I am send second request.");
//                                    }
//                                });
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