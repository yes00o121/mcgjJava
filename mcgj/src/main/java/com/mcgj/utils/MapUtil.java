package com.mcgj.utils;

import java.util.Map;

/**
 * map����
 * @author �
 * @date 2019-03-21
 * @address ����
 *
 */
public class MapUtil {
	
	public static boolean isEmpty(Map<String,Object> map){
		if(map == null || map.size() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Map<String,Object> map){
		if(map != null && map.size() > 0){
			return true;
		}
		return false;
	}
}
