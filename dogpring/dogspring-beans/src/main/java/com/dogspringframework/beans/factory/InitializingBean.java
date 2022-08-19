package com.dogspringframework.beans.factory;

/**
 * 容器内部广泛使用的一个对象声明周期标识接口。<br>
 * 作用在于，在对象实例化过程调用 "BeanPostProcessor的前置处理" 后，
 * 会接着检测当前对象是否实现了 InitializingBean 接口。
 * 如果是，则会调用其 afterPropertiesSet() 方法进一步调整对象实例的状态。
 * 比如，有些情况下，某个业务对象实例化完成后还不能处于可使用状态，
 * 此时可以让该业务对象实现该接口，在方法 afterPropertiesSet() 中完成后续处理。<br><br>
 *
 * 注意，此接口虽然在 Spring 内部广泛应用，但是业务代码中使用侵入性较强，<br>
 * 推荐使用 init-method 属性。<br><br>
 *
 * 本接口需要被那些，需要在它们所有属性被 BeanFactory 设置后响应的 bean 实现：
 * 例如执行自定义初始化，或仅检查是否已设置所有必需属性。<br><br>
 *
 * 一种实现 InitializingBean 的替代方法是指定自定义 init 方法，如在 XML bean 定义中。
 * 有关所有 bean 生命周期方法的列表，请参阅 {@link BeanFactory BeanFactory javadocs}。
 */
public interface InitializingBean {
	/**
	 * Bean 处理了属性填充后调用
	 *
	 * @throws Exception
	 */
	void afterPropertiesSet() throws Exception;
}
