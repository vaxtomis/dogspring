package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @author vaxtomis
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
	@Override
	public Object instantiate(BeanDefinition bd, String beanName, Constructor ctor, Object[] args) throws BeansException {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(bd.getBeanClass());
		enhancer.setCallback(new NoOp() {
			@Override
			public int hashCode() {
				return super.hashCode();
			}
		});
		if (null == ctor) {
			return enhancer.create();
		}
		return enhancer.create(ctor.getParameterTypes(), args);
	}
}
