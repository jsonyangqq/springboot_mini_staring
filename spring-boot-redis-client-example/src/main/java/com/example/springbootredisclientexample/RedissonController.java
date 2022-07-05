package com.example.springbootredisclientexample;

import com.example.springbootredisclientexample.utils.ResultsBean;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;



@RestController
public class RedissonController {
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/set")
    public ResultsBean<String> set(){
        redissonClient.getBucket("test").set(123);
        return ResultsBean.SUCCESS();
    }

    @GetMapping("/get")
    public ResultsBean<String> get(){
        return ResultsBean.SUCCESS(redissonClient.getBucket("test").get().toString());
    }

}
