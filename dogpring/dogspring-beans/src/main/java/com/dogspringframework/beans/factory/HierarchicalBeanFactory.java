package com.dogspringframework.beans.factory;

/**
 * 由可以成为层次结构一部分的 bean 工厂实现的子接口。<br>
 *
 * <p>可以在 ConfigurableBeanFactory 接口中，
 * 找到允许以可配置方式设置父级的 bean 工厂的相应 {@code setParentBeanFactory} 方法。
 * (这里没实现)
 *
 * @author vaxtomis
 */
public interface HierarchicalBeanFactory extends BeanFactory {

}
