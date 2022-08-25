package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.FactoryBean;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.beans.factory.config.ConfigurableBeanFactory;
import com.dogspringframework.util.ClassUtils;
import com.dogspringframework.util.PrintUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 简化版 AbstractBeanFactory 抽象类实现
 * <p>继承 DefaultSingletonBeanRegistry 负责注册相关功能<br>
 * 此抽象类内部关注实现 BeanFactory 的 getBean 方法<br>
 * 并新拓展两个抽象方法，用于获取 BeanDefinition 和 创建 Bean 对象
 *
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

	/**
	 * 在 createBean 中应用的 BeanPostProcessors
 	 */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

	/**
	 * 如有必要，使用 ClassLoader 解析 bean 类名
	 */
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return doGetBean(name, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return (T) getBean(name);
	}

	protected <T> T doGetBean(final String name, final Object[] args) {
		Object sharedInstance = getSingleton(name);
		if (sharedInstance != null) {
			// 如果是 FactoryBean，则需要调用 FactoryBean#getObject
			return (T) getObjectForBeanInstance(sharedInstance, name);
		}

		BeanDefinition beanDefinition = getBeanDefinition(name);
		Object bean = createBean(name, beanDefinition, args);
		return (T) getObjectForBeanInstance(bean, name);
	}

	private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
		if (!(beanInstance instanceof FactoryBean)) {
			PrintUtils.print(beanInstance + " 未实现 FactoryBean，直接返回创建的 Bean 实例");
			return beanInstance;
		}

		PrintUtils.print(beanInstance + " 实现 FactoryBean，查询缓存 FactoryBeanObjectCache");
		Object object = getCachedObjectForFactoryBean(beanName);

		if (object == null) {
			PrintUtils.print(beanName + " 不存在 FactoryBeanObjectCache 中，" + beanInstance +" 转换为 FactoryBean");
			FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
			object = getObjectFromFactoryBean(factoryBean, beanName);
		}
		PrintUtils.print("返回缓存中查询到或通过" + beanInstance + "创建的 Object: " + beanName);
		return object;
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	protected abstract Object createBean(String beanName, BeanDefinition bd, Object[] args) throws BeansException;

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
		this.beanPostProcessors.remove(beanPostProcessor);
		this.beanPostProcessors.add(beanPostProcessor);
	}

	/**
	 * 返回将应用于使用此工厂创建的 bean 的 BeanPostProcessor 列表
	 */
	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}

	public ClassLoader getBeanClassLoader() {
		return this.beanClassLoader;
	}

}
