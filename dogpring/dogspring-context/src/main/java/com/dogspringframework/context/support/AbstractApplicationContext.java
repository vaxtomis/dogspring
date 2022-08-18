package com.dogspringframework.context.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanFactoryPostProcessor;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.dogspringframework.context.ConfigurableApplicationContext;
import com.dogspringframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * <p>{@link com.dogspringframework.context.ApplicationContext ApplicationContext} 接口的抽象实现。
 * 不强制要求用于配置的存储类型；简单地实现通用的上下文功能。使用模板方法设计模式，需要具体的子类来实现抽象方法。
 *
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	@Override
	public void refresh() throws BeansException {
		// 1. 创建 BeanFactory，并加载 BeanDefinition
		refreshBeanFactory();

		// 2. 获取 BeanFactory
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();

		// 3. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
		invokeBeanFactoryPostProcessors(beanFactory);

		// 4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
		registerBeanPostProcessors(beanFactory);

		// 5. 提前实例化单例Bean对象
		beanFactory.preInstantiateSingletons();
	}

	protected abstract void refreshBeanFactory() throws BeansException;

	protected abstract ConfigurableListableBeanFactory getBeanFactory();

	private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return getBeanFactory().getBean(name, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(name, requiredType);
	}
}
