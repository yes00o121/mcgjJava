package com.mcgj.entity;

public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	//���Ӧ�ֶ�

	private String userName;//�û�����
	
	private String account;//�˺�
	
	private Boolean admin = false;//�����û�
	
	private String password;//����
	
	private String photo;//�û�ͷ����Ƭ
	
	private String background;//�������ı���ͼƬ
	
	private String card;//��Ƭ���
	
	//��չ�ֶ�
	
	private String browser;//ʹ�������
	
	private String os;//�û�ϵͳ
	
	private String ip;//�û�ip
	
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
