package com.mcgj.entity;

/**
 * �û��ղ�����ʵ����
 * @author ad
 *
 */
public class UserCollectionConversationChild extends BaseEntity{
	
	private Integer userId;//�û�id
	
	private Integer conversationChildId;//�ղص�����id

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConversationChildId() {
		return conversationChildId;
	}

	public void setConversationChildId(Integer conversationChildId) {
		this.conversationChildId = conversationChildId;
	}
	
}
