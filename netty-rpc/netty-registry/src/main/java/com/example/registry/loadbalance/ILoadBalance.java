package com.example.registry.loadbalance;

import java.util.List;

/**
 * @author jason.yang
 * @Description 对应的算法策略
 * @Date 2022-07-24 14:22
 */
public interface ILoadBalance<T> {

    T select(List<T> serverList);

}