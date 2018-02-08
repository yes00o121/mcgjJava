package com.mcgj.web.websocket;

import com.mcgj.entity.MessageType;

/**
 * websocket��Ϣ����
 * @author ad
 *
 */
public class Message {
	
	private Object message;//��Ϣ��
	
	private MessageType msgType;//��Ϣ����,0¥��ظ���Ϣ
	
	public Message(){
		
	}

	public Message(Object message, MessageType msgType) {
		super();
		this.message = message;
		this.msgType = msgType;
	}



	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
	
	
	
}
