package com.mcgj.utils;

import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;

/**
 * �û�������
 * @author ad
 *
 */
public class UserUtil {
	
	/**
	 * ��ȡ��¼�û�����
	 * @return
	 */
	public static User getCurrentUser(String token){
		return (User)RedisHashUtil.get(token);
	}
	
}
