package com.example.redisnetworkmodel.BIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jason.yang
 * @Description 线程池的方式实现连接数量控制
 * @Date 2022-07-07 22:29
 */
public class BIOServerSocketWithMulThread {


    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("启动服务，服务端端口为：8080");
            while(true) {
                Socket socket = serverSocket.accept(); //建立连接，连接是阻塞式的
                System.out.println("客户端端口:" + socket.getPort());
                executorService.execute(new SocketThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}