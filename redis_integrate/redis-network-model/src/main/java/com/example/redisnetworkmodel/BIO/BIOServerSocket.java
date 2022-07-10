package com.example.redisnetworkmodel.BIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-07
 */
public class BIOServerSocket {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("启动服务，服务端端口为：8080");
            while(true) {
                Socket socket = serverSocket.accept(); //建立连接，连接是阻塞式的
                System.out.println("客户端端口:" + socket.getPort());
                //读取客户端写入数据
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String readData = bufferedReader.readLine();
                System.out.println("收到客户端发送的信息为：" + readData);
                //将数据返回给客户端
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("receive a message：" + readData + "\n");
                //写完之后要继续刷新
                bufferedWriter.flush();
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