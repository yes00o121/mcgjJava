package com.mcgj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.User;
import com.mcgj.entity.loginLog;
/**
 * �û��ӿ�
 * @author ad
 *
 */
public interface UserMapper extends BaseService<User,Integer>{
	
	/**
	 * ��¼�ӿ�
	 * @param user
	 * @return
	 */
	User login(User user);
	
	/**
	 * �����û��˺Ų�ѯ�û�����
	 * @param account
	 * @return
	 */
	User findUserByAccount(@Param("account") String account);
	
	/**
	 * ��ȡ�û�δ�鿴�ظ����ݵ�����
	 * @return
	 */
	Integer selectUserUnreadMessageCountByUserId(Integer userId);
	
	/**
	 * ��¼�û���¼��־
	 * @param loginLog
	 */
	void insertLoginLog(loginLog loginLog);
	
}