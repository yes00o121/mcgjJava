package com.mcgj.service;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.User;
import com.mcgj.web.websocket.Message;

public interface IUserService extends BaseService<User,Integer>{
	/**
	 * ��¼�ӿ�
	 * @return
	 */
	User login(User user);
	/**
	 * ע�᷽��
	 * @param user �û�ʵ����
	 * @return
	 */
	void register(User user);
	
	/**
	 * �����û��˺Ų�ѯ�û�����
	 * @param account
	 * @return
	 */
	User findUserByAccount(String account);
	
	/**
	 *  �����û�id��ѯ�û�δ������Ϣ��������
	 * @param userId
	 * @return
	 */
	List<Message> selectUserUnreadMessageCountByUserId(Integer userId);
	
	/**
	 * �����û�id���ղص���������
	 * @return
	 */
	List<ConversationChild> selectCollectionConversationChildByUserId(Integer userId);
}