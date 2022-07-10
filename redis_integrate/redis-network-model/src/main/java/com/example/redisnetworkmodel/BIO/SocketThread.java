package com.example.redisnetworkmodel.BIO;

import java.io.*;
import java.net.Socket;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-07 22:31
 */
public class SocketThread implements Runnable{

    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        BufferedWriter bufferedWriter = null;
        try {
            //读取客户端写入数据
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("收到客户端发送的信息为：" + line);
                //将数据返回给客户端
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("receive a message：" + line + "\n");
                //写完之后要继续刷新
                bufferedWriter.flush();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}