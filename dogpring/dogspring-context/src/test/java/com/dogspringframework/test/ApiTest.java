package com.dogspringframework.test;

import com.dogspringframework.beans.factory.support.DefaultListableBeanFactory;
import com.dogspringframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.dogspringframework.test.bean.UserService;
import com.dogspringframework.test.common.MyBeanFactoryPostProcessor;
import com.dogspringframework.test.common.MyBeanPostProcessor;
import org.junit.Test;

public class ApiTest {
	@Test
	public void testBeanFactoryPostProcessorAndBeanPostProcessor(){
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2. 读取配置文件&注册Bean
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");

		// 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
		MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
		beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

		// 4. Bean实例化之后，修改 Bean 属性信息
		MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
		beanFactory.addBeanPostProcessor(beanPostProcessor);

		// 5. 获取Bean对象调用方法
		UserService userService = beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);
	}
}
