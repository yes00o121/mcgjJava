package com.mcgj.service;

import java.util.List;
import java.util.Map;

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
 * @author ad
 *
 */
@Service
public class ConversationService implements IConversationService{
	
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
	 * @return
	 */
	public List<Map<String, Object>> selectNewestConversation() {
		List<Map<String,Object>> conversations = conversationMapper.selectNewestConversation();
		return conversations;
	}
	
	/**
	 * �����µ���������
	 * @return
	 */
	public void addConversation(Conversation conversation) {
		//�жϲ����Ϸ���
		if(conversation.getConversationName() == null || "".equals(conversation.getConversationName())){
			throw new RuntimeException("���Ʋ���Ϊ��");
		}
		if(conversation.getConversationType() == null || "".equals(conversation.getConversationType())){
			throw new RuntimeException("���Ͳ���Ϊ��");
		}
		if(conversation.getPhoto() == null || "".equals(conversation.getPhoto())){
			throw new RuntimeException("ͼƬ����Ϊ��");
		}
		User user = UserUtil.getCurrentUser(conversation.getToken());//��ȡ��ǰ�û�����
		//����ǰ�û���ֵ���ӵ�������
		conversation.setCurrentManageUserId(user.getId());
		conversation.setCreateUserId(user.getId());
		conversationMapper.insert(conversation);//��������
	}

	/**
	 * ��ѯ��ǰ���ӵı���ͷ�������
	 */
	public Conversation selectConversationById(Conversation conversation) {
		if(conversation.getId() == null || "".equals(conversation.getId())){
			throw new RuntimeException("id����Ϊ��");
		}
		Conversation c = conversationMapper.selectConversation(conversation);
		//�ж��Ƿ���Բ�ѯ��������
		if(c == null){
			throw new RuntimeException("���ɲ�����");
		}
		return c;
	}

	/**
	 * �������Ʋ�ѯ��������
	 */
	public Conversation selectConversationByName(Conversation conversation) {
		if(conversation.getConversationName() == null || "".equals(conversation.getConversationName())){
			throw new RuntimeException("���Ʋ���Ϊ��");
		}
		return conversationMapper.selectConversation(conversation);
	}

	/**
	 * �����û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	public void addConversationFollow(
			UserFollowConversation userFollowConversation) {
		if(userFollowConversation.getUserId() == null || "".equals(userFollowConversation.getUserId()))
			throw new RuntimeException("�û�id����Ϊ��");
		if(userFollowConversation.getConversationId() == null || "".equals(userFollowConversation.getConversationId()))
			throw new RuntimeException("����id����Ϊ��");
		userFollowConversationMapper.addConversationFollow(userFollowConversation);//���ӹ�ע
	}

	/**
	 * ȡ���û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	public void deleteConversationFollow(UserFollowConversation userFollowConversation) {
		if(userFollowConversation.getId() == null || "".equals(userFollowConversation.getId()))
			throw new RuntimeException("��ע����id����Ϊ��");
		userFollowConversationMapper.deleteConversationFollow(userFollowConversation);
	}
	
	/**
	 * ��ѯ�û���ע������
	 */
	public List<UserFollowConversation> selectConversationFollow(
			UserFollowConversation userFollowConversation) {
		if(userFollowConversation.getUserId() == null || "".equals(userFollowConversation.getUserId()))
			throw new RuntimeException("�û�id����Ϊ��");
//		if(userFollowConversation.getConversationId() == null || "".equals(userFollowConversation.getConversationId()))
//			throw new RuntimeException("����id����Ϊ��");
		return userFollowConversationMapper.selectConversationFollow(userFollowConversation);
	}
	
	

}