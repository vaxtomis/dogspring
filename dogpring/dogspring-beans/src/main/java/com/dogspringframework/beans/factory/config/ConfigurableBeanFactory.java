package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.factory.HierarchicalBeanFactory;

/**
 * 大多数 BeanFactory 要实现的配置接口。<br>
 * 除了 BeanFactory 接口中的 bean 工厂客户端方法之外，
 * 还提供了配置 bean 工厂的工具。
 * (这里没实现)
 *
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	String SCOPE_SINGLETON = "singleton";

	String SCOPE_PROTOTYPE = "prototype";

	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
