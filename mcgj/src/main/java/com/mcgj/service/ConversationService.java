package com.mcgj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationMapper;
import com.mcgj.dao.UserFollowConversationMapper;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.User;
import com.mcgj.entity.UserFollowConversation;
import com.mcgj.utils.UserUtil;

/**
 * ����ҵ���
 * 
 * @author ad
 *
 */
@Service
public class ConversationService implements IConversationService {

	@Autowired
	private ConversationMapper conversationMapper;

	@Autowired
	private UserFollowConversationMapper userFollowConversationMapper;

	public void delete(Integer id) {

	}

	public void insert(Conversation record) {

	}

	public void update(Conversation record) {

	}

	public Conversation selectById(Integer id) {

		return null;
	}

	/**
	 * ��ѯ���µ�������������(�û����ظ�����������)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectNewestConversation() {
		List<Map<String, Object>> conversations = conversationMapper
				.selectNewestConversation();
		return conversations;
	}

	/**
	 * ����µ���������
	 * 
	 * @return
	 */
	public void addConversation(Conversation conversation) {
		// �жϲ����Ϸ���
		if (conversation.getConversationName() == null
				|| "".equals(conversation.getConversationName())) {
			throw new RuntimeException("���Ʋ���Ϊ��");
		}
		if (conversation.getConversationType() == null
				|| "".equals(conversation.getConversationType())) {
			throw new RuntimeException("���Ͳ���Ϊ��");
		}
		if (conversation.getPhoto() == null
				|| "".equals(conversation.getPhoto())) {
			throw new RuntimeException("ͼƬ����Ϊ��");
		}
		User user = UserUtil.getCurrentUser(conversation.getToken());// ��ȡ��ǰ�û�����
		// ����ǰ�û���ֵ��ӵ�������
		conversation.setCurrentManageUserId(user.getId());
		conversation.setCreateUserId(user.getId());
		conversationMapper.insert(conversation);// ��������
	}

	/**
	 * ��ѯ��ǰ���ӵı���ͷ�������
	 */
	public Conversation selectConversationById(Conversation conversation) {
		if (conversation.getId() == null || "".equals(conversation.getId())) {
			throw new RuntimeException("id����Ϊ��");
		}
		Conversation c = conversationMapper.selectConversation(conversation);
		// �ж��Ƿ���Բ�ѯ��������
		if (c == null) {
			throw new RuntimeException("���ɲ�����");
		}
		return c;
	}

	/**
	 * �������Ʋ�ѯ��������
	 */
	public Conversation selectConversationByName(Conversation conversation) {
		if (conversation.getConversationName() == null
				|| "".equals(conversation.getConversationName())) {
			throw new RuntimeException("���Ʋ���Ϊ��");
		}
		return conversationMapper.selectConversation(conversation);
	}

	/**
	 * ����û���ע������
	 * 
	 * @param userFollowConversation
	 * @return
	 */
	public void addConversationFollow(
			UserFollowConversation userFollowConversation) {
		if (userFollowConversation.getUserId() == null
				|| "".equals(userFollowConversation.getUserId()))
			throw new RuntimeException("�û�id����Ϊ��");
		if (userFollowConversation.getConversationId() == null
				|| "".equals(userFollowConversation.getConversationId()))
			throw new RuntimeException("����id����Ϊ��");
		userFollowConversationMapper
				.addConversationFollow(userFollowConversation);// ��ӹ�ע
	}

	/**
	 * ȡ���û���ע������
	 * 
	 * @param userFollowConversation
	 * @return
	 */
	public void deleteConversationFollow(
			UserFollowConversation userFollowConversation) {
		if (userFollowConversation.getConversationId() == null
				|| "".equals(userFollowConversation.getConversationId()))
			throw new RuntimeException("��ע����id����Ϊ��");
		userFollowConversationMapper
				.deleteConversationFollow(userFollowConversation);
	}

	/**
	 * ��ѯ�û���ע������
	 */
	public List<UserFollowConversation> selectConversationFollow(
			UserFollowConversation userFollowConversation) {
		if (userFollowConversation.getUserId() == null
				|| "".equals(userFollowConversation.getUserId()))
			throw new RuntimeException("�û�id����Ϊ��");
		// if(userFollowConversation.getConversationId() == null ||
		// "".equals(userFollowConversation.getConversationId()))
		// throw new RuntimeException("����id����Ϊ��");
		return userFollowConversationMapper
				.selectConversationFollow(userFollowConversation);
	}

	/**
	 * ������������
	 */
	public void updateConversation(Conversation conversation) {
		if (conversation.getId() == null || "".equals(conversation.getId())) {
			throw new RuntimeException("����id����Ϊ��");
		}
		conversationMapper.update(conversation);
	}

	/**
	 * ��ѯ���µ�������������(�û����ظ�����������)
	 */
	public Map<String, Object> selectConversationStatistics(
			Conversation conversation) {
		if(conversation.getId() == null || "".equals(conversation.getId()))
			throw new RuntimeException("����id����Ϊ��");
		return conversationMapper.selectConversationStatistics(conversation);
	}

	/**
	 * ��ѯ�������ݣ����û���ע����ѯָ����������������
	 * @param limit ����
	 * @return
	 */
	public List<Conversation> selectMaxFollow(Integer limit) {
		if(limit == null || "".equals(limit))
			throw new RuntimeException("��������Ϊ��");
		return conversationMapper.selectMaxFollow(limit);
	}
	/**
	 * ��ѯ���ɵ������Ժ������¶�Ӧ����������
	 * @return
	 */
	@Override
	public Map<String,List<Object>> selectConversationTypeAndData() {
		List<Map<String, Object>> selectConversationTypeAndData = conversationMapper.selectConversationTypeAndData();
		//��ȡ���е���������
		Map<String,List<Object>> types = new HashMap<String, List<Object>>();
		for(Map<String,Object> map:selectConversationTypeAndData){
			List<Object> list2 = types.get(map.get("dictId").toString());
			if(list2!= null){//key����
				//��������е����ݴ���4���˳�ѭ��
				if(list2.size() >4)
					continue;
				list2.add(map);
			}else{//key���������´���
				List<Object> list =  new ArrayList<Object>();
				list.add(map);
				types.put(map.get("dictId").toString(), list);
			}
		}

		return types;
	}

	/**
	 * �����û�id��ѯ�û���ע�������µ��������¶�̬
	 */
	public List<Map<String, Object>> selectUserFollowConversation(User user) {
		if(user.getId() == null || "".equals(user.getId()))
			throw new RuntimeException("�û�id����Ϊ��");
		List<Map<String, Object>> selectUserFollowConversation = conversationMapper.selectUserFollowConversation(user);
		return selectUserFollowConversation;
	}

	/**
	 * ��ѯ�����������
	 */
	public Map<String, Object> selectConversationMaster(
			Conversation conversation) {
		if(conversation.getId() == null || "".equals(conversation.getId()))
			throw new RuntimeException("id����Ϊ��");
		return conversationMapper.selectConversationMaster(conversation);
	}

}
