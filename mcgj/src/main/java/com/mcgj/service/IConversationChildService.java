package com.mcgj.service;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.UserCollectionConversationChild;

public interface IConversationChildService extends BaseService<ConversationChild, Integer>{
	
	/**
	 * ��������id��ѯ,��ȡ��ǰ���ӵĵ�һ������
	 * @return
	 */
	public Map<String,Object> selectConversationChildById(Integer id);
	
	/**
	 * ��������id��ѯ����
	 * @return
	 */
	public Map<String,Object> selectConversationChildByConversationId(ConversationChild conversationChild);
	
	/**
	 * �������ӵķ���
	 * @param conversationChild
	 */
	public void addConversationChild(ConversationChild conversationChild);
	
	/**
	 * �����ղط���
	 */
	void addConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * ɾ���ղ����ӷ���
	 * @return
	 */
	void deleteConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * �����û�Id���������Ʋ�ѯ�ղ�����
	 * @param userCollectionConversationChild
	 * @return
	 */
	UserCollectionConversationChild selectConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * ��ѯ�û������������� 
	 * @return
	 */
	List<ConversationChild> selectUserPublishConversationChild(ConversationChild conversationChild);
	
}

