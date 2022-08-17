package com.dogspringframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * {@code java.net.URL} 定位器的 {@link Resource} 实现。
 * 在 {@code "file:"} 协议的情况下，支持作为 {@code URL} 和 {@code File} 的解析。<br><br>
 *
 * @author vaxtomis
 */
public class UrlResource implements Resource {
	private final URL url;

	public UrlResource(URL url) {
		Assert.notNull(url,"URL must not be null");
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection con = this.url.openConnection();
		try {
			return con.getInputStream();
		}
		catch (IOException ex){
			if (con instanceof HttpURLConnection){
				((HttpURLConnection) con).disconnect();
			}
			throw ex;
		}
	}
}
