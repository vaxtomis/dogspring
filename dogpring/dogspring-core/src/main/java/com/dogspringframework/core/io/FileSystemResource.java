package com.dogspringframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@code java.io.File} 和 {@code java.nio.file.Path} 的 {@link Resource} 实现处理文件系统目标。<br>
 * 支持解析为 {@code File} 和 {@code URL}。实现扩展的 WritableResource 接口。(这里没实现)
 *
 * @author vaxtomis
 */
public class FileSystemResource implements Resource {
	private final File file;

	private final String path;

	public FileSystemResource(File file) {
		this.file = file;
		this.path = file.getPath();
	}

	public FileSystemResource(String path) {
		this.file = new File(path);
		this.path = path;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	public final String getPath() {
		return this.path;
	}
}
