package com.mcgj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ����ʵ����ĸ��࣬�ṩһЩ��������
 * @author ad
 *
 */
public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	protected Integer start;//��ʼҳ
	
	protected Integer limit;//����ҳ

	protected Date createDate =new Date();//����ʱ��
	
	protected Date modifyDate;//�޸�ʱ��
	
	protected String token;//�û���¼Ψһ��ʾ
	
	protected String verificationCode;//��֤��code,��������û�δ��¼��֤Ψһ��ʶ
	
	protected String checkCode;//У����,�û��������֤��
	
	protected Integer state=1;//״̬,Ĭ��Ϊ����
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}