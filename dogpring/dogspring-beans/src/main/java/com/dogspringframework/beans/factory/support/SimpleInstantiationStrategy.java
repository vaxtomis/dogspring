package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 在 BeanFactory 中使用的简单对象实例化策略<br>
 * 不支持方法注入，尽管它为子类提供钩子，使用重写来添加方法注入支持。例如可以重写方法
 *
 * @author vaxtomis
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
	@Override
	public Object instantiate(BeanDefinition bd, String beanName, Constructor ctor, Object[] args) throws BeansException {
		Class clazz = bd.getBeanClass();
		try {
			if (null != ctor) {
				return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
			} else {
				return clazz.getDeclaredConstructor().newInstance();
			}
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
		}
	}
}
