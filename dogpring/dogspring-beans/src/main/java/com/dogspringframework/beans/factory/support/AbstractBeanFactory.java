package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.beans.factory.config.ConfigurableBeanFactory;
import com.dogspringframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 简化版 AbstractBeanFactory 抽象类实现
 * <p>继承 DefaultSingletonBeanRegistry 负责注册相关功能<br>
 * 此抽象类内部关注实现 BeanFactory 的 getBean 方法<br>
 * 并新拓展两个抽象方法，用于获取 BeanDefinition 和 创建 Bean 对象
 *
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

	/**
	 * 在 createBean 中应用的 BeanPostProcessors
 	 */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

	/**
	 * 如有必要，使用 ClassLoader 解析 bean 类名
	 */
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return doGetBean(name, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return (T) getBean(name);
	}

	protected <T> T doGetBean(final String name, final Object[] args) {
		Object bean = getSingleton(name);
		if (bean != null) {
			return (T) bean;
		}

		BeanDefinition bd = getBeanDefinition(name);
		return (T) createBean(name, bd, args);
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition bd, Object[] args) throws BeansException;

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
		this.beanPostProcessors.remove(beanPostProcessor);
		this.beanPostProcessors.add(beanPostProcessor);
	}

	/**
	 * 返回将应用于使用此工厂创建的 bean 的 BeanPostProcessor 列表
	 */
	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}

	public ClassLoader getBeanClassLoader() {
		return this.beanClassLoader;
	}

}
