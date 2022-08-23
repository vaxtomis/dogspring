package com.dogspringframework.context;

import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.Aware;

/**
 *
 * 实现此接口，能够感知到 ApplicationContext
 *
 * <br><br>
 *
 * 当一个对象需要进入一组共同工作的 bean 时，实现此接口。
 * 通过 bean 引用进行配置优于仅出于 bean 查找目的实现此接口
 *
 * <br><br>
 *
 * 如果对象需要访问文件资源，即想要调用 {@code getResource}、
 * 想要发布应用程序事件或需要访问 MessageSource，也可以实现此接口。
 * 但是，在这样的特定场景中，最好实现更具体的 ResourceLoaderAware、
 * ApplicationEventPublisherAware 或 MessageSourceAware 接口
 *
 * <br><br>
 *
 * 请注意，文件资源依赖项也可以作为类型的bean属性{@link com.dogspringframework.core.io.Resource Resource}的bean属性，
 * 该案例是通过bean Factory通过带有自动类型转换的字符串填充的
 * 这消除了仅仅为了访问特定文件资源而实现任何回调接口的需要
 *
 */
public interface ApplicationContextAware extends Aware {

	/**
	 * 设置该对象运行的 ApplicationContext
	 * <br><br>
	 * 通常，此调用将用于初始化对象
	 * <br><br>
	 * 在填充普通 bean 属性之后但在初始化回调之前调用，
	 * 例如在 {@link com.dogspringframework.beans.factory.InitializingBean#afterPropertiesSet() afterPropertiesSet()}
	 * 或自定义初始化方法之前<br>
	 * 在 ResourceLoaderAwaresetResourceLoader、ApplicationEventPublisherAwaresetApplicationEventPublisher 和 MessageSourceAware（如果适用）之后调用。
	 */
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
