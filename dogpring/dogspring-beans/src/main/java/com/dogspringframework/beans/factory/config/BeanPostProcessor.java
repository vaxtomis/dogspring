package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.BeansException;

/**
 * BeanPostProcessor 的概念容易和 BeanFactoryPostProcessor 的概念混淆。<br>
 * 区别在于：<br>
 * BeanPostProcessor 是存在于对象实例化阶段（实例化后修改属性值），<br>
 * BeanFactoryPostProcessor 存在于容器启动阶段（实例化前修改 BD）。<br>
 * {@link BeanFactoryPostProcessor}
 * 调用 ConfigurableBeanFactory 的 addBeanPostProcessor() 方法
 * 注册自定义 BeanPostProcessor。
 * 对于 ApplicationContext，则将实现类通过通常 XML 配置即可（自动识别并加载注册）
 * 在下方 [注册] 有提到。<br><br>
 *
 * 允许自定义修改新创建 bean 实例的工厂钩子 —— 例如，检查标记接口或用代理包装 bean。<br><br>
 *
 * 通常，通过标记接口等填充 bean 的后处理器将实现 {@link #postProcessBeforeInitialization}，
 * 而使用代理包装 bean 的后处理器通常将实现 {@link #postProcessAfterInitialization}。<br><br>
 *
 * [注册]：<br>
 * ApplicationContext 可以在其 bean 定义中自动检测 BeanPostProcessor bean，
 * 并将这些后处理器应用于随后新建的任何 bean。
 * 一个普通的 BeanFactory 允许以编程方式注册后处理器，
 * 将它们应用于通过 BeanFactory 创建的所有 bean。<br><br>
 *
 * [顺序]：<br>
 * 在 ApplicationContext 中自动检测的 BeanPostProcessor bean，
 * 会根据 PriorityOrdered 和 Ordered 语义进行排序。
 * 相比之下，以编程方式注册到 BeanFactory 的 BeanPostProcessor bean 将按注册顺序应用；
 * 对于以编程方式注册的后处理器，通过实现 PriorityOrdered 或 Ordered 接口表达的任何排序语义都将被忽略。
 * 此外，@Order 注释不会被 BeanPostProcessor bean 考虑在内。<br><br>
 */
public interface BeanPostProcessor {
	/**
	 * 在 Bean 对象执行初始化方法之前，执行此方法
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

	/**
	 * 在 Bean 对象执行初始化方法之后，执行此方法
	 *
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
