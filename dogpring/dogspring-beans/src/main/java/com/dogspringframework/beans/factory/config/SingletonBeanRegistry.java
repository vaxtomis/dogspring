package com.dogspringframework.beans.factory.config;

/**
 * @author vaxtomis
 */
public interface SingletonBeanRegistry {
	Object getSingleton(String beanName);
}
