package com.example.redisnetworkmodel.reactor;

import com.example.redisnetworkmodel.reactor.multithread.MultipleThreadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author jason.yang
 * @Description Reactor模型实现IO多路复用
 * @Date 2022-07-09
 */
public class Reactor implements Runnable{

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                selector.select(); //阻塞等待着事件就绪
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    dispatch(selectionKey);
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) {
        //先将对应的select上面的channel注册到对应的Handler上面
        Object attachment = key.attachment();
        // 多线程Reactor模型和单线程Reactor替换，下面这里要替换，还有Acceptor这个类注册读事件那里也需要替换
//        if(Objects.nonNull(attachment) && attachment instanceof SingleHandler) {
//            ((SingleHandler) key.attachment()).setKey(key);
//        }
        if(Objects.nonNull(attachment) && attachment instanceof MultipleThreadHandler) {
            ((MultipleThreadHandler) key.attachment()).setKey(key);
        }
        //会把对应的Acceptor事件和Handler事件进行注册到Selector上面
        Runnable runnable = (Runnable) key.attachment();
        if(runnable != null) {
            runnable.run();
        }
    }

}