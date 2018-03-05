package com.mcgj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.User;
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
	
	/**
	 * ��ѯ���ɵ�һЩͳ�����ݣ����ע������������������
	 * @param ���ɶ���
	 * @return
	 */
	Map<String,Object> selectConversationStatistics(Conversation conversation);
	
	/**
	 * ��ѯ�������ݣ����û���ע����ѯָ����������������
	 * @param limit ����
	 * @return
	 */
	List<Conversation> selectMaxFollow(@Param("limit")Integer limit);
	
	/**
	 * ��ѯ���ɵ������Ժ������¶�Ӧ����������
	 * @return
	 */
	List<Map<String,Object>> selectConversationTypeAndData();
	
	/**
	 * �����û�id��ѯ�û���ע�������µ��������¶�̬
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> selectUserFollowConversation(User user);
	
	/**
	 * ��ѯ�����������
	 * @param conversation
	 * @return
	 */
	Map<String,Object> selectConversationMaster(Conversation conversation);
}
