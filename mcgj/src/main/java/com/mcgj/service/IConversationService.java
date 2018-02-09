package com.mcgj.service;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.UserFollowConversation;
import com.mcgj.web.dto.ResultDTO;

public interface IConversationService extends BaseService<Conversation,Integer>{
	
	/**
	 * ��ѯ���µ�������������(�û����ظ�����������)
	 * @return
	 */
	List<Map<String,Object>> selectNewestConversation();
	
	/**
	 * �����µ���������
	 * @return
	 */
	void addConversation(Conversation conversation);
	
	/**
	 * ��ѯ��ǰ���ӵı���ͷ�������
	 * @param id
	 * @return
	 */
	Conversation selectConversationById(Conversation conversation);
	
	/**
	 * �������Ʋ�ѯ��������
	 * @param id
	 * @return
	 */
	Conversation selectConversationByName(Conversation conversation);
	
	/**
	 * �����û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	void addConversationFollow(UserFollowConversation userFollowConversation);
	
	/**
	 * ȡ���û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	void deleteConversationFollow(UserFollowConversation userFollowConversation);
	
	/**
	 * ��ѯ�û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	List<UserFollowConversation> selectConversationFollow(UserFollowConversation userFollowConversation);
}