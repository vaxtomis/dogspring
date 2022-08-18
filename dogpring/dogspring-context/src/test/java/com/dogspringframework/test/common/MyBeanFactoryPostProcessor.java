package com.dogspringframework.test.common;


import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.PropertyValue;
import com.dogspringframework.beans.PropertyValues;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanFactoryPostProcessor;
import com.dogspringframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
		PropertyValues propertyValues = beanDefinition.getPropertyValues();
		System.out.println("BeanFactoryPostProcessor 修改前为：" + propertyValues.getPropertyValue("company").getValue());
		propertyValues.addPropertyValue(new PropertyValue("company", "字节跳动"));
	}

}
