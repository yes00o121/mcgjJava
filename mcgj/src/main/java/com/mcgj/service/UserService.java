package com.mcgj.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.dao.UserMapper;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.MessageType;
import com.mcgj.entity.User;
import com.mcgj.entity.loginLog;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.security.MD5Util;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.web.websocket.Message;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	/**
	 * ��¼����
	 */
	public User login(User user) {
		//�ж����ݺϷ���
		if(!verificationCodeCheck(user)){//У����֤���Ƿ���ȷ
			throw new RuntimeException("��֤�����");
		}
		if(user.getAccount() == null || "".equals(user.getAccount())){
			throw new RuntimeException("�˺Ų���Ϊ��");
		}
		if(user.getPassword() == null || "".equals(user.getPassword())){
			throw new RuntimeException("���벻��Ϊ��");
		}
		//���û�������м���ת��
		user.setPassword(MD5Util.getMD5(user.getPassword().getBytes()));
		User record = userMapper.login(user);//��ѯ�û�����
		if(record == null){
			throw new RuntimeException("�û������ڻ��������");
		}
		//��¼�������û�Ψһ��token��д�뻺����
		String auth = MD5Util.getMD5((user.getAccount()+""+user.getPassword()).getBytes());
		record.setToken(auth);//��token���ظ��û�����ΪΨһ��֤
		RedisHashUtil.setex(auth,record,PropertiesUtil.getLoginDelay());//�����û��Ựʱ��
		insertLogin(record);//��¼�û���¼��־ 
		return record;
	}
	/**
	 * ��¼�û���¼��־
	 */
	private void insertLogin(User user){
		loginLog loginLog = new loginLog();
		loginLog.setUserId(user.getId());
		loginLog.setCreateDate(new Date());
		userMapper.insertLoginLog(loginLog);
	}
	/**
	 * ��֤�û�������֤���Ƿ�����
	 * @return
	 */
	private Boolean verificationCodeCheck(User user){
		String verificationCode=  user.getVerificationCode();//��ȡ�������֤code
		if(verificationCode == null || "".equals(verificationCode)){
			throw new RuntimeException("�����쳣������ϵ����Ա");
		}
		if(user.getCheckCode() == null || "".equals(user.getCheckCode())){
			throw new RuntimeException("��֤�벻��Ϊ��");
		}
		String code = (String)RedisHashUtil.get(verificationCode);//��ȡ��Ӧ����֤��
		if(code ==null){
			throw new RuntimeException("��֤��ʱ");
		}
		if(code.equals(user.getCheckCode())){
			return true;
		}
		return false;
	}

	
	/**
	 * �û�ע�᷽��
	 */
	public void register(User user) {
		//���ݺϷ����ж�
		if(user.getAccount() == null || "".equals(user.getAccount())){
			throw new RuntimeException("�˺Ų���Ϊ��");
		}
		if(user.getPassword() == null || "".equals(user.getPassword())){
			throw new RuntimeException("���벻��Ϊ��");
		}
		if(user.getUserName() == null || "".equals(user.getUserName())){
			throw new RuntimeException("�û�������Ϊ��");
		}
		if(user.getPhoto() == null || "".equals(user.getPhoto())){
			throw new RuntimeException("ͷ����Ϊ��");
		}
		//�ж��˺��Ƿ����
		User record = this.findUserByAccount(user.getAccount());
		if(record != null){
			throw new RuntimeException("�˺��Ѵ���");
		}
		//��������м���
		user.setPassword(MD5Util.getMD5(user.getPassword().getBytes()));
		this.insert(user);//�����ݲ���user��
	}
	/**
	 * �ϴ�ͼƬ�ӿ�
	 * @return
	 */
	public String upload(){
		return null;
	}

	public void delete(Integer id) {
		
	}
	/**
	 * ���뷽��
	 */
	public void insert(User record) {
		this.userMapper.insert(record);
	}

	public void update(User record) {
		
	}

	public User selectById(Integer id) {
		
		return null;
	}
	
	/**
	 * �����û��˺Ų�ѯ�û�����
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account) {
		User user = this.userMapper.findUserByAccount(account);
		return user;
	}

	@Override
	public List<Message> selectUserUnreadMessageCountByUserId(Integer userId) {
		if(userId == null || "".equals(userId)){
			throw new RuntimeException("�û�id����Ϊ��");
		}
		List<Message> messages = new ArrayList<Message>();
		Integer replyNumber = userMapper.selectUserUnreadMessageCountByUserId(userId);//��ȡ�û�δ�鿴�Ļظ���Ϣ
		messages.add(new Message(replyNumber, MessageType.reply));
		return messages;
	}

	public List<ConversationChild> selectCollectionConversationChildByUserId(
			Integer userId) {
		if(userId == null || "".equals(userId)){
			throw new RuntimeException("�û�id����Ϊ��");
		}
		
		return conversationChildMapper.selectCollectionConversationChildByUserId(userId);
	}
	public static void main(String[] args) {
		String auth = MD5Util.getMD5(("123456").getBytes());
		System.out.println(auth);
	}

}	