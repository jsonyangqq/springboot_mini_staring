package com.example.codec;

import com.example.constant.ReqType;
import com.example.constant.RpcConstantHelper;
import com.example.protocol.entity.Header;
import com.example.protocol.entity.RpcProtocol;
import com.example.protocol.entity.RpcRequest;
import com.example.protocol.entity.RpcResponse;
import com.example.serial.SerializerItf;
import com.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jason.yang
 * @Description 制定数据解码器 字节流=》对象
 * @Date 2022-07-19 16:15
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("========== RpcDecoder=>decode开始解码 ==========");
        // 如果header信息不完整，直接返回
        if(in.readableBytes() < RpcConstantHelper.HEADER_TOTAL_LENGTH) {
            return;
        }
        // 标记读取的开始的索引
        in.markReaderIndex();
        short magic = in.readShort();
        if(!RpcConstantHelper.MAGIC.equals(magic)) {
            throw new IllegalArgumentException("Illegal request param 'magic' " + magic);
        }
        byte serialType = in.readByte();
        byte reqType = in.readByte();
        long requestId = in.readLong();
        int dataLength = in.readInt();
        // 表示还未读完
        if(in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] contents = new byte[dataLength];
        in.readBytes(contents);
        Header header = new Header(magic, serialType, reqType, requestId, dataLength);
        SerializerItf serializer = SerializerManager.getSerializer(serialType);
        ReqType rt = ReqType.findByCode(reqType);
        switch (rt) {
            case REQUEST:
                RpcRequest rpcRequest = serializer.deserializeFromFile(contents, RpcRequest.class);
                RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();
                rpcProtocol.setHeader(header);
                rpcProtocol.setContent(rpcRequest);
                out.add(rpcProtocol);
                break;
            case RESPONSE:
                RpcResponse rpcResponse = serializer.deserializeFromFile(contents,RpcResponse.class);
                RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
                responseRpcProtocol.setHeader(header);
                responseRpcProtocol.setContent(rpcResponse);
                out.add(responseRpcProtocol);
                break;
            case HEARTBEAT:
                break;
            default:
                break;
        }

    }

}