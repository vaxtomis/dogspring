package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanDefinitionRegistry;
import com.dogspringframework.core.io.Resource;
import com.dogspringframework.core.io.ResourceLoader;

/**
 * BeanDefinitionReader 简单接口。使用资源和字符串位置参数指定加载方法。
 *
 * @author vaxtomis
 */
public interface BeanDefinitionReader {
	BeanDefinitionRegistry getRegistry();

	ResourceLoader getResourceLoader();

	void loadBeanDefinitions(Resource resource) throws BeansException;

	void loadBeanDefinitions(Resource... resources) throws BeansException;

	void loadBeanDefinitions(String location) throws BeansException;

}
