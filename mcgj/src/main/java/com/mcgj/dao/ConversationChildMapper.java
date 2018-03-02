package com.mcgj.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.User;
import com.mcgj.entity.UserCollectionConversationChild;

public interface ConversationChildMapper extends BaseService<ConversationChild,Integer>{
	
	/**
	 * ��������id��ѯ,��ȡ��ǰ���ӵĵ�һ������
	 * @return
	 */
	public Map<String,Object> selectConversationChildById(Integer id);
	
	/**
	 * ��������id��ѯ����
	 * @return
	 */
	public List<ConversationChild> selectConversationChildByConversationId(ConversationChild conversationChild);
	
	/**
	 * �������ӵķ���
	 * @param conversationChild
	 */
	public void insert(ConversationChild conversationChild);
	
	
	/**
	 * �������ӵ�id��ȡ�û�¥��������
	 * @return
	 */
	public User selectUserByconversationChildId(Integer conversationChildId);
	
	/**
	 * �����ղط���
	 * @param userCollectionConversationChild
	 */
	void addConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * ɾ���ղط���
	 * @param userCollectionConversationChild
	 * @return
	 */
	int deleteConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * ��ѯ�����ղط���
	 * @param userCollectionConversationChild
	 * @return
	 */
	UserCollectionConversationChild selectConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * �����û�id���ղص���������
	 * @param userId
	 * @return
	 */
	List<ConversationChild> selectCollectionConversationChildByUserId(Integer userId);
	
	/**
	 * ��ѯ�û������������� 
	 * @param conversationChild
	 * @return
	 */
	List<ConversationChild> selectUserPublishConversationChild(ConversationChild conversationChild);
	
	/**
	 * ��ѯָ��ʱ��������ŵ���������
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ConversationChild> selectMaxConversationChildByDay(@Param("startTime") Date startTime,@Param("endTime") Date endTime);
}
