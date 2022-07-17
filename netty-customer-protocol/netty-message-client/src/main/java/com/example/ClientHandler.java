package com.example;

import com.example.constant.OpCode;
import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import com.example.serialize.domain.StudentProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jason.yang
 * @Description 自定义一个客户端Handler处理类处理客户端写事件
 * @Date 2022-07-17 16:25
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端输入数据写入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            // 这里如果不用protobuf序列化方式，这里的泛型就需要改成Student就ok了
            MessageRecord<StudentProtos.Student> record=new MessageRecord<>();
            Header header=new Header();
            header.setSessionId(100001L + i);
            header.setReqType(OpCode.REQ.code());
            record.setHeader(header);
            StudentProtos.Student student =  StudentProtos.Student.newBuilder()
                    .setName("zhangsan"+i)
                    .setAge(23)
                    .build();
            record.setBody(student);
            ctx.writeAndFlush(record);
        }
        super.channelActive(ctx);
    }

    /**
     * 读取服务端响应结果
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ClientHandler=>channelRead客户端准备接收服务端响应数据");
        MessageRecord messageRecord = (MessageRecord) msg;
        log.info("Client receive message: " + messageRecord);
        super.channelRead(ctx, msg);
    }
}