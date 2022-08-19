package com.dogspringframework.context;

import com.dogspringframework.beans.BeansException;

/**
 * 配置和生命周期方法被封装在这里，以避免使它们对 ApplicationContext 客户端代码显而易见。
 * 本方法只应由启动和关闭代码使用。
 *
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
	/**
	 * 刷新容器
	 *
	 * @throws BeansException
	 */
	void refresh() throws BeansException;

	void registerShutdownHook();

	void close();
}
