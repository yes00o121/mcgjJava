package com.mcgj.entity;
/**
 * ��Ϣ����ö��
 * @author ad
 *
 */
public enum MessageType {
	
	reply("�ظ�"), notice("֪ͨ"), rivateChat("˽��");

	private String desc;
	
	private MessageType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return this.desc;
	}

}
