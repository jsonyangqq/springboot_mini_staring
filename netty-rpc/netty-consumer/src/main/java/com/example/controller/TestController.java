package com.example.controller;

import com.example.RpcClientProxy;
import com.example.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason.yang
 * @Description 测试类
 * @Date 2022-07-20 15:06
 */
@RequestMapping("/test")
@RestController
public class TestController {

    private RpcClientProxy rcp=new RpcClientProxy();


    @GetMapping("/saveUser")
    public String saveUser(@RequestParam("name") String name){
        IUserService userService=rcp.clientProxy(IUserService.class,"127.0.0.1",8080);
        return userService.saveUser(name);
    }

}