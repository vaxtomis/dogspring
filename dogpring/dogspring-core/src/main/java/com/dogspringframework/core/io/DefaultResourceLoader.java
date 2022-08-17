package com.dogspringframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 首先检查路径是否以 classpath: 前缀打头，如果是则尝试构造 ClassPathResource 返回<br>
 * 否则，尝试通过 URL，根据资源路径来定位资源，没有抛出异常则构造 UrlResource 返回<br>
 * 如还是无法根据资源路径定位指定资源，则委托 getResourceByPath(String)(这里没实现)<br><br>
 *
 * @author vaxtomis
 */
public class DefaultResourceLoader implements ResourceLoader {

	@Override
	public Resource getResource(String location) {
		Assert.notNull(location, "Location must not be null");
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
		}
		else {
			try {
				URL url = new URL(location);
				return new UrlResource(url);
			} catch (MalformedURLException e) {
				return new FileSystemResource(location);
			}
		}
	}
}
