package com.mcgj.entity;

import java.util.Date;

/**
 * ��¼��־ʵ����
 * @author ad
 *
 */
public class loginLog extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Date loginDate;//��¼ʱ��
	
	private String loginIp;//��¼��ip��ַ
	
	private Integer userId;//��¼���û�id

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
