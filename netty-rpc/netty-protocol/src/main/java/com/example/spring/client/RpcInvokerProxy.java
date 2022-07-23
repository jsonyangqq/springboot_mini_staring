package com.example.spring.client;

import com.example.constant.ReqType;
import com.example.constant.RequestHolder;
import com.example.constant.RpcConstantHelper;
import com.example.constant.SerialType;
import com.example.protocol.NettyClient;
import com.example.protocol.entity.*;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-22 14:52
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> reqProtocol=new RpcProtocol<>();
        long requestId= RequestHolder.REQUEST_ID.incrementAndGet();
        Header header=new Header(RpcConstantHelper.MAGIC, SerialType.JSON_SERIAL.getCode(),
                ReqType.REQUEST.getCode(),requestId,0);
        reqProtocol.setHeader(header);
        RpcRequest request=new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamterTypes(method.getParameterTypes());
        request.setParams(args);
        reqProtocol.setContent(request);

        NettyClient nettyClient=new NettyClient(host,port);
        //①.提供回调：这边添加一个reqeustId和Future对象包装的Promisse的映射关系，为了让客户端收到响应数据后能够及时返回
        RpcFuture<RpcResponse> future=new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId,future);
        nettyClient.sendRequest(reqProtocol);
        return future.getPromise().get().getObject();
    }

}