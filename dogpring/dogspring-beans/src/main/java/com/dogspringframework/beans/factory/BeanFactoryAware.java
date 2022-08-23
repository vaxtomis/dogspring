package com.dogspringframework.beans.factory;

import com.dogspringframework.beans.BeansException;

/**
 * Spring 框架提供了一个 BeanFactoryAware 接口，
 * 容器在实例化 实现了该接口的 bean 的过程会自动将容器本身注入该 bean，
 * 这样该 bean 就持有了它所处的 BeanFactory 的引用 ——《Spring 揭秘》 P63
 *
 * <br><br>
 *
 * 持有 BeanFactory 之后就可以通过这个 BeanFactory 的引用去创建不同且独立的 Bean 对象
 *
 * <br><br>
 *
 * 实现此接口，能感知到 Bean 所属的 BeanFactory
 *
 * <br><br>
 *
 * 例如，bean 可以通过工厂查找依赖的 bean (依赖查询)
 * 请注意，大多数 bean 将选择通过相应的 bean 属性或构造函数参数接收依赖 bean (依赖注入)。
 *
 */
public interface BeanFactoryAware extends Aware {
	/**
	 * 将拥有工厂提供给 bean 实例的回调<br>
	 * 在填充普通 bean 属性之后但在初始化回调，
	 * 例如 {@link InitializingBean#afterPropertiesSet()} 或自定义初始化方法）之前调用。
	 */
	void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
