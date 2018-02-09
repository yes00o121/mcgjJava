package com.mcgj.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationChildChildMapper;
import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.UserCollectionConversationChild;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PageUtil;

@Service
public class ConversationChildService implements IConversationChildService{
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	@Autowired
	private ConversationChildChildMapper conversationChildChildMapper;

	public void delete(Integer id) {
		
	}

	public void insert(ConversationChild record) {
		
	}

	public void update(ConversationChild record) {
		
	}

	public ConversationChild selectById(Integer id) {
		return null;
	}
	
	/**
	 * ��������id��ѯ,��ȡ��ǰ���ӵĵ�һ������
	 * @return
	 */
	public Map<String,Object> selectConversationChildById(Integer id) {
		//�ж�id�Ƿ�Ϸ�
		if(id == null || "".equals(id)){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		Map<String,Object> converstationChilds = conversationChildMapper.selectConversationChildById(id);
		if(converstationChilds == null || converstationChilds.size() == 0){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		return converstationChilds;
	}

	@Override
	public Map<String,Object> selectConversationChildByConversationId(
			ConversationChild conversationChild) {
		if(conversationChild.getConversationId() == null || "".equals(conversationChild.getConversationId())){
			throw new RuntimeException("id����Ϊ��");
		}
		Map<String,Object> maps = new HashMap<String, Object>();
		List<ConversationChild> ConversationChilds = conversationChildMapper.selectConversationChildByConversationId(conversationChild);
		maps.put("conversationChilds", PageUtil.createPage(conversationChild.getStart(),conversationChild.getLimit(),ConversationChilds));
		maps.put("size", ConversationChilds.size());
		return maps;
	}

	/**
	 * �������ӵķ���
	 */
	public void addConversationChild(ConversationChild conversationChild) {
		conversationChild.setCreateDate(new Date());
		//�жϲ����Ϸ���
		if(conversationChild.getConversationId() == null || "".equals(conversationChild.getConversationId())){
			throw new RuntimeException("id����Ϊ��");
		}
		if(conversationChild.getUserId() == null || "".equals(conversationChild.getUserId())){
			throw new RuntimeException("�û�id����Ϊ��");
		}
		if(conversationChild.getTitle() == null || "".equals(conversationChild.getTitle())){
			throw new RuntimeException("���ⲻ��Ϊ��");
		}
		if(conversationChild.getContent() == null || "".equals(conversationChild.getContent())){
			throw new RuntimeException("���ݲ���Ϊ��");
		}
		conversationChildMapper.insert(conversationChild);//��������
		ConversationChildChild ccc = new ConversationChildChild();
		ccc.setConversationChildId(conversationChild.getId());
		ccc.setUserId(conversationChild.getUserId());
		ccc.setConversationChildId(conversationChild.getId());
		ccc.setContent(conversationChild.getContent());
		ccc.setCreateDate(new Date());
		ccc.setIsManage(1);//¥��
		ccc.setIsLook(1);
		conversationChildChildMapper.insert(ccc);//�����ӵ����ݲ���¥����
	}
	
	/**
	 * �����ղط���
	 */
	public void addConversationChildCollection(
			UserCollectionConversationChild userCollectionConversationChild) {
		this.paramsJudge(userCollectionConversationChild);
		conversationChildMapper.addConversationChildCollection(userCollectionConversationChild);
	}

	@Override
	public void deleteConversationChildCollection(
			UserCollectionConversationChild userCollectionConversationChild) {
		this.paramsJudge(userCollectionConversationChild);
		int num = conversationChildMapper.deleteConversationChildCollection(userCollectionConversationChild);
		if(num == 0){//�ж�ɾ���������������0û���ҵ�����ɾ�������ݣ��׳��쳣
			throw new RuntimeException(MessageUtil.MSG_COLLECTION_ERROR);
		}
	}

	@Override
	public UserCollectionConversationChild selectConversationChildCollection(
			UserCollectionConversationChild userCollectionConversationChild) {
		this.paramsJudge(userCollectionConversationChild);
		return conversationChildMapper.selectConversationChildCollection(userCollectionConversationChild);
	}
	//�����Ϸ����ж�,�ղص���ɾ�鹫��
	private void paramsJudge(UserCollectionConversationChild userCollectionConversationChild){
		//�жϲ����Ϸ���
		if(userCollectionConversationChild.getUserId() == null || "".equals(userCollectionConversationChild.getUserId())){
			throw new RuntimeException(MessageUtil.MSG_COLLECTION_ERROR);
		}
		//�жϲ����Ϸ���
		if(userCollectionConversationChild.getConversationChildId() == null || "".equals(userCollectionConversationChild.getConversationChildId())){
			throw new RuntimeException(MessageUtil.MSG_COLLECTION_ERROR);
		}
	}

	/**
	 * ��ѯ�û������������� 
	 * @return
	 */
	public List<ConversationChild> selectUserPublishConversationChild(
			ConversationChild conversationChild) {
		//�жϲ����Ƿ�Ϸ�
		if(conversationChild.getUserId() == null || "".equals(conversationChild.getUserId()))
			throw new RuntimeException("�û�id����Ϊ��");
		
		return conversationChildMapper.selectUserPublishConversationChild(conversationChild);
	}
}