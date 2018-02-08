package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.UserFollowConversation;

public interface UserFollowConversationMapper extends BaseService<UserFollowConversation,Integer>{
	/**
	 * ����û���ע������
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
