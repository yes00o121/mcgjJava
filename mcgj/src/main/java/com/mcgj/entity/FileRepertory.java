package com.mcgj.entity;

/**
 * �ļ��ֿ�ʵ����
 * @author �
 * @date 219-03-01
 */
public class FileRepertory extends BaseEntity{
	
	private String source;//�ļ���Դ
	
	private String address;//�ļ���ַ
	
	private String mongodbId;//�ļ�mongoId
	
	
	public FileRepertory(){}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMongodbId() {
		return mongodbId;
	}

	public void setMongodbId(String mongodbId) {
		this.mongodbId = mongodbId;
	}

	public FileRepertory(String source, String address, String mongodbId) {
		super();
		this.source = source;
		this.address = address;
		this.mongodbId = mongodbId;
	}
	
}
