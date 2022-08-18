package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.BeansException;

/**
 * 容器扩展机制<br>
 * 定义了 BD 注册后，实例化之前，一系列修改 BD 的操作<br><br>
 *
 * 在它标准初始化后，可用于修改 应用上下文内部 BeanFactory。
 * 所有的 bean definitions 将会被装载，但没有 bean 是已经实例化的。
 * 它允许覆盖或添加属性，可以对将要初始化的 bean 进行操作。<br><br>
 *
 */
public interface BeanFactoryPostProcessor {
	/**
	 * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
	 *
	 * @param beanFactory
	 * @throws BeansException
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
