package com.example.codec;

import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import com.example.protocol.Student;
import com.example.serialize.ProtobufSerializer;
import com.example.serialize.SerializerItf;
import com.example.serialize.domain.StudentProtos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jason.yang
 * @Description 消息数据解码
 * @Date 2022-07-17 13:45
 */
@Slf4j
public class MessageRecordDecord extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("MessageRecordDecord=>decode消息数据解码开始");
        MessageRecord messageRecord = new MessageRecord();
        Header header = new Header();
        header.setSessionId(in.readLong());
        header.setReqType(in.readByte());
        header.setLength(in.readInt());
        messageRecord.setHeader(header);
        if(header.getLength() > 0) {
            // 把头部信息都读取完成了，就只身下body部分了
            byte[] contents = new byte[header.getLength()];
            in.readBytes(contents);
            SerializerItf itf = new ProtobufSerializer();
            StudentProtos.Student student = (StudentProtos.Student) itf.deserializeFromFile(contents);
            messageRecord.setBody(student);
            log.info("使用的序列化方式为: {},反序列化得到的结果：{}", itf, messageRecord);
            out.add(messageRecord);
        } else {
            log.info("消息内容为空");
        }
    }
}