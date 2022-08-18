package com.dogspringframework.context.support;

import com.dogspringframework.beans.factory.support.DefaultListableBeanFactory;
import com.dogspringframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * <p>{@link com.dogspringframework.context.ApplicationContext ApplicationContext} 实现的方便基类，
 * 从包含 {@link com.dogspringframework.beans.factory.xml.XmlBeanDefinitionReader} 理解的 bean 定义的 XML 文档中绘制配置。
 *
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
		String[] configLocations = getConfigLocations();
		if (null != configLocations){
			beanDefinitionReader.loadBeanDefinitions(configLocations);
		}
	}

	protected abstract String[] getConfigLocations();
}
