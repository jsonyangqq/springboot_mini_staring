package com.example.nettydemo.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.Charset;

/**
 * @author jason.yang
 * @Description ByteBuf相关方法距离
 * @Date 2022-07-15 21:07
 */
public class NettyByteBufMethodExample {

    public static void main(String[] args) {

//        testWriteAndRead();

        sliceTest();


    }

    private static void sliceTest() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(new byte[]{1,2,3,4,5,6,7,8,9,10});
        ByteBufLogUtils.log(buf);

        //进行数据拆分
        ByteBuf buf1 = buf.slice(0,5);
        ByteBuf buf2 = buf.slice(5,5);
        ByteBufLogUtils.log(buf1);
        ByteBufLogUtils.log(buf2);

        System.out.println("修改原始数据后");
        // 把数据2修改成55，这里的55其实是10进制的55，10进制55对应的控制字符为'7'，16进制为37
        buf.setByte(1,55);
        ByteBufLogUtils.log(buf1);
    }

    /**
     * 测试ByteBuf中数据读写
     */
    private static void testWriteAndRead() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();
        byte[] bytes = new byte[]{1,2,3,4};
        buf.writeBytes(bytes);
        ByteBufLogUtils.log(buf);
        buf.writeInt(5);
        ByteBufLogUtils.log(buf);

        buf.readInt();
        ByteBufLogUtils.log(buf);

        buf.writeCharSequence("abc", Charset.defaultCharset());
        ByteBufLogUtils.log(buf);
    }


}