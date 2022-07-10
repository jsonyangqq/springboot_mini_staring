package com.example.redisnetworkmodel.reactor.singthread;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author jason.yang
 * @Description 处理读写等事件
 * @Date 2022-07-09
 */
public class SingleHandler implements Runnable{

    private SocketChannel socketChannel;
    private SelectionKey key;

    ByteBuffer inputBuffer=ByteBuffer.allocate(1024);
    ByteBuffer outputBuffer=ByteBuffer.allocate(1024);

    private StringBuilder sbBuffer = new StringBuilder();
    public SingleHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        try {
            if(this.key.isReadable()) {
                read();
            }else if(this.key.isWritable()) {
                write();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(this.key.channel() != null) {
                try {
                    this.key.channel().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理读事件channel
     * @throws IOException
     */
    private void read() throws IOException {
        inputBuffer.clear();
        int len = socketChannel.read(inputBuffer);
        if(inputComplete(len)) {
            System.out.println(Thread.currentThread().getName()+"------");
            System.out.println(socketChannel.getRemoteAddress() + "Server receive Msg: " + sbBuffer.toString());
            //将服务端接受数据回传给客户端输入流
            outputBuffer.put(sbBuffer.toString().getBytes(StandardCharsets.UTF_8));
            this.key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    /**
     * 处理写事件channel
     * @throws IOException
     */
    private void write() throws IOException {
        int write=-1;
        outputBuffer.flip(); //读写转换
        if(outputBuffer.hasRemaining()) {
            write = socketChannel.write(outputBuffer);
        }
        outputBuffer.clear();
        //客户端写入完毕，清空原始字符串的值
        sbBuffer.delete(0, sbBuffer.length());
        if(write <= 0) {
            this.key.channel().close();
        } else {
            socketChannel.write(ByteBuffer.wrap("\r\nreactor> ".getBytes(StandardCharsets.UTF_8)));
            this.key.interestOps(SelectionKey.OP_READ); // 又转变为读事件
        }
    }

    /**
     * 判断用户是否输入完成
     * @param bytes
     * @return
     * @throws EOFException
     */
    private boolean inputComplete(int bytes) throws EOFException {
        if(bytes > 0) {
            inputBuffer.flip(); //读写模式切换
            while (inputBuffer.hasRemaining()) {
                byte b = inputBuffer.get();
                if(b == 3) {  //表示 ctrl + c
                    throw new EOFException();
                }else if(b == '\r' || b == '\n') { //回车事件
                    sbBuffer.append((char)b);
                    return true;
                }else {
                    sbBuffer.append((char)b);
                }
            }
        } else if(bytes == 1) {
            throw new EOFException();
        }
        return false;
    }

}