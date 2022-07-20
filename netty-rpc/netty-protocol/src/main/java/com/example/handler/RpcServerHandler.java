package com.example.handler;

import com.example.constant.ReqType;
import com.example.protocol.entity.Header;
import com.example.protocol.entity.RpcProtocol;
import com.example.protocol.entity.RpcRequest;
import com.example.protocol.entity.RpcResponse;
import com.example.spring.SpringBeanManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jason.yang
 * @Description 服务端字节流读取
 * 在接收到客户端数据进行解码后，读取解码后内容
 * @Date 2022-07-19 18:01
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
        Header header = msg.getHeader();
        header.setReqType(ReqType.RESPONSE.getCode());
        Object content = invoke(msg.getContent());
        responseRpcProtocol.setHeader(header);
        RpcResponse response = new RpcResponse();
        response.setCode(200);
        response.setMessage("Success");
        response.setObject(content);
        responseRpcProtocol.setContent(response);
        // 将数据返回给客户端
        ctx.writeAndFlush(responseRpcProtocol);
    }

    private Object invoke(RpcRequest rpcRequest) {
        try {
            Class<?> clazz = Class.forName(rpcRequest.getClassName());
            // 这里创建对象的方法，我们可以交给spring容器来管理
            Object bean = SpringBeanManager.getBean(clazz);
            Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamterTypes());
            return method.invoke(bean, rpcRequest.getParams());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}