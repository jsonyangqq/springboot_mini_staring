package com.example.spring.service;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author jason.yang
 * @Description 存储Bean实例，和对应的方法
 * @Date 2022-07-22 10:43
 */
@Data
public class BeanMethod {

    private Object bean;

    private Method method;

}