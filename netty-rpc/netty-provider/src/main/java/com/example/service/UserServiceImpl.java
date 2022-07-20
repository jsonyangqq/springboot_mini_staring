package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-20 10:33
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {


    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}",name);
        return "save User success: "+name;
    }

}