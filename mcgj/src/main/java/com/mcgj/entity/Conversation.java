package com.mcgj.entity;
/**
 * ����(�Ự)ʵ����
 * @author ad
 *
 */
public class Conversation extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer createUserId;//�������ɵ��û�id
	
	private String conversationName;//���ɵ�����
	
	private Integer currentManageUserId;//��ǰ���������ɵ��û�id
	
	private Integer conversationType;//���ɵ����ͣ��ö�Ӧ�����ֽ��б�ʾ(1.����2.��Ӱ3.����4.����5.�Ƽ�6.�Ļ�7.��Ϸ...(δ�����))
	
	private String photo;//��Ƭ(����ͷ��)
	
	private Integer followUserNumber = 0;//�û�����

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

	public Integer getCurrentManageUserId() {
		return currentManageUserId;
	}

	public void setCurrentManageUserId(Integer currentManageUserId) {
		this.currentManageUserId = currentManageUserId;
	}

	public Integer getConversationType() {
		return conversationType;
	}

	public void setConversationType(Integer conversationType) {
		this.conversationType = conversationType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getFollowUserNumber() {
		return followUserNumber;
	}

	public void setFollowUserNumber(Integer followUserNumber) {
		this.followUserNumber = followUserNumber;
	}

}