package com.mcgj.entity;

import java.util.Date;

/**
 * ����������ʵ����
 * @author ad
 *
 */
public class ConversationChild extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Integer conversationId;//���Ӷ�Ӧ������id

	private Integer userId;//���Ӵ�����id
	
	private String title;//���ӱ���
	
	private String content;//��������
	
	private Integer flow=0;//��������,Ĭ��ֵΪ0
	
	private String createUserName;//�������ӵ��û�����
	
	private String lastUserName;//���ظ����ӵ��û�����
	
	private Date lastDate;//�������Ļظ�ʱ��
	
	private Integer replyNumber;//��������
	
	private Integer conversationChildId;//��Ӧ������id
	
	public Integer getConversationChildId() {
		return conversationChildId;
	}

	public void setConversationChildId(Integer conversationChildId) {
		this.conversationChildId = conversationChildId;
	}

	public Integer getReplyNumber() {
		return replyNumber;
	}

	public void setReplyNumber(Integer replyNumber) {
		this.replyNumber = replyNumber;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getLastUserName() {
		return lastUserName;
	}

	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}
	
	
}
