package com.dogspringframework.core.io;

/**
 * 资源查找定位策略的统一抽象。具体的资源查找策略由相应实现类给出。<br><br>
 *
 * 用于加载资源（例如类路径或文件系统资源）的策略接口。<br>
 * 需要 {@link org.springframework.context.ApplicationContext} 来提供此功能，
 * 以及扩展的 {@link org.springframework.core.io.support.ResourcePatternResolver} 支持。<br><br>
 *
 * 在 ApplicationContext 中运行时，
 * 可以使用特定上下文的资源加载策略从字符串填充类型为 Resource 和 Resource 数组的 Bean 属性。<br><br>
 *
 * @author vaxtomis
 */
public interface ResourceLoader {
	String CLASSPATH_URL_PREFIX = "classpath:";

	Resource getResource(String location);
}
