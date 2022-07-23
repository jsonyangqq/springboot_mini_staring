# 基于Netty实现RPC通信

## 1. 基于Netty实现自定义协议，集成可选序列化方式

## SpringBoot 自动配置失效了
遇到的一个比较严重的问题就是通过@EnableConfigurationProperties这种自动装配的方式注入值到
实现了BeanFactoryPostProcessor这个接口的类时，压根注入不进去，原因是BeanFactoryPostProcessor
这个接口方法postProcessBeanFactory的时机是Bean定时时，而非Bean实例化的时候，所以一只会注入不上去
这里试了很多种方法，都不能够绕过这个限制
所以了解Spring的启动流程和Bean的装载过程很重要，不然你会一只觉得有些东西是有可能的，不过却是也可能还有
其它解决这个问题的方法
我们这里使用的是Environment获取全局配置的方式获取的这个可以在BeanFactoryPostProcessor组装Bean定义
前得到对应的Environment对应的属性值。



## 2. 定义远程通信接口，实现服务注册和发现(集成zookeeper实现注册中心)

## 3. 可动态伸缩配置中心