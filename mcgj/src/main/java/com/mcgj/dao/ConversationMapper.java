package com.mcgj.dao;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.UserFollowConversation;
/**
 * ����mapper�ӿ�
 * @author ad
 *
 */
public interface ConversationMapper extends BaseService<Conversation,Integer>{
	
	/**
	 * ��ѯ���µ�������������(�û����ظ�����������)
	 * @return
	 */
	List<Map<String,Object>> selectNewestConversation();
	
	/**
	 * ����µ���������
	 * @return
	 */
	void addConversation(Conversation conversation);
	
	/**
	 * ��ѯ��ǰ���ӵı���ͷ�������
	 * @param id
	 * @return
	 */
	Conversation selectConversation(Conversation conversation);
	
}
