package com.example.protocol;

import com.example.handler.RpcClienInitializer;
import com.example.protocol.entity.RpcProtocol;
import com.example.protocol.entity.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 定义我们的netty客户端
 * @Date 2022-07-20 10:00
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;

    private final EventLoopGroup eventLoopGroup;

    private String serverAddress;

    private int serverPort;

    public NettyClient(String serverAddress, int serverPort) {
        log.info("begin started netty client, serverAddress:{},serverPort:{}", serverAddress, serverPort);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                // 这里客户端是一个SocketChannel，而不是一个ServerSocketChannel，一定要记得哦
                .channel(NioSocketChannel.class)
                .handler(new RpcClienInitializer());
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(this.serverAddress, this.serverPort).sync();
        // 给future添加监听器
        future.addListener(listener -> {
           if(listener.isSuccess()) {
               log.info("Connect rpc server success,serverAddress:{}", this.serverAddress);
           } else {
               log.error("Connect rpc server fail,serverAddress:{}", this.serverAddress);
               future.cause().printStackTrace();
               eventLoopGroup.shutdownGracefully();
           }
        });
        // 写回数据
        future.channel().writeAndFlush(protocol);
    }

}