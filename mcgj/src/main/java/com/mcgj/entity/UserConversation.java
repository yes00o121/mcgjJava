package com.mcgj.entity;
/**
 * �û���ע������ʵ����
 * @author ad
 *
 */
public class UserConversation extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer userId;//�û�id
	
	private Integer conversationId;//��ע������id
	
	private Integer grade;//���ɵȼ�

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}
