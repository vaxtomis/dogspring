package com.dogspringframework.beans.factory.config;

import com.dogspringframework.beans.factory.BeanFactory;

/**
 * BeanFactory 接口的扩展，由能够自动装配的 bean 工厂实现，
 * 前提是他们希望为现有的 bean 实例公开此功能。
 * (这里没实现)
 *
 * @author vaxtomis
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
}
