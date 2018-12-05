package com.yc.tieba.entity;

import java.util.List;

/**
 * 贴吧对象
 * @author Administrator
 *
 */
public class Conversaion {
	
	private String conversaionName;//贴吧名称
	
	private String autograph;//签名
	
	private String background;//背景图片
	
	private String cardBanner;//横幅图片
	
	private String photo;//贴吧图片
	
	private List<ConversationChild> conversaionChild;//贴子
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getConversaionName() {
		return conversaionName;
	}

	public void setConversaionName(String conversaionName) {
		this.conversaionName = conversaionName;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getCardBanner() {
		return cardBanner;
	}

	public void setCardBanner(String cardBanner) {
		this.cardBanner = cardBanner;
	}

	public List<ConversationChild> getConversaionChild() {
		return conversaionChild;
	}

	public void setConversaionChild(List<ConversationChild> conversaionChild) {
		this.conversaionChild = conversaionChild;
	}
	
}
