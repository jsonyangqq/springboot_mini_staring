package com.example.springbootredisclientexample.client;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-04
 */
public class CustomerRedisClient {

    private CustomerRedisClientSocket customerRedisClientSocket;

    public CustomerRedisClient(String host,int port) {
        customerRedisClientSocket=new CustomerRedisClientSocket(host,port);
    }

    public String auth(String value){
        customerRedisClientSocket.send(convertToCommand(CommandConstant.CommandEnum.AUTH, value.getBytes()));
        //在等待返回结果的时候，是阻塞的
        return customerRedisClientSocket.read();
    }

    public String set(String key,String value){
        customerRedisClientSocket.send(convertToCommand(CommandConstant.CommandEnum.SET,key.getBytes(),value.getBytes()));
        //在等待返回结果的时候，是阻塞的
        return customerRedisClientSocket.read();
    }

    public String get(String key){
        customerRedisClientSocket.send(convertToCommand(CommandConstant.CommandEnum.GET,key.getBytes()));
        //在等待返回结果的时候，是阻塞的
        return customerRedisClientSocket.read();
    }

    public static String convertToCommand(CommandConstant.CommandEnum commandEnum,byte[]... bytes){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(CommandConstant.START).append(bytes.length+1).append(CommandConstant.LINE);
        stringBuilder.append(CommandConstant.LENGTH).append(commandEnum.toString().length()).append(CommandConstant.LINE);
        stringBuilder.append(commandEnum).append(CommandConstant.LINE);
        for (byte[] by:bytes){
            stringBuilder.append(CommandConstant.LENGTH).append(by.length).append(CommandConstant.LINE);
            stringBuilder.append(new String(by)).append(CommandConstant.LINE);
        }
        return stringBuilder.toString();
    }
}
