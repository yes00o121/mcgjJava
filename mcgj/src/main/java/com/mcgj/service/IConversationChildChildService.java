package com.mcgj.service;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.User;

/**
 * ¥��������ӿ�
 * @author ad
 *
 */
public interface IConversationChildChildService extends BaseService<ConversationChildChild,Integer>{
	
	/**
	 * ��������id��ѯ���µ�¥������
	 * @return
	 */
	Map<String,Object> selectCCCByCCId(ConversationChildChild conversationChildChild);
	
	/**
	 * ����¥�㷽��
	 * @return
	 */
	void addConversationChildChild(ConversationChildChild conversationChildChild);
	
	/**
	 * ��ѯ�û�Ϊ�鿴�����лظ���¥������
	 * @param conversationChildChild
	 * @return
	 */
	List<ConversationChildChild> selectConversationChildChildReplyByUserId(Integer userId);
	
	/**
	 * �����û����е�δ�鿴¥�㶼�޸�Ϊ�鿴״̬
	 * @param userId
	 */
	void updateConversationChildChildReplyByUerId(Integer userId);

}
