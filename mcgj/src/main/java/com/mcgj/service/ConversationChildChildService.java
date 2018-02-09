package com.mcgj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationChildChildMapper;
import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.MessageType;
import com.mcgj.entity.User;
import com.mcgj.entity.UserCollectionConversationChild;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.security.MD5Util;
import com.mcgj.utils.Base64Util;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PageUtil;
import com.mcgj.web.websocket.Message;
import com.mcgj.web.websocket.WebSocketServer;

/**
 * ¥��ҵ��� 
 * @author ad
 *
 */
@Service
public class ConversationChildChildService implements IConversationChildChildService{

	@Autowired
	private ConversationChildChildMapper conversationChildChildMapper;
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	public void delete(Integer id) {
		
	}

	public void insert(ConversationChildChild record) {
		
	}

	public void update(ConversationChildChild record) {
		
	}

	public ConversationChildChild selectById(Integer id) {
		return null;
	}
	
	/**
	 * ��������id��ѯ���µ�¥������
	 * @return
	 */
	public Map<String,Object> selectCCCByCCId(ConversationChildChild conversationChildChild){
		//�жϲ����Ϸ���
		if(conversationChildChild.getConversationChildId() == null || "".equals(conversationChildChild.getConversationChildId())){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		if(conversationChildChild.getSingleFloor() == null || "".equals(conversationChildChild.getSingleFloor())){
			throw new RuntimeException(MessageUtil.MSG_QUERY_ERROR);
		}
		List<Map<String,Object>> conversationChildChilds  = conversationChildChildMapper.selectCCCByCCId(conversationChildChild);//��ǰ���ӵ�����¥������
		
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("conversationChildChilds", PageUtil.createPage(conversationChildChild.getStart(),conversationChildChild.getLimit(),conversationChildChilds));
		maps.put("size", conversationChildChilds.size());
		return maps;
	}
	
	/**
	 * ����¥�㷽��
	 */
	public void addConversationChildChild(ConversationChildChild conversationChildChild) {
		//�����¥��������¥�㣬¥���鿴״̬Ĭ��Ϊ1,��Ȼ����Ϣ���͸�¥��
		if(conversationChildChild.getIsManage().equals(1)){
			conversationChildChild.setIsLook(1);//¥���ظ������ӣ�����Ҫ������Ϣ������
		}
//		conversationChildChild.setContent(Base64Util.getStrBASE64(conversationChildChild.getContent()));//�������ı����м���
		conversationChildChildMapper.insert(conversationChildChild);//����¥��
		if(conversationChildChild.getIsManage().equals(0)){//����¥���ظ������ӣ�����Ϣ���͸�¥��
			if(conversationChildChild.getConversationChildId() != null && !"".equals(conversationChildChild.getConversationChildId())){
				User user = conversationChildMapper.selectUserByconversationChildId(conversationChildChild.getConversationChildId());
				if(user != null){
					String token = MD5Util.getMD5((user.getAccount()+user.getPassword()).getBytes());//��ȡ¥��token
					if(RedisHashUtil.get(token) != null){//�ж�¥���Ự�Ƿ���ڣ������ڲ�������Ϣ������
						WebSocketServer.sendMessage(token,new Message(MessageUtil.MSG_REPLY_FLOOR,MessageType.reply));
					}
				}
			}
		}
	}

	@Override
	public List<ConversationChildChild> selectConversationChildChildReplyByUserId(
			Integer userId) {
		if(userId == null){
			throw new RuntimeException("�û�id����Ϊ��");
		}
		List<ConversationChildChild> ccc = conversationChildChildMapper.selectConversationChildChildReplyByUserId(userId);
		return ccc;
	}

	/**
	 * �����û����е�δ�鿴¥�㶼�޸�Ϊ�鿴״̬
	 */
	public void updateConversationChildChildReplyByUerId(Integer userId) {
		if(userId == null || "".equals(userId)){
			throw new RuntimeException("�û�id����Ϊ��");
		}
		conversationChildChildMapper.updateConversationChildChildReplyByUerId(userId);
	}
	
	
}