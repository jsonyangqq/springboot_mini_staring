package com.example.redisnetworkmodel.reactor;

import com.example.redisnetworkmodel.reactor.multithread.MultipleThreadHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author jason.yang
 * @Description 连接事件处理器
 * @Date 2022-07-09
 */
public class Acceptor implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            System.out.println(socketChannel.getRemoteAddress() + ":收到一个客户端连接");
            socketChannel.configureBlocking(false);
            // 注册读事件  多线程Reactor模型和单线程Reactor替换，下面这里要替换，Reactor这个类处理对应的selector中channel事件
//            socketChannel.register(selector, SelectionKey.OP_READ, new SingleHandler(socketChannel));
            socketChannel.register(selector, SelectionKey.OP_READ, new MultipleThreadHandler(socketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}