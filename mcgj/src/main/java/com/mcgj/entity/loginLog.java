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
	
	private String os;//�û�����ϵͳ
	
	private String browser;//�����
	
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

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
