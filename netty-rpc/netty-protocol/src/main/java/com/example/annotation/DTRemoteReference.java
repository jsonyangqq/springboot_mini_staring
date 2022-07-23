package com.example.annotation;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-22 14:40
 */

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface DTRemoteReference {

}