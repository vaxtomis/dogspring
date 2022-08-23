package com.dogspringframework.test.common;


import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.test.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if ("userService".equals(beanName)) {
			UserService userService = (UserService) bean;
			System.out.println("执行 BeanPostProcessor 修改：" + userService.getLocation());
			userService.setLocation("北京");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
