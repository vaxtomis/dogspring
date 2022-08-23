package com.dogspringframework.context.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.dogspringframework.beans.factory.support.DefaultListableBeanFactory;
import com.dogspringframework.util.PrintUtils;

/**
 * <p>{@link com.dogspringframework.context.ApplicationContext ApplicationContext} 实现的基类，
 * 它应该支持对 {@link #refresh()} 的多次调用，每次都创建一个新的内部 bean 工厂实例。
 * 通常（但不一定），这样的上下文将由一组配置位置驱动，以从中加载 bean 定义。
 *
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	private DefaultListableBeanFactory beanFactory;

	@Override
	protected void refreshBeanFactory() throws BeansException {
		PrintUtils.print("AC1.1 创建 DefaultListableBeanFactory");
		DefaultListableBeanFactory beanFactory = createBeanFactory();
		PrintUtils.print("AC1.2 加载 BeanDefinitions");
		loadBeanDefinitions(beanFactory);
		this.beanFactory = beanFactory;
	}

	private DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory();
	}

	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

	@Override
	protected ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}
}
