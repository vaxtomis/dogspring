package com.dogspringframework.context;

import com.dogspringframework.beans.factory.ListableBeanFactory;

/**
 * 为应用程序提供配置的中央接口。
 * ApplicationContext 在应用程序运行时是只读的，此接口的实现可能会重新加载。<br><br>
 * ApplicationContext 提供了：<br>
 * > 用于访问应用组件的 Bean 工厂方法。
 * 继承自接口 ListableBeanFactory<br>
 * > 以通用方式加载文件资源的能力。
 * 继承自接口 ResourceLoader<br>
 * > 能够将事件发布到注册的侦听器。
 * 继承自接口 ApplicationEventPublisher<br>
 * > 解析消息的能力，支持国际化（I18n）。
 * 继承自接口 MessageSource
 * 继承自父类上下文。后代上下文中的定义将始终优先（子中的定义大于父）。
 * 这意味着，单个父上下文可以被整个 Web 应用程序使用，
 * 而每个 servlet 都有自己的子上下文，该子上下文独立于任何其他 servlet 的子上下文。
 * <br><br>
 */
public interface ApplicationContext extends ListableBeanFactory {

}
