package com.dogspringframework.context.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.context.ApplicationContext;
import com.dogspringframework.context.ApplicationContextAware;

/**
 * 应用程序上下文将自动将其注册到其底层 bean 工厂，应用程序不直接使用它
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

	private final ApplicationContext applicationContext;

	public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ApplicationContextAware){
			((ApplicationContextAware) bean).setApplicationContext(applicationContext);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
