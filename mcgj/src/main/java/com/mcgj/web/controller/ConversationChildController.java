package com.mcgj.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.UserCollectionConversationChild;
import com.mcgj.service.IConversationChildService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * ��������������
 * @author ad
 *
 */
@Controller
@RequestMapping("/conversationChild")
public class ConversationChildController {
	
	Logger log = Logger.getLogger(ConversationChildController.class);
	
	@Autowired
	private IConversationChildService conversationChildService;
	
	/**
	 * ��������id��ѯ,��������id��ѯ,��ȡ��ǰ���ӵĵ�һ������
	 * @return
	 */
	@RequestMapping("/selectConversationChildById")
	@ResponseBody
	public ResultDTO selectConversationChildById(Integer id){
		ResultDTO result = new ResultDTO();
		try{
			result.setResult(conversationChildService.selectConversationChildById(id));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	/**
	 * ��������id��ѯ���µ�������������
	 * @return
	 */
	@RequestMapping("/selectConversationChildByConversationId")
	@ResponseBody
	public ResultDTO selectConversationChildByConversationId(ConversationChild conversationChild){
		ResultDTO result = new ResultDTO();
		try{
			result.setResult(conversationChildService.selectConversationChildByConversationId(conversationChild));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	/**
	 * �������ӵķ���
	 * @return
	 */
	@RequestMapping("/addConversationChild")
	@ResponseBody
	public ResultDTO addConversationChild(ConversationChild conversation){
		ResultDTO result = new ResultDTO();
		try{
//			Conversation con = conversationService.selectConversationById(conversation);
			conversationChildService.addConversationChild(conversation);
//			result.setResult(con);
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
	 * ���������ղط���
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/addConversationChildCollection")
	@ResponseBody
	public ResultDTO addConversationChildCollection(HttpServletRequest request,HttpServletResponse response,UserCollectionConversationChild userCollectionConversationChild){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.addConversationChildCollection(userCollectionConversationChild);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_COLLECTION_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_COLLECTION_ERROR);
			return result;
		}
	}
	
	/**
	 * ɾ�������ղط���
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/deleteConversationChildCollection")
	@ResponseBody
	public ResultDTO deleteConversationChildChildCollection(HttpServletRequest request,HttpServletResponse response,UserCollectionConversationChild userCollectionConversationChild){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildService.deleteConversationChildCollection(userCollectionConversationChild);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_COLLECTION_CANCEL_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_COLLECTION_CANCEL_ERROR);
			return result;
		}
	}
	
	/**
	 * �������Ӻ��û�id�ж��û��Ƿ��ղ��˸���
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/selectConversationChildCollection")
	@ResponseBody
	public ResultDTO selectConversationChildCollection(HttpServletRequest request,HttpServletResponse response,UserCollectionConversationChild userCollectionConversationChild){
		ResultDTO result = new ResultDTO();
		try{
			UserCollectionConversationChild uc = conversationChildService.selectConversationChildCollection(userCollectionConversationChild);
			result.setResult(uc);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			return result;
		}
	}
	
	/**
	 * ��ѯ�û������������� 
	 * @param conversationChild
	 * @return
	 */
	@RequestMapping("/selectUserPublishConversationChild")
	@ResponseBody
	public ResultDTO selectUserPublishConversationChild(ConversationChild conversationChild){
		ResultDTO result = new ResultDTO();
		try{
			List<ConversationChild> selectUserPublishConversationChild = conversationChildService.selectUserPublishConversationChild(conversationChild);
			result.setResult(selectUserPublishConversationChild);
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setMessage(MessageUtil.MSG_QUERY_ERROR);
			result.setResult(e.getMessage());
			result.setSuccess(false);
			return result;
		}
		
	}
}