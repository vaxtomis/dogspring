package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.BeanFactory;

/**
 * BeanFactory 接口的扩展，由能够自动装配的 bean 工厂实现，
 * 前提是他们希望为现有的 bean 实例公开此功能。
 * (这里没实现)
 *
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
	/**
	 * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

	/**
	 * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
