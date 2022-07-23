package com.example.controller;

import com.example.annotation.DTRemoteReference;
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

    @DTRemoteReference
    private IUserService userService;


    @GetMapping("/saveUser")
    public String saveUser(@RequestParam("name") String name){
        return userService.saveUser(name);
    }

}