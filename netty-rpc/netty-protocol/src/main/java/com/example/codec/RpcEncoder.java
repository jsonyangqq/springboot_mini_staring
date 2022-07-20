package com.example.codec;

import com.example.protocol.entity.Header;
import com.example.protocol.entity.RpcProtocol;
import com.example.serial.SerializerItf;
import com.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 制定数据编码器
 * @Date 2022-07-19 16:07
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("========== RpcEncoder=>encode开始编码 ==========");
        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());
        // 根据具体的序列化方式序列化对象，得到对应的字节流数据,然后进行传输
        SerializerItf serializer = SerializerManager.getSerializer(header.getSerialType());
        byte[] bytes = serializer.serializeToFile(msg.getContent());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }


}