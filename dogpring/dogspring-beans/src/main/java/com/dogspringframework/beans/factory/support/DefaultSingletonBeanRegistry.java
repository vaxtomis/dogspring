package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 共享 bean 实例的通用注册表，允许注册应为注册表的所有调用者共享的单例实例<br>
 * 这一分支主要负责 Bean 的注册相关功能保存在 HashMap 内<br>
 * 和 DefaultListableBeanFactory 不同的是
 * DefaultSingletonBeanRegistry 关注的是单例 Bean 对象的注册
 *
 * @author vaxtomis
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	private Map<String, Object> singletonObjects = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}

	protected void addSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
	}
}
