package com.example.handler;

import com.example.constant.RequestHolder;
import com.example.protocol.entity.RpcFuture;
import com.example.protocol.entity.RpcProtocol;
import com.example.protocol.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 客户端接收服务端响应真实数据
 * 接收服务端响应数据，待客户端解码完成后才执行下面方法
 * @Date 2022-07-19 18:03
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("RpcClientHandler=>channelRead0 receive Rpc Server Result");
        long requestId = msg.getHeader().getRequestId();
        // 本来是需要通过ctx.writeAndFlush()方式将响应数据返回给客户端的，使用Promise回调也是不错的选择
        RpcFuture<RpcResponse> future = RequestHolder.REQUEST_MAP.remove(requestId);
        //②.响应回调：表示客户端真正收到数据了，回调响应给客户端真正发送请求等待服务端响应的地方
        future.getPromise().setSuccess(msg.getContent());
    }
}