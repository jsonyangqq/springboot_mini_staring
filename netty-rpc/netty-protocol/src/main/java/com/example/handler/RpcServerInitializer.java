package com.example.handler;

import com.example.codec.RpcDecoder;
import com.example.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 服务端初始化各类编码器
 * @Date 2022-07-20 9:50
 */
@Slf4j
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("RpcServerInitializer begin init.");
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                Integer.MAX_VALUE,
                12,
                4,
                0,
                0))
                .addLast(new RpcDecoder())
                .addLast(new RpcEncoder())
                .addLast(new RpcServerHandler());
    }

}