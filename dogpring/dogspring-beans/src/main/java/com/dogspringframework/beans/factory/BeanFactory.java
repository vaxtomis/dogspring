package com.dogspringframework.beans.factory;


import com.dogspringframework.beans.BeansException;

/**
 * 简化版 BeanFactory 接口
 *
 * @author vaxtomis
 */
public interface BeanFactory {
	/**
	 * 通过 name 获取 Bean
	 */
	Object getBean(String name) throws BeansException;

	Object getBean(String name, Object ... args) throws BeansException;
}
