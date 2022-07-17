package com.example.codec;

import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import com.example.serialize.ProtobufSerializer;
import com.example.serialize.SerializerItf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 消息数据编码
 * @Date 2022-07-17 13:33
 */
@Slf4j
public class MessageRecordEncord extends MessageToByteEncoder<MessageRecord> {

    /**
     * 消息编码我们可以通过java序列化进行编码成字节流
     * @param channelHandlerContext
     * @param messageRecord
     * @param outBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageRecord messageRecord, ByteBuf outBuf) throws Exception {
        log.info("MessageRecordEncord=>encode开始进行消息编码");
        Header header = messageRecord.getHeader();
        outBuf.writeLong(header.getSessionId());
        outBuf.writeByte(header.getReqType());
        // 消息体数据就需要进行具体的序列化操作了
        Object body = messageRecord.getBody();
        if(body != null) {
            SerializerItf itf = new ProtobufSerializer();
            byte[] bytes = itf.serializeToFile(body);
            outBuf.writeInt(bytes.length);
            outBuf.writeBytes(bytes);
        } else {
            outBuf.writeInt(0);
        }
    }
}