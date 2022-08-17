package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.factory.config.BeanDefinitionRegistry;
import com.dogspringframework.core.io.DefaultResourceLoader;
import com.dogspringframework.core.io.ResourceLoader;

/**
 * 实现 {@link BeanDefinitionReader} 接口的 bean 定义阅读器的抽象基类。
 *
 * @author vaxtomis
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	private final BeanDefinitionRegistry registry;

	private ResourceLoader resourceLoader;

	protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this(registry, new DefaultResourceLoader());
	}

	public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		this.registry = registry;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public BeanDefinitionRegistry getRegistry() {
		return registry;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
