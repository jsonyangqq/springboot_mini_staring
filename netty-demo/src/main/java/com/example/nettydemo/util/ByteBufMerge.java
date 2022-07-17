package com.example.nettydemo.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author jason.yang
 * @Description ByteBuf合并
 * @Date 2022-07-16 14:59
 */
public class ByteBufMerge {

    public static void main(String[] args) {

//        twoDataCopty();

        onceDataCopy();

    }

    /**
     * 合并二个buf需要二次数据拷贝的方式
     */
    private static void twoDataCopty() {
        ByteBuf bufHeader = ByteBufAllocator.DEFAULT.buffer();
        bufHeader.writeCharSequence("header", CharsetUtil.UTF_8);
        ByteBuf bufBody = ByteBufAllocator.DEFAULT.buffer();
        bufBody.writeCharSequence("body", CharsetUtil.UTF_8);

        ByteBuf buffer = Unpooled.buffer(bufHeader.readableBytes() + bufBody.readableBytes());
        buffer.writeBytes(bufHeader);
        buffer.writeBytes(bufBody);
        ByteBufLogUtils.log(buffer);
    }

    private static void onceDataCopy() {
        ByteBuf bufHeader = ByteBufAllocator.DEFAULT.buffer();
        bufHeader.writeCharSequence("header", CharsetUtil.UTF_8);
        ByteBuf bufBody = ByteBufAllocator.DEFAULT.buffer();
        bufBody.writeCharSequence("body", CharsetUtil.UTF_8);
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        // 第一个参数是是否递增写的index的标识，true标识递增写index; false表示不递增，那么会读不到任何数据
        compositeByteBuf.addComponents(true, bufHeader, bufBody);
        ByteBufLogUtils.log(compositeByteBuf);
    }

}