package com.dogspringframework.test.bean;


import com.dogspringframework.beans.BeansException;
import com.dogspringframework.beans.factory.*;
import com.dogspringframework.context.ApplicationContext;
import com.dogspringframework.context.ApplicationContextAware;

public class UserService implements InitializingBean, DisposableBean, ApplicationContextAware, BeanFactoryAware, BeanNameAware, BeanClassLoaderAware {

	private ApplicationContext applicationContext;

	private BeanFactory beanFactory;

	private String uId;
	private String company;
	private String location;
	private UserDao userDao;

	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("执行：UserService.destroy");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("执行：UserService.afterPropertiesSet");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("Bean Name is：" + name);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("ClassLoader：" + classLoader);
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
}
