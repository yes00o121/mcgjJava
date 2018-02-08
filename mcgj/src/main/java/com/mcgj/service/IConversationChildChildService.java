package com.mcgj.service;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.User;

/**
 * 楼层控制器接口
 * @author ad
 *
 */
public interface IConversationChildChildService extends BaseService<ConversationChildChild,Integer>{
	
	/**
	 * 根据贴子id查询其下的楼层数据
	 * @return
	 */
	Map<String,Object> selectCCCByCCId(ConversationChildChild conversationChildChild);
	
	/**
	 * 新增楼层方法
	 * @return
	 */
	void addConversationChildChild(ConversationChildChild conversationChildChild);
	
	/**
	 * 查询用户为查看的所有回复的楼层数据
	 * @param conversationChildChild
	 * @return
	 */
	List<ConversationChildChild> selectConversationChildChildReplyByUserId(Integer userId);
	
	/**
	 * 将该用户所有的未查看楼层都修改为查看状态
	 * @param userId
	 */
	void updateConversationChildChildReplyByUerId(Integer userId);

}
