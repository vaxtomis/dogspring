package com.dogspringframework.core.io;

import cn.hutool.core.lang.Assert;
import com.dogspringframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类路径资源的 {@link Resource} 实现。<br>
 * 使用给定的 {@link ClassLoader} 或给定的 {@link Class} 来加载资源。<br><br>
 * 如果类路径资源驻留在文件系统中，则支持解析为 {@code java.io.File}，但不支持 JAR 中的资源。始终支持解析为 URL。
 *
 * @author vaxtomis
 */
public class ClassPathResource implements Resource {
	private final String path;

	private ClassLoader classLoader;

	public ClassPathResource(String path) {
		this(path, null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		Assert.notNull(path, "Path must not be null");
		this.path = path;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is = classLoader.getResourceAsStream(path);
		if (is == null) {
			throw new FileNotFoundException(
					this.path + " cannot be opened because it does not exist");
		}
		return is;
	}

}
