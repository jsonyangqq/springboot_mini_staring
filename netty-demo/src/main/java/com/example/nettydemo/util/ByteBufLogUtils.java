package com.example.nettydemo.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * @author jason.yang
 * @Description bytebuf日志记录工具类
 * @Date 2022-07-15 21:05
 */
public class ByteBufLogUtils {

    /**
     * 通过16进制打印出buffer的内容
     * @param buf
     */
    public final static void log(ByteBuf buf) {
        StringBuilder sb = new StringBuilder();
        sb.append(" read index: ").append(buf.readerIndex());
        sb.append(" write index: ").append(buf.writerIndex());
        sb.append(" capacity: ").append(buf.capacity());
        ByteBufUtil.appendPrettyHexDump(sb, buf);
        System.out.println(sb.toString());
    }

}