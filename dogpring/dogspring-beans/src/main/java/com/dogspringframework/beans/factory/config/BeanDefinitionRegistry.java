package com.dogspringframework.beans.factory.config;

/**
 * 包含 bean 定义的注册表的接口，
 * 例如 RootBeanDefinition 和 ChildBeanDefinition 实例。<br>
 * 通常由内部使用 AbstractBeanDefinition 层次结构的 BeanFactories 实现。
 *
 * @author vaxtomis
 */
public interface BeanDefinitionRegistry {
	void registerBeanDefinition(String beanName, BeanDefinition bd);

	boolean containsBeanDefinition(String beanName);

	BeanDefinition getBeanDefinition(String beanName) throws Exception;

	String[] getBeanDefinitionNames();
}
