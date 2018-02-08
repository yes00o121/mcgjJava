package com.mcgj.dao;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.UserCollectionConversationChild;

public interface ConversationChildChildMapper extends BaseService<ConversationChildChild,Integer>{
	
	/**
	 * ��������id��ѯ���µ�¥������
	 * @return
	 */
	List<Map<String,Object>> selectCCCByCCId(ConversationChildChild conversationChildChild);
	
	
	/**
	 * ��ѯ�û�Ϊ�鿴�����лظ���¥������
	 * @param userId
	 * @return
	 */
	List<ConversationChildChild> selectConversationChildChildReplyByUserId(Integer userId);
	
	/**
	 * �����û����е�δ�鿴¥�㶼�޸�Ϊ�鿴״̬
	 * @param userId
	 */
	void updateConversationChildChildReplyByUerId(Integer userId);
}
