package com.example.redisnetworkmodel.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jason.yang
 * @Description select模型实现NIO
 * @Date 2022-07-09 15:12
 */
public class NIOSelectorServerSocket implements Runnable{

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public NIOSelectorServerSocket(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // 注册selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();
                // 获取所有的事件列表
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next());
                    //移除当前就绪的事件,以免重复获取
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 分发并处理所有列表事件
     * @param key
     */
    private void dispatch(SelectionKey key) throws IOException {
        if(key.isAcceptable()) {
            registryAcceptable(key);
        } else if(key.isReadable()) {
            registryRead(key);
        } else if(key.isWritable()) {
            registryWrite(key);
        }
    }

    private void registryAcceptable(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        // 获取连接之后注册可读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void registryRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        int i = socketChannel.read(buff);
        if(i > 0) {
            System.out.println("Server Receive Msg: " + new String(buff.array(), 0, i));
        }
        buff.clear();
    }

    private void registryWrite(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        buff.flip();
        socketChannel.write(buff);
        buff.clear();
    }

    public static void main(String[] args) throws IOException {
        NIOSelectorServerSocket nioSelectorServerSocket = new NIOSelectorServerSocket(8080);
        new Thread(nioSelectorServerSocket).start();
    }

}