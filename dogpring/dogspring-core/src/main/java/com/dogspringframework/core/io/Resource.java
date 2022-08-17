package com.dogspringframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource 接口可以根据资源的不同类型，或者资源所处的不同场合，给出相应的具体实现。<br>
 * Spring 在此基础上提供了实现类：
 * {@link ByteArrayResource}：<br><br>
 * 将字节数组提供的数据作为一种资源进行封装，使用 InputStream 形式访问该类型的资源，
 * 该实现会根据字节数组的数据，构造相应的 ByteArrayInputStream 并返回。<br><br>
 *
 * {@link ClassPathResource}：
 * 从 ClassPath 中加载具体资源并进行封装。使用 ClassLoader 或者给定的类加载。<br><br>
 *
 * {@link FileSystemResource}
 * 对 java.io.File 类型的封装，我们可以以文件或者 URL 的形式对该类型资源进行访问。<br><br>
 *
 * {@link UrlResource}
 * 通过 java.net.URL 进行的具体资源查找定位的实现类，内部委派 URL 进行具体的资源操作。<br><br>
 *
 * {@link InputStreamResource}
 * 将给定的 InputStream 视为一种资源的 Resource 实现类。<br><br>
 *
 * 从底层资源的实际类型（例如文件或类路径资源）中抽象出来的资源描述符的接口。<br><br>
 *
 * 如果 InputStream 以物理形式存在，则可以为每个资源打开它，但只能为某些资源返回 URL 或文件句柄。
 * Resource 的具体操作是基于不同的实现的。(这里简单实现)
 * @author vaxtomis
 */
public interface Resource {
	InputStream getInputStream() throws IOException;
}
