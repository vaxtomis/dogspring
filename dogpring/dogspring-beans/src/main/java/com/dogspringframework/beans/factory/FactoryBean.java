package com.dogspringframework.beans.factory;

/**
 * 由 {@link BeanFactory} 中使用的对象实现的接口，这些对象本身是单个对象的工厂。
 * 如果一个 bean 实现了这个接口，它就被当成工厂用于传出对象，而不是作为 bean 实例传出自己。
 *
 * <br><br>
 *
 * 实现此接口的 bean 不能用作普通 bean。<br>
 * FactoryBean 以 bean 样式定义，但通过 bean 依赖传出的对象始终是它创建的对象。
 *
 * <br><br>
 *
 * FactoryBean 可以支持单例和原型，并且可以根据需要懒加载创建对象。
 * {@link SmartFactoryBean} 接口允许公开更细粒度的行为元数据。
 *
 * <br><br>
 *
 * 该接口在框架本身中大量使用，例如用于 AOP ProxyFactoryBean
 * 或 JndiObjectFactoryBean。
 * 它也可以用于自定义组件；然而，这仅适用于基础设施代码。
 *
 * <br><br>
 *
 * 当某些对象的实例化过程过于繁琐，通过 XML 配置过于复杂，
 * 使我们宁愿使用 Java 代码来完成这个实例化过程的时候，
 * 或者，某些第三方库不能直接注册到 Spring 容器的时候，就可以实现这个接口，
 * 给出自己的对象实例化逻辑代码。
 *
 * <br><br>
 *
 * {@code FactoryBean} 是一个程序化合约，实现不应该依赖于注释驱动的注入或其他反射设施。
 * {@link #getObjectType()} {@link #getObject()} 调用可能会在引导过程的早期到达，
 * 甚至在任何后处理器设置之前。
 * 如果您需要访问其他 bean，请实现 {@link BeanFactoryAware} 并以编程方式获取它们。
 *
 * <br><br>
 *
 * 容器只负责管理 FactoryBean 实例的生命周期，而不是 FactoryBean 创建的对象的生命周期。
 * 因此，传出的 bean 对象上有一个 destroy 方法，
 * 例如 {@link java.io.Closeable#close() } 不会被自动调用。<br>
 * 对应的，失去 close() 方法后，FactoryBean 应该实现 {@link DisposableBean}
 * 并将关闭调用委托给底层对象（去实现）。
 *
 * <br><br>
 *
 * FactoryBean 对象参与包含 BeanFactory 的 bean 创建同步。
 * 除了 FactoryBean 本身（或类似的）内部的延迟初始化之外，通常不需要内部同步。
 */
public interface FactoryBean<T> {

	/**
	 *  返回该 FactoryBean 生产的对象实例。
	 *
	 *  <br><br>
	 *
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。
	 * 与 {@link BeanFactory} 一样，同时支持单例和原型设计模式。
	 * 如果此 FactoryBean 在执行时尚未完全初始化调用（例如因为它涉及循环引用），
	 * 抛出相应的异常 {@link FactoryBeanNotInitializedException}。
	 *
	 * <br><br>
	 *
	 * 从 Spring 2.0 开始，FactoryBeans 被允许返回 {@code null} 对象。
	 * 工厂会将此视为使用的正常值；在这种情况下它不会再抛出 FactoryBeanNotInitializedException 。
	 * 鼓励 FactoryBean 实现在适当的时候自己抛出 FactoryBeanNotInitializedException 。
	 */
	T getObject() throws Exception;

	/**
	 * 返回 getOject() 方法返回对象的类型。
	 *
	 * <br><br>
	 *
	 * 返回此 FactoryBean 创建的对象类型，如果无法确定，则返回 {@code null}。
	 * 这允许人们在不实例化对象的情况下检查 bean 的具体类型，例如在自动装配时。
	 *
	 * <br><br>
	 *
	 * 此方法应尽量避免创建单例的情况。对于原型，也建议在此处返回有意义的类型。
	 *
	 * <br><br>
	 *
	 * 可以在 FactoryBean 完全初始化之前调用此方法。
	 * 它不依赖于初始化期间创建的状态；当然，它仍然可以使用这种状态。
	 *
	 * <br><br>
	 *
	 * 注意：自动装配将忽略返回 {@code null} 的 FactoryBean。
	 * 因此，强烈建议使用 FactoryBean 的当前状态正确实现此方法。
	 */
	Class<?> getObjectType();

	boolean isSingleton();
}
