package com.dogspringframework.context.support;

import com.dogspringframework.beans.BeansException;

/**
 * <p>独立的 XML 应用程序上下文，
 * 从类路径中获取上下文定义文件，将普通路径解释为包含包路径的类路径资源名称（例如“mypackagemyresource.txt”）。
 * 对于测试工具以及嵌入在 JAR 中的应用程序上下文很有用。
 *
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	private String[] configLocations;

	public ClassPathXmlApplicationContext() {
	}

	/**
	 * 从 XML 中加载 BeanDefinition，并刷新上下文
	 *
	 * @param configLocations
	 * @throws BeansException
	 */
	public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
		this(new String[]{configLocations});
	}

	/**
	 * 从 XML 中加载 BeanDefinition，并刷新上下文
	 * @param configLocations
	 * @throws BeansException
	 */
	public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
		this.configLocations = configLocations;
		refresh();
	}

	@Override
	protected String[] getConfigLocations() {
		return configLocations;
	}
}
