package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.FactoryBean;
import com.dogspringframework.util.PrintUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持需要处理 {@link com.dogspringframework.beans.factory.FactoryBean FactoryBean} 实例的单例注册表的基类，与 {@link DefaultSingletonBeanRegistry} 的单例管理集成。
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

	protected Object getCachedObjectForFactoryBean(String beanName) {
		Object object = this.factoryBeanObjectCache.get(beanName);
		return (object != NULL_OBJECT ? object : null);
	}

	protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
		if (factory.isSingleton()) {
			PrintUtils.print(beanName + " 为单例，创建 Object 并加入 FactoryBeanObjectCache");
			Object object = this.factoryBeanObjectCache.get(beanName);
			if (object == null) {
				object = doGetObjectFromFactoryBean(factory, beanName);
				this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
			}
			return (object != NULL_OBJECT ? object : null);
		} else {
			PrintUtils.print(beanName + " 为原型，创建 Object");
			return doGetObjectFromFactoryBean(factory, beanName);
		}
	}

	private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
		try {
			return factory.getObject();
		} catch (Exception e) {
			throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
		}
	}
}
