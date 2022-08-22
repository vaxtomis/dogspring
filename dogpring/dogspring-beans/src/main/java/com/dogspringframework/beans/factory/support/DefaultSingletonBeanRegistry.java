package com.dogspringframework.beans.factory.support;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.DisposableBean;
import com.dogspringframework.beans.factory.config.SingletonBeanRegistry;
import com.dogspringframework.util.PrintUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 共享 bean 实例的通用注册表，允许注册应为注册表的所有调用者共享的单例实例<br>
 * 这一分支主要负责 Bean 的注册相关功能保存在 HashMap 内<br>
 * 和 DefaultListableBeanFactory 不同的是
 * DefaultSingletonBeanRegistry 关注的是单例 Bean 对象的注册
 *
 * @author vaxtomis
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	private Map<String, Object> singletonObjects = new HashMap<>();

	private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}

	protected void addSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
	}

	public void registerDisposableBean(String beanName, DisposableBean bean) {
		disposableBeans.put(beanName, bean);
	}

	public void destroySingletons() {
		PrintUtils.print("--- 开始执行单例销毁流程 ---");
		Set<String> keySet = this.disposableBeans.keySet();
		Object[] disposableBeanNames = keySet.toArray();
		int count = 1;
		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			Object beanName = disposableBeanNames[i];
			DisposableBean disposableBean = disposableBeans.remove(beanName);
			try {
				PrintUtils.print("销毁第 " + count++ + " 个，名称为 " + beanName.toString(), 1);
				disposableBean.destroy();
			} catch (Exception e) {
				throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
			}
		}
		PrintUtils.print("--- 单例销毁流程执行完毕 ---");
	}
}
