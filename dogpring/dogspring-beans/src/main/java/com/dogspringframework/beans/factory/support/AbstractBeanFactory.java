package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.BeanFactory;
import com.dogspringframework.beans.factory.config.BeanDefinition;

/**
 * 简化版 AbstractBeanFactory 抽象类实现
 * <p>继承 DefaultSingletonBeanRegistry 负责注册相关功能<br>
 * 此抽象类内部关注实现 BeanFactory 的 getBean 方法<br>
 * 并新拓展两个抽象方法，用于获取 BeanDefinition 和 创建 Bean 对象
 *
 * @author vaxtomis
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return doGetBean(name, args);
	}

	protected <T> T doGetBean(final String name, final Object[] args) {
		Object bean = getSingleton(name);
		if (bean != null) {
			return (T) bean;
		}

		BeanDefinition beanDefinition = getBeanDefinition(name);
		return (T) createBean(name, beanDefinition, args);
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

}
