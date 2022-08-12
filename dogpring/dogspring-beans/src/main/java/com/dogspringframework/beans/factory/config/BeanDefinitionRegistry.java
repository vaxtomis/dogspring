package com.dogspringframework.beans.factory.config;

/**
 * @author vaxtomis
 */
public interface BeanDefinitionRegistry {
	void registerBeanDefinition(String beanName, BeanDefinition bd);
}
