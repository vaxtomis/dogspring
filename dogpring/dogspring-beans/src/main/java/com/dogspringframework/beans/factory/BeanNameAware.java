package com.dogspringframework.beans.factory;

/**
 * bean 可以在 bean 工厂中了解其 beanName<br>
 * 请注意，通常不建议对象依赖于其 beanName，因为这表示对外部配置的潜在脆弱依赖，
 * 以及对 Spring API 的可能不必要的依赖。
 */
public interface BeanNameAware extends Aware {
	void setBeanName(String name);
}
