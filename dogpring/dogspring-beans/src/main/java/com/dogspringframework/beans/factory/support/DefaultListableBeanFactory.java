package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于 bean 定义元数据的成熟 bean 工厂，可通过后处理器进行扩展<br>
 * 主要关注点在 getBeanDefinition 方法中<br>
 * 内部包含了 BeanDefinitionMap 用于管理 BeanDefinition<br>
 * 并可以进行获取和新增操作
 *
 * @author vaxtomis
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition bd) {
		beanDefinitionMap.put(beanName, bd);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
		BeanDefinition bd = beanDefinitionMap.get(beanName);
		if (bd == null) {
			throw new BeansException("No bean named '" + beanName + "' is defined");
		}
		return bd;
	}

}
