package com.mcgj.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String userName;//�û�����
	
	private String account;//�˺�
	
	private Boolean admin = false;//�����û�
	
	private String password;//����
	
	private String photo;//�û�ͷ����Ƭ
	
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
//	static List<String> list = new ArrayList<String>();
	static List<String> list = Collections.synchronizedList(new ArrayList<String>());
	public static void main(String[] args) throws InterruptedException, IOException {
//		List<String> list = new ArrayList<String>();
		long start = new Date().getTime();
		System.out.println(Thread.currentThread().getId());
//		myTest m = new myTest();
		Thread m = new Thread(new myTest());
		m.start();
//		myTest m2 = new myTest();
		System.out.println("����");
//		m2.run();
//		m2.start();
		long end = new Date().getTime();
//		System.out.println(end-start);
		
	}
}
class myTest implements Runnable{

	private static int num = 0;
	public  myTest() {
		num++;
	}
	@Override
	public void run() {
		System.out.println(num+"�߳�ִ��...."+Thread.currentThread().getId());
	}
	
}