package com.example.codec;

import com.example.constant.OpCode;
import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import com.example.protocol.Student;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-17 13:53
 */
public class Test {

    public static void main(String[] args) {

        EmbeddedChannel channel=new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder
                        (1024*1024,
                                9,
                                4,
                                0,
                                0),
                new LoggingHandler(),
                new MessageRecordEncord(),
                new MessageRecordDecord()
        );

        //定义消息内容
        Header header=new Header();
        header.setSessionId(1234546L);
        header.setReqType(OpCode.REQ.code());
        MessageRecord record=new MessageRecord();
        record.setHeader(header);
        Student student = new Student();
        student.setName("张三");
        student.setAge(23);
//        record.setBody(student);

        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer();
        try {
            new MessageRecordEncord().encode(null,record,buf);
//            new MessageRecordDecord().decode(null, buf, new ArrayList<>());
              // 读取消息内容，进行解码

              // 方式一：直接写入，也就是进行解码操作
//            channel.writeInbound(buf);

            /**
             * 方式二：拆包后在进行解码(需要定义LengthFieldBasedFrameDecoder编解码器)
             * 进行数据包拆包
             * 这里进行拆包的时候，只要我们上面定义了“长度域编码器”，还是会按照我们编码器规则来进行读取包相关信息，就可以
             * 解决拆包粘包问题了
             */
            ByteBuf bb1=buf.slice(0,7); //数据包
            ByteBuf bb2=buf.slice(7,buf.readableBytes()-7); //数据包2
            // 增加引用计数
            bb1.retain();

            channel.writeInbound(bb1);
            channel.writeInbound(bb2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}