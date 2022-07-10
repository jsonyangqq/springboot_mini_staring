package com.example.redisnetworkmodel.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author jason.yang
 * @Description NIO实现非阻塞
 * @Date 2022-07-08
 */
public class NIOServerSocket {


    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); //设置连接非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            while(true){
                //是非阻塞的
                SocketChannel socketChannel=serverSocketChannel.accept(); //获得一个客户端连接
                if(socketChannel!=null){
                    socketChannel.configureBlocking(false);//IO非阻塞
                    ByteBuffer byteBuffer= ByteBuffer.allocate(1024);
                    int i;
                    while((i=socketChannel.read(byteBuffer)) != -1) {
                        if(i > 0) {
                            System.out.println("服务端接收到数据：" + new String(byteBuffer.array()));
                            Thread.sleep(10000);
                            byteBuffer.flip(); //反转
                            socketChannel.write(byteBuffer);
                        }
                        byteBuffer.clear();
                    }
                }else{
                    Thread.sleep(1000);
                    System.out.println("连接位就绪");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}