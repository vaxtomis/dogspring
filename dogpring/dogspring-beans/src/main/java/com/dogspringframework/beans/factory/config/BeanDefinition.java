package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.PropertyValues;

/**
 * BeanDefinition
 * 当前抽象成只包含 Class
 *
 * @author vaxtomis
 */
public class BeanDefinition {

	private Class beanClass;

	private PropertyValues propertyValues;

	/**
	 * 标记 init 方法名
	 */
	private String initMethodName;

	/**
	 * 标记 destroy 方法名
	 */
	private String destroyMethodName;

	public BeanDefinition(Class beanClass) {
		this.beanClass = beanClass;
		this.propertyValues = new PropertyValues();
	}

	public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
		this.beanClass = beanClass;
		this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public String getInitMethodName() {
		return initMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public String getDestroyMethodName() {
		return destroyMethodName;
	}

	public void setDestroyMethodName(String destroyMethodName) {
		this.destroyMethodName = destroyMethodName;
	}
}
