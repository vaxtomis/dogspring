package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 负责创建对应于根 bean 定义的实例的接口<br>
 * 由于各种方法都是可能的，因此将其纳入策略中，包括使用 CGLIB 动态创建子类以支持方法注入
 *
 * @author vaxtomis
 */
public interface InstantiationStrategy {
	Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
