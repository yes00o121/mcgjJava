package com.mcgj.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.ConversationMapper;
import com.mcgj.dao.UserFollowConversationMapper;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.User;
import com.mcgj.entity.UserFollowConversation;
import com.mcgj.utils.UserUtil;

/**
 * 贴吧业务层
 * 
 * @author ad
 *
 */
@Service
public class ConversationService implements IConversationService {

	@Autowired
	private ConversationMapper conversationMapper;

	@Autowired
	private UserFollowConversationMapper userFollowConversationMapper;

	public void delete(Integer id) {

	}

	public void insert(Conversation record) {

	}

	public void update(Conversation record) {

	}

	public Conversation selectById(Integer id) {

		return null;
	}

	/**
	 * 查询最新的贴吧帖子数据(用户最后回复的帖子数据)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectNewestConversation() {
		List<Map<String, Object>> conversations = conversationMapper
				.selectNewestConversation();
		return conversations;
	}

	/**
	 * 添加新的贴吧数据
	 * 
	 * @return
	 */
	public void addConversation(Conversation conversation) {
		// 判断参数合法性
		if (conversation.getConversationName() == null
				|| "".equals(conversation.getConversationName())) {
			throw new RuntimeException("名称不能为空");
		}
		if (conversation.getConversationType() == null
				|| "".equals(conversation.getConversationType())) {
			throw new RuntimeException("类型不能为空");
		}
		if (conversation.getPhoto() == null
				|| "".equals(conversation.getPhoto())) {
			throw new RuntimeException("图片不能为空");
		}
		User user = UserUtil.getCurrentUser(conversation.getToken());// 获取当前用户对象
		// 将当前用户的值添加到对象中
		conversation.setCurrentManageUserId(user.getId());
		conversation.setCreateUserId(user.getId());
		conversationMapper.insert(conversation);// 插入贴吧
	}

	/**
	 * 查询当前帖子的标题头像等内容
	 */
	public Conversation selectConversationById(Conversation conversation) {
		if (conversation.getId() == null || "".equals(conversation.getId())) {
			throw new RuntimeException("id不能为空");
		}
		Conversation c = conversationMapper.selectConversation(conversation);
		// 判断是否可以查询到该贴吧
		if (c == null) {
			throw new RuntimeException("贴吧不存在");
		}
		return c;
	}

	/**
	 * 根据名称查询贴吧数据
	 */
	public Conversation selectConversationByName(Conversation conversation) {
		if (conversation.getConversationName() == null
				|| "".equals(conversation.getConversationName())) {
			throw new RuntimeException("名称不能为空");
		}
		return conversationMapper.selectConversation(conversation);
	}

	/**
	 * 添加用户关注的贴吧
	 * 
	 * @param userFollowConversation
	 * @return
	 */
	public void addConversationFollow(
			UserFollowConversation userFollowConversation) {
		if (userFollowConversation.getUserId() == null
				|| "".equals(userFollowConversation.getUserId()))
			throw new RuntimeException("用户id不能为空");
		if (userFollowConversation.getConversationId() == null
				|| "".equals(userFollowConversation.getConversationId()))
			throw new RuntimeException("贴吧id不能为空");
		userFollowConversationMapper
				.addConversationFollow(userFollowConversation);// 添加关注
	}

	/**
	 * 取消用户关注的贴吧
	 * 
	 * @param userFollowConversation
	 * @return
	 */
	public void deleteConversationFollow(
			UserFollowConversation userFollowConversation) {
		if (userFollowConversation.getConversationId() == null
				|| "".equals(userFollowConversation.getConversationId()))
			throw new RuntimeException("关注贴吧id不能为空");
		userFollowConversationMapper
				.deleteConversationFollow(userFollowConversation);
	}

	/**
	 * 查询用户关注的贴吧
	 */
	public List<UserFollowConversation> selectConversationFollow(
			UserFollowConversation userFollowConversation) {
		if (userFollowConversation.getUserId() == null
				|| "".equals(userFollowConversation.getUserId()))
			throw new RuntimeException("用户id不能为空");
		// if(userFollowConversation.getConversationId() == null ||
		// "".equals(userFollowConversation.getConversationId()))
		// throw new RuntimeException("贴吧id不能为空");
		return userFollowConversationMapper
				.selectConversationFollow(userFollowConversation);
	}

	/**
	 * 更新贴吧数据
	 */
	public void updateConversation(Conversation conversation) {
		if (conversation.getId() == null || "".equals(conversation.getId())) {
			throw new RuntimeException("贴吧id不能为空");
		}
		conversationMapper.update(conversation);
	}

	/**
	 * 查询最新的贴吧帖子数据(用户最后回复的帖子数据)
	 */
	public Map<String, Object> selectConversationStatistics(
			Conversation conversation) {
		if(conversation.getId() == null || "".equals(conversation.getId()))
			throw new RuntimeException("贴吧id不能为空");
		return conversationMapper.selectConversationStatistics(conversation);
	}

}
