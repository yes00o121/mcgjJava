package com.mcgj.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.Conversation;
import com.mcgj.entity.UserFollowConversation;
import com.mcgj.service.IConversationService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * ���ɿ��������������ɵ����ݴ���
 * @author ad
 *
 */
@Controller
@RequestMapping("/conversation")
public class ConversationController extends AbstractBaseController{
		
	Logger log = Logger.getLogger(ConversationController.class);
	
	@Autowired
	private IConversationService conversationService;
	
	/**
	 * ��ѯ���µ�������������(�û����ظ�����������)
	 * @return
	 */
	@RequestMapping("/selectNewestConversation")
	@ResponseBody
	public ResultDTO selectNewestConversation(){
		ResultDTO result = new ResultDTO();
		try{
			List<Map<String,Object>> conversations = conversationService.selectNewestConversation();
			result.setResult(conversations);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setResult(e.getMessage());
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			result.setSuccess(false);
			return result;
		}
	}
	/**
	 * �����µ���������
	 * @return
	 */
	@RequestMapping("/addConversation")
	@ResponseBody
	public ResultDTO addConversation(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.addConversation(conversation);
			result.setMessage(MessageUtil.MSG_INSERT_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setResult(e.getMessage());
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}
	/**
	 * ��ѯ��ǰ���ӵı���ͷ�������
	 * @return
	 */
	@RequestMapping("/selectConversationById")
	@ResponseBody
	public ResultDTO selectConversationById(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			Conversation con = conversationService.selectConversationById(conversation);
			result.setResult(con);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setResult(e.getMessage());
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}
	/**
	 * �����������ƽ��в�ѯ
	 * @return
	 */
	@RequestMapping("/selectConversationByName")
	@ResponseBody
	public ResultDTO selectConversationByName(Conversation conversation){
		ResultDTO result = new ResultDTO();
		try{
			Conversation con = conversationService.selectConversationByName(conversation);
			result.setResult(con);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setResult(e.getMessage());
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}
	
	/**
	 * �����û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	@RequestMapping("/addConversationFollow")
	@ResponseBody
	public ResultDTO addConversationFollow(UserFollowConversation userFollowConversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.addConversationFollow(userFollowConversation);
			result.setMessage(MessageUtil.MSG_FOLLOW_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_FOLLOW_ERROR);
			return result;
		}
	}
	
	/**
	 * ȡ���û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	@RequestMapping("/deleteConversationFollow")
	@ResponseBody
	public ResultDTO deleteConversationFollow(UserFollowConversation userFollowConversation){
		ResultDTO result = new ResultDTO();
		try{
			conversationService.deleteConversationFollow(userFollowConversation);
			result.setMessage(MessageUtil.MSG_UNKONW_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_UNKONW_ERROR);
			return result;
		}
	}
	/**
	 * ��ѯ�û���ע������
	 * @param userFollowConversation
	 * @return
	 */
	@RequestMapping("/selectConversationFollow")
	@ResponseBody
	public ResultDTO selectConversationFollow(UserFollowConversation userFollowConversation){
		ResultDTO result = new ResultDTO();
		try{
			List<UserFollowConversation> ufc = conversationService.selectConversationFollow(userFollowConversation);
			result.setResult(ufc);
			result.setMessage(MessageUtil.MSG_UNKONW_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResult(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_UNKONW_ERROR);
			return result;
		}
	}
}