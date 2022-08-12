package com.dogspringframework.beans.factory.config;

/**
 * @author vaxtomis
 */
public class BeanReference {
	private final String beanName;

	public BeanReference(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}
}
