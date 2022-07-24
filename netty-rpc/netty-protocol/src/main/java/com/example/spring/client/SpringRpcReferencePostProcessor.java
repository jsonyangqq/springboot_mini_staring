package com.example.spring.client;

import com.example.annotation.DTRemoteReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jason.yang
 * @Description bean后置处理器
 * @Date 2022-07-22 14:56
 */
@Slf4j
public class SpringRpcReferencePostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor, PriorityOrdered {

    private ClassLoader classLoader;

    private ApplicationContext applicationContext;

    private String registryAddress;

    private byte registry;

    /**
     * 保存发布时使用的Bean的信息
     */
    public final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public SpringRpcReferencePostProcessor(String registryAddress, byte registry) {
        log.info("registry SpringRpcReferencePostProcessor construct.");
        this.registryAddress = registryAddress;
        this.registry = registry;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

        @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //记载所有bean的定义保存到beanDefinitionMap中
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if(beanClassName != null) {
                Class<?> aClass = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                ReflectionUtils.doWithFields(aClass, this::parseRpcReference);
            }
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        //将Bean的定义注册
        this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if(applicationContext.containsBean(beanName)) {
                log.warn("SpringContext already registry the bean: {}",beanName);
                return;
            }
            registry.registerBeanDefinition(beanName, beanDefinition);
            log.info("registry SpringRpcReferenceBean {} success.", beanName);
        });
    }

    private void parseRpcReference(Field field) {
        DTRemoteReference dtRemoteReference = AnnotationUtils.getAnnotation(field, DTRemoteReference.class);
        if(dtRemoteReference != null) {
            // 构建beanDefinition
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SpringRpcReferenceBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("registryAddress",this.registryAddress);
            builder.addPropertyValue("registry",this.registry);
            BeanDefinition beanDefinition = builder.getBeanDefinition();
            beanDefinitionMap.put(field.getName(), beanDefinition);
        }
    }

    @Override
    public int getOrder() {
        return 500;
    }
}