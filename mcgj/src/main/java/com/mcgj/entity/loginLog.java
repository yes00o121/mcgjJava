package com.mcgj.entity;

import java.util.Date;

/**
 * 登录日志实体类
 * @author ad
 *
 */
public class loginLog extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Date loginDate;//登录时间
	
	private String loginIp;//登录的ip地址
	
	private Integer userId;//登录的用户id

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
