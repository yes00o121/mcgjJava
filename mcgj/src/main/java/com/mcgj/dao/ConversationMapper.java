package com.mcgj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.UserFollowConversation;
/**
 * 贴吧mapper接口
 * @author ad
 *
 */
public interface ConversationMapper extends BaseService<Conversation,Integer>{
	
	/**
	 * 查询最新的贴吧帖子数据(用户最后回复的帖子数据)
	 * @return
	 */
	List<Map<String,Object>> selectNewestConversation();
	
	/**
	 * 添加新的贴吧数据
	 * @return
	 */
	void addConversation(Conversation conversation);
	
	/**
	 * 查询当前帖子的标题头像等内容
	 * @param id
	 * @return
	 */
	Conversation selectConversation(Conversation conversation);
	
	/**
	 * 查询贴吧的一些统计数据，如关注人数，发贴数等数据
	 * @param 贴吧对象
	 * @return
	 */
	Map<String,Object> selectConversationStatistics(Conversation conversation);
	
	/**
	 * 查询贴吧数据，按用户关注量查询指定数量的贴吧数据
	 * @param limit 数量
	 * @return
	 */
	List<Conversation> selectMaxFollow(@Param("limit")Integer limit);
	
}
