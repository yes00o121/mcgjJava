package com.mcgj.entity;
/**
 * 贴吧(会话)实体类
 * @author ad
 *
 */
public class Conversation extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer createUserId;//创建贴吧的用户id
	
	private String conversationName;//贴吧的名称
	
	private Integer currentManageUserId;//当前管理该贴吧的用户id
	
	private Integer conversationType;//贴吧的类型，用对应的数字进行表示(1.动漫2.电影3.明星4.体育5.科技6.文化7.游戏...(未完待续))
	
	private String photo;//照片(贴吧头像)
	
	private Integer followUserNumber = 0;//用户流量

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
