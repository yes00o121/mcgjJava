package com.mcgj.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.ConversationChildChild;
import com.mcgj.service.IConversationChildChildService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * ����¥�������
 * @author ad
 *
 */
@Controller
@RequestMapping("/conversationChildChild")
public class ConversationChildChildController {
	
	Logger log = Logger.getLogger(ConversationChildChildController.class);
	
	@Autowired
	private IConversationChildChildService conversationChildChildService;
	
	/**
	 * ��������id��ѯ���µ�¥������
	 * @return
	 */
	@RequestMapping("/selectCCCByCCId")
	@ResponseBody
	public ResultDTO selectCCCByCCId(HttpServletRequest request,HttpServletResponse response,ConversationChildChild conversationChildChild){
		ResultDTO result = new ResultDTO();
		try{
			Map<String,Object> maps = conversationChildChildService.selectCCCByCCId(conversationChildChild);
			result.setResult(maps);
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
	 * ����¥�㷽��,����¥��ʱ��Ϣʵʱ���͸�¥��������д��ظ����У�����û��������򲻽�������
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/addConversationChildChild")
	@ResponseBody
	public ResultDTO addConversationChildChild(HttpServletRequest request,HttpServletResponse response,ConversationChildChild conversationChildChild){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildChildService.addConversationChildChild(conversationChildChild);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_INSERT_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_INSERT_FAILED);
			return result;
		}
	}
	
	/**
	 * ��ѯ�û�Ϊ�鿴�����лظ���¥������
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/selectConversationChildChildReplyByUserId")
	@ResponseBody
	public ResultDTO selectConversationChildChildReplyByUserId(HttpServletRequest request,HttpServletResponse response,ConversationChildChild conversationChildChild){
		ResultDTO result = new ResultDTO();
		try{
			result.setResult(conversationChildChildService.selectConversationChildChildReplyByUserId(conversationChildChild));
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
	 * �����û����е�δ�鿴¥�㶼�޸�Ϊ�鿴״̬
	 * @param request 
	 * @param response
	 * @param conversationChildChild
	 * @return
	 */
	@RequestMapping("/updateConversationChildChildReplyByUerId")
	@ResponseBody
	public ResultDTO updateConversationChildChildReplyByUerId(HttpServletRequest request,HttpServletResponse response,Integer userId){
		ResultDTO result = new ResultDTO();
		try{
			conversationChildChildService.updateConversationChildChildReplyByUerId(userId);
			result.setSuccess(true);
			result.setMessage(MessageUtil.MSG_EDIT_SUCCESS);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			result.setSuccess(false);
			result.setMessage(MessageUtil.MSG_EDIT_FATLED);
			return result;
		}
	}
}
