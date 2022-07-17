package com.example.nettydemo.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author jason.yang
 * @Description ByteBuf的创建方式
 * @Date 2022-07-15 16:58
 */
public class NettyByteBufCreate {

    public static void main(String[] args) {

        // 普通创建方式
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf);
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.heapBuffer(10);
        System.out.println(buf1);
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.directBuffer(10);
        System.out.println(buf2);

    }

}