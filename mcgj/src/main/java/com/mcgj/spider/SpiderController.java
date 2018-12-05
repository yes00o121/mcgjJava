package com.mcgj.spider;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.filter.AutoLoad;
import com.mcgj.dao.ConversationChildChildMapper;
import com.mcgj.dao.ConversationChildMapper;
import com.mcgj.dao.ConversationMapper;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.User;
import com.mcgj.service.IConversationChildService;
import com.mcgj.service.IUserService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.controller.AbstractBaseController;
import com.mcgj.web.dto.ResultDTO;

/**
 * ���������
 * @author ad
 *
 */
@Controller
@RequestMapping("/spider")
public class SpiderController extends AbstractBaseController{
		
	Logger log = Logger.getLogger(SpiderController.class);
	
	@Autowired
	IConversationChildService conversationChildService;
	
	@Autowired
	private ConversationMapper conversationMapper;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ConversationChildChildMapper conversationChildChildMapper;
	
	@Autowired
	private ConversationChildMapper conversationChildMapper;
	
	/**
	 * ��ѯ��Ӧ��������Ϊ��Ծ����������
	 * @param limit Ҫ��ѯ����������
	 * @return
	 */
	@RequestMapping("/addFloorDataSpider")
	@ResponseBody
	public ResultDTO addFloorDataSpider(ConversationChildChild conversationChildChild,Integer userId2){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.addFloorDataSpider(conversationChildChild,userId2);
//			List<ConversationChild> selectMaxConversationChildByDays = conversationChildService.selectMaxConversationChildByDay(day);
//			result.setResult(selectMaxConversationChildByDays);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			return result;
		}
	}
	
	/**
	 * 
	 */
	@RequestMapping("/addConversation")
	@ResponseBody
	public ResultDTO addConversation(Conversation conversation){
		try {
			conversationMapper.insert(conversation);// ��������
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS, true, conversation);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_INSERT_FAILED, false, null);
		}
		
	}
	
	@RequestMapping("/selectConversation")
	@ResponseBody
	public ResultDTO selectConversation(){
		try {
			return new ResultDTO(MessageUtil.MSG_QUERY_SUCCESS, true, conversationMapper.selectConversation(null));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_QUERY_ERROR, false, null);
		}
		
	}
	
	@RequestMapping("/selectUserByAccount")
	@ResponseBody
	public User getUserByAccount(String account){
		return userService.findUserByAccount(account);
	}
	
	/**
	 * ������������
	 * @return
	 */
	@RequestMapping("/addConversationChild")
	@ResponseBody
	public ResultDTO insertConversationChild(ConversationChild conversationChild){
		try {
//			conversationChild.setContent(conversationChild.getContent());
			conversationChildMapper.insert(conversationChild);//��������
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS,true,conversationChild);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_INSERT_FAILED,false,null);
		}
	}
	
	/**
	 * ��ѯ��������
	 * @param conversationChild
	 * @return
	 */
	@RequestMapping("/selectConversationChild")
	@ResponseBody
	public ConversationChild selectConversationChild(ConversationChild conversationChild){
		return conversationChildMapper.selectConversationChild(conversationChild);
	}
	
	/**
	 * ��������¥��
	 * @return
	 */
	@RequestMapping("/addConversationChildChild")
	@ResponseBody
	public ResultDTO addConversationChildChild(ConversationChildChild conversationChildChild,String time){
		try {
			conversationChildChild.setCreateDate(new Date(Long.parseLong(time)));//ת��ʱ��
			conversationChildChildMapper.insert(conversationChildChild);//����¥��
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDTO(MessageUtil.MSG_INSERT_SUCCESS, false, null);
		}
	}
}
