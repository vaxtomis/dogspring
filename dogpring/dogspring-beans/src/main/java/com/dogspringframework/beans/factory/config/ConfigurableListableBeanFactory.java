package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.ListableBeanFactory;

/**
 * 大多数 ListableBeanFactory 要实现的配置接口。<br>
 * 除了 {@link ConfigurableBeanFactory} 之外，
 * 它还提供了分析和修改 bean 定义以及预实例化单例的工具。
 * (这里没实现)
 *
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

	BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	/**
	 * 确保实例化所有非惰性初始化单例，
	 * 同时考虑 {@link com.dogspringframework.beans.factory.FactoryBean FactoryBean}。
	 * 如果需要，通常在工厂设置结束时调用。
	 */
	void preInstantiateSingletons() throws BeansException;
}
