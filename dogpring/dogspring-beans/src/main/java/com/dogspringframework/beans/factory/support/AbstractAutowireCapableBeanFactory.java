package com.dogspringframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.PropertyValue;
import com.dogspringframework.beans.PropertyValues;
import com.dogspringframework.beans.factory.*;
import com.dogspringframework.beans.factory.config.AutowireCapableBeanFactory;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanPostProcessor;
import com.dogspringframework.beans.factory.config.BeanReference;
import com.dogspringframework.util.PrintUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 继承 AbstractBeanFactory<br>
 * 此抽象类主要目的为实现 createBean 方法<br>
 * 可知 getBeanDefinition 还未实现
 *
 * <br><br>
 *
 * 实现默认 bean 创建的抽象 bean 工厂超类，具有 {@link RootBeanDefinition} 类指定的全部功能。
 * 除了 AbstractBeanFactory 的 {@link createBean} 方法外，
 * 还实现了 {@link com.dogspringframework.beans.factory.config.AutowireCapableBeanFactory AutowireCapableBeanFactory} 接口
 *
 * <br><br>
 *
 * 提供 bean 创建（使用构造函数解析）、属性填充、连接（包括自动连接）和初始化。
 * 处理运行时 bean 引用、解析托管集合、调用初始化方法等。
 * 支持自动装配构造函数、按名称的属性和按类型的属性
 *
 * <br><br>
 *
 * 子类要实现的主要模板方法是{@link resolveDependency(DependencyDescriptor, String, Set , TypeConverter)}，
 * 用于按类型自动装配。
 * 如果工厂能够搜索其 bean 定义，匹配的 bean 通常将通过这样的搜索来实现。
 * 对于其他工厂风格，可以实现简化的匹配算法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition bd, Object[] args) throws BeansException {
		Object bean = null;
		System.out.println();
		PrintUtils.print(">>> 开始创建 Bean: " + beanName + "; Scope: " + bd.getScope() + " <<<");
		try {
			// 实例化 Bean 对象
			PrintUtils.print("1 创建 Bean 空对象");
			bean = createBeanInstance(bd, beanName, args);
			// 填充属性
			PrintUtils.print("2 为 Bean 填充属性");
			applyPropertyValues(beanName, bean, bd);
			// 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
			PrintUtils.print("3 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法");
			bean = initializeBean(beanName, bean, bd);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		PrintUtils.print("4 开始注册 DisposableBean");
		registerDisposableBeanIfNecessary(beanName, bean, bd);

		/**
		 * 单例模式和原型模式的区别就在于是否存放到内存中，
		 * 如果是原型模式那么就不会存放到内存中，每次获取都重新创建对象。
		 * 另外非 Singleton 类型的 Bean 不需要执行销毁方法
		 */
		// 判断 SCOPE_SINGLETON、SCOPE_PROTOTYPE，如为单例则对创建好的 bean 对象进行单例注册
		PrintUtils.print("5 判断 Scope，如为单例则对创建好的 bean 对象进行单例注册");
		if (bd.isSingleton()) {
			PrintUtils.print("注册单例 bean: " + beanName);
			addSingleton(beanName, bean);
		}
		PrintUtils.print(">>> 结束创建 Bean: " + beanName + "，返回: " + bean + " <<<");
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

	private Object initializeBean(String beanName, Object bean, BeanDefinition bd) {

		// PrintUtils.print("↓↓↓ " + beanName + " 开始执行 initializeBean 内部逻辑 ↓↓↓");

		// invokeAwareMethods
		PrintUtils.print("3.1 invokeAwareMethods 尝试识别 Aware");
		if (bean instanceof Aware) {
			if (bean instanceof BeanFactoryAware) {
				PrintUtils.print("3.1.1 BeanFactoryAware 被识别，setBeanFactory");
				((BeanFactoryAware) bean).setBeanFactory(this);
			}
			if (bean instanceof BeanClassLoaderAware){
				PrintUtils.print("3.1.2 BeanClassLoaderAware 被识别，setBeanClassLoader");
				((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
			}
			if (bean instanceof BeanNameAware) {
				PrintUtils.print("3-1.3 BeanNameAware 被识别，setBeanName");
				((BeanNameAware) bean).setBeanName(beanName);
			}
		}

		// 1. 执行 BeanPostProcessor Before 处理
		PrintUtils.print("3.2 BeanPostProcessor Before");
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

		// 2. 表示 bean 的 init 方法
		try {
			PrintUtils.print("3.3 invokeInitMethods");
			invokeInitMethods(beanName, wrappedBean, bd);
		} catch (Exception e) {
			throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
		}

		// 3. 执行 BeanPostProcessor After 处理
		PrintUtils.print("3.4 BeanPostProcessor After");
		wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

		// PrintUtils.print("↑↑↑ " + beanName + " initializeBean 内部逻辑结束 ↑↑↑");
		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object bean, BeanDefinition bd) throws Exception {
		// 1. 实现接口 InitializingBean
		PrintUtils.print("3.3.1 如果实现 InitializingBean 接口则调用 afterPropertiesSet");
		if (bean instanceof InitializingBean) {
			((InitializingBean) bean).afterPropertiesSet();
		}

		// 2. 配置信息 init-method {判断是为了避免二次执行销毁}
		String initMethodName = bd.getInitMethodName();
		PrintUtils.print("3.3.2 如果检测到配置自定义 init-method 则调用 " + initMethodName);
		if (StrUtil.isNotEmpty(initMethodName)) {
			Method initMethod = bd.getBeanClass().getMethod(initMethodName);
			initMethod.invoke(bean);
		}
	}

	protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition bd) {
		// 非 Singleton 类型的 Bean 不执行销毁方法
		if (!bd.isSingleton()) {
			PrintUtils.print(beanName + " 非 Singleton 类型，不执行销毁方法");
			return;
		}
		PrintUtils.print(beanName + " 注册 DisposableBean: " + bean.toString());
		if (bean instanceof DisposableBean || StrUtil.isNotEmpty(bd.getDestroyMethodName())) {
			registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, bd));
		}
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
