package com.mcgj.entity;

/**
 * �û���ע������ʵ����
 * @author ad
 */
public class UserFollowConversation extends BaseEntity{

	private Integer userId;//�û�id
	
	private Integer conversationId;//����id
	
	private String conversationName;//�û���ע����������
	
	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

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
	
	
}
