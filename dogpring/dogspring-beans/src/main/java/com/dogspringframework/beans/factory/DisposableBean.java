package com.dogspringframework.beans.factory;

/**
 * 一切准备就绪后，容器会检查 singleton 类型的 bean 实例。查看是否实现了 DisposableBean 接口，
 * 或者其对应的 bean 定义是否通过 <bean> 的 destroy-method 属性指定了自定义的销毁方法。<br>
 * 如果是，会为该实例注册一个用于对象销毁的回调（Callback），以便在这些 singleton 类型的
 * 对象实例销毁之前，执行销毁逻辑。<br><br>
 *
 * 自定义的对象销毁逻辑不会马上执行。只有该对象实例不再被使用时才会执行相关自定义销毁逻辑。<br><br>
 * 【对于 BeanFactory】<br>
 * 我们需要告知容器，在哪个时间点来执行对象的销毁方法。<br>
 * 如果不能在正确的时机调用 destroySinletons()，所有实现了 DisposableBean 接口的
 * 对象实例或者声明了 destroy-method 的 bean 的对象实例，不会执行销毁逻辑。<br><br>
 *
 * 【对于 ApplicationContext】<br>
 * AbstractApplicationContext 提供了 registerShutdownHook() 方法，
 * 底层使用标准的 Runtime 类的 addShutdownHook() 调用相应 bean 对象的销毁逻辑，
 * 从而保证在 JVM 退出之前，singleton 类型的 bean 对象实例的自定义销毁逻辑会执行。<br><br>
 *
 * 想要在销毁时释放资源的 bean 实现的接口。<br>
 * {@link BeanFactory} 将在单独销毁作用域 bean 时调用本接口 destroy() 方法。<br>
 * {@link com.dogspringframework.context.ApplicationContext} 应该在关闭时处理其所有单例，
 * 由应用程序生命周期驱动。<br><br>
 *
 * 出于同样的目的，Spring 管理的 bean 也可以实现 Java 的 {@link AutoCloseable} 接口。<br>
 * 作为一种可以指定自定义销毁方法的替代，例如在 XML BeanDefinition 中。<br>
 * 有关所有 bean 生命周期方法的列表，请参阅 {@link BeanFactory BeanFactory javadocs}。<br><br>
 *
 */
public interface DisposableBean {
	void destroy() throws Exception;
}
