package com.dogspringframework.test;

import com.dogspringframework.beans.PropertyValue;
import com.dogspringframework.beans.PropertyValues;
import com.dogspringframework.beans.factory.config.BeanDefinition;
import com.dogspringframework.beans.factory.config.BeanReference;
import com.dogspringframework.beans.factory.support.DefaultListableBeanFactory;
import com.dogspringframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.dogspringframework.test.bean.UserDao;
import com.dogspringframework.test.bean.UserService;
import com.dogspringframework.util.PrintUtils;
import org.junit.Test;

public class ApiTest {

	@Test
	public void testBeanFactory() {
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2. UserDao 注册
		beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

		// 3. UserService 设置属性[uId、userDao]
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
		propertyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));

		// 4. UserService 注入bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 5. UserService 获取bean
		UserService userService = (UserService) beanFactory.getBean("userService");
		userService.queryUserInfo();
	}

	@Test
	public void testXmlLoader() {
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2.读取配置文件&注册bean
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");

		// 3.获取bean对象调用方法
		UserService userService = (UserService) beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		PrintUtils.print("测试结果：" + result);
	}

}
