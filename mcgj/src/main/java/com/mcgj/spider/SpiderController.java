package com.mcgj.spider;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.service.IConversationChildService;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.controller.AbstractBaseController;
import com.mcgj.web.controller.ConversationChildController;
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
}