package com.yc.tieba.entity;

import java.util.List;

public class ConversationChild {
	
	private String ConversationChildName;//贴子名称
	
	private Integer conversationId;//贴吧id
	
	private Integer userId;//用户id
	
	private String content;//内容
	
	private List<ConversationChildChild> conversationChildChild;//贴子下的路层

	public String getConversationChildName() {
		return ConversationChildName;
	}

	public void setConversationChildName(String conversationChildName) {
		ConversationChildName = conversationChildName;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<ConversationChildChild> getConversationChildChild() {
		return conversationChildChild;
	}

	public void setConversationChildChild(List<ConversationChildChild> conversationChildChild) {
		this.conversationChildChild = conversationChildChild;
	}
	
}
