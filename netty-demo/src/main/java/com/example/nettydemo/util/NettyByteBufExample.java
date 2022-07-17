package com.example.nettydemo.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.StandardCharsets;

/**
 * @author jason.yang
 * @Description netty中ByteBuf使用
 * @Date 2022-07-15 14:53
 */
public class NettyByteBufExample {


    public static void main(String[] args) {
        // 初始化一个容量大小为256的ByteBuf,可自动扩容
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println("========== before ==========");
        ByteBufLogUtils.log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("-"+i);
        }
        buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("========== after ==========");

        buf.readShort();  //读取二个字节
        buf.readByte();  //读取一个字节
        ByteBufLogUtils.log(buf);

    }

}