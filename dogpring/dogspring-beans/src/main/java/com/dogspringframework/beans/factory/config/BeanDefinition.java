package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.PropertyValues;

/**
 * BeanDefinition
 * 当前抽象成只包含 Class
 */
public class BeanDefinition {

	private final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

	private final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

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

	private String scope = SCOPE_SINGLETON;

	private boolean singleton = true;

	private boolean prototype = false;

	public BeanDefinition(Class beanClass) {
		this(beanClass, null);
	}

	public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
		this.beanClass = beanClass;
		this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
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

	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}

	public String getScope() {
		return scope;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public boolean isPrototype() {
		return prototype;
	}
}
