package com.dogspringframework.test.bean;

/**
 *
 */
public class UserServiceFBT {
	private String uId;
	private String company;
	private String location;
	private IUserDaoFBT iUserDaoFBT;

	public String queryUserInfo() {
		return iUserDaoFBT.queryUserName(uId) + "," + company + "," + location;
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

	public IUserDaoFBT getiUserDaoFBT() {
		return iUserDaoFBT;
	}

	public void setiUserDaoFBT(IUserDaoFBT iUserDaoFBT) {
		this.iUserDaoFBT = iUserDaoFBT;
	}
}
