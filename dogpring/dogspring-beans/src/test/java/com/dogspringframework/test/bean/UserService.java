package com.dogspringframework.test.bean;

/**
 * @author vaxtomis
 */
public class UserService {
	private String uId;

	private UserDao userDao;

	public String queryUserInfo() {
		return userDao.queryUserName(uId);
	}
}
