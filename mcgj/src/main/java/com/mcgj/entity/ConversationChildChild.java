package com.mcgj.entity;
/**
 * ����¥��ʵ����
 * @author ad
 *
 */
public class ConversationChildChild extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer conversationChildId;//¥���Ӧ������id
	
	private Integer userId;//¥���û�id
	
	private String content;//¥��ظ�����
	
	private Integer isManage;//�Ƿ���¥��
	
	private Integer isLook = 0;//¥���Ƿ�鿴���ûظ�,Ĭ��Ϊû��
	
	private Boolean singleFloor;//ֻ��¥��
	
	private String conversationName;//¥���Ӧ����������
	
	private String title;//¥���Ӧ�����ӱ���
	
	private String userName;//¥���Ӧ���û�����
	
	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getSingleFloor() {
		return singleFloor;
	}

	public void setSingleFloor(Boolean singleFloor) {
		this.singleFloor = singleFloor;
	}

	public Integer getIsLook() {
		return isLook;
	}

	public void setIsLook(Integer isLook) {
		this.isLook = isLook;
	}

	public Integer getIsManage() {
		return isManage;
	}

	public void setIsManage(Integer isManage) {
		this.isManage = isManage;
	}

	public Integer getConversationChildId() {
		return conversationChildId;
	}

	public void setConversationChildId(Integer conversationChildId) {
		this.conversationChildId = conversationChildId;
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
}
