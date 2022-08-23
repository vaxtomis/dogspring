import cn.hutool.core.io.IoUtil;
import com.dogspringframework.core.io.DefaultResourceLoader;
import com.dogspringframework.core.io.Resource;
import com.dogspringframework.util.PrintUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author vaxtomis
 */
public class ResourceLoaderTest {

	private DefaultResourceLoader resourceLoader;

	@Before
	public void init() {
		resourceLoader = new DefaultResourceLoader();
	}

	@Test
	public void test_classpath() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);
		System.out.println(content);
	}

	@Test
	public void test_file() throws IOException {
		Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);
		System.out.println(content);
	}

	/*@Test
	public void test_url() throws IOException {
		Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);
		System.out.println(content);
	}*/
}
