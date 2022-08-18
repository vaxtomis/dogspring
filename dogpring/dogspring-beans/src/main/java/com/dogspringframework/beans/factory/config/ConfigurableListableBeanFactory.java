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

	void preInstantiateSingletons() throws BeansException;
}
