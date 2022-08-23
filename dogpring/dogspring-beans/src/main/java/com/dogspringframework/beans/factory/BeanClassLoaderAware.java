package com.dogspringframework.beans.factory;

/**
 * 允许 bean 感知 bean 的 {@link ClassLoader class loader}；
 * 也就是当前bean工厂用来加载bean类的类加载器。
 */
public interface BeanClassLoaderAware extends Aware {

	/**
	 * 将 bean {@link ClassLoader class loader} 提供给 bean 实例的回调<br>
	 * 在普通 Bean 属性被调用之后，但在 afterPropertiesSet() 和 自定义 init 之前
	 */
	void setBeanClassLoader(ClassLoader classLoader);
}
