package com.dogspringframework.beans.factory.config;

/**
 * BeanDefinition
 * 当前抽象成只包含 Class
 *
 * @author vaxtomis
 */
public class BeanDefinition {
	private Class beanClass;

	public BeanDefinition(Class beanClass) {
		this.beanClass = beanClass;
	}

	public Class getBeanClass() {
		return beanClass;
	}
}
