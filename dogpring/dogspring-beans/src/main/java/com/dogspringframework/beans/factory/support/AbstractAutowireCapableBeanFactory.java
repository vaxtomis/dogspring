package com.dogspringframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.PropertyValue;
import com.dogspringframework.beans.PropertyValues;
import com.dogspringframework.beans.factory.config.AutowireCapableBeanFactory;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * 继承 AbstractBeanFactory<br>
 * 此抽象类主要目的为实现 createBean 方法<br>
 * 可知 getBeanDefinition 还未实现
 *
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition bd, Object[] args) throws BeansException {
		Object bean = null;
		try {
			// 实例化 Bean 对象
			bean = createBeanInstance(bd, beanName, args);
			// 填充属性
			applyPropertyValues(beanName, bean, bd);
			// 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
			bean = initializeBean(beanName, bean, bd);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}
		// 对创建好的 bean 对象进行注册
		addSingleton(beanName, bean);
		return bean;
	}

	protected Object createBeanInstance(BeanDefinition bd, String beanName, Object[] args) {
		Constructor constructorToUse = null;
		Class<?> beanClass = bd.getBeanClass();
		Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
		for (Constructor ctor : declaredConstructors) {
			if (null != args && ctor.getParameterTypes().length == args.length) {
				constructorToUse = ctor;
				break;
			}
		}
		return getInstantiationStrategy().instantiate(bd, beanName, constructorToUse, args);
	}

	/**
	 * 应用给定的属性值，解析对此 bean 工厂中其他 bean 的任何运行时引用。
	 * 必须使用深拷贝，所以我们不会永久修改这个属性。
	 *
	 * @param beanName
	 * @param bean
	 * @param bd
	 */
	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition bd) {
		try {
			PropertyValues pvs = bd.getPropertyValues();
			for (PropertyValue pv : pvs.getPropertyValues()) {
				String name = pv.getName();
				Object value = pv.getValue();
				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = getBean(beanReference.getBeanName());
				}
				// TODO 属性注入
				BeanUtil.setFieldValue(bean, name, value);
			}
		} catch (Exception e) {
			throw new BeansException("Error setting property values：" + beanName);
		}
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}

	private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		// 1. 执行 BeanPostProcessor Before 处理
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

		// 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
		// 表示 bean 的 init 方法
		invokeInitMethods(beanName, wrappedBean, beanDefinition);

		// 2. 执行 BeanPostProcessor After 处理
		wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (null == current) {
				return result;
			}
			result = current;
		}
		return result;
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (null == current) {
				return result;
			}
			result = current;
		}
		return result;
	}
}
