package com.mcgj.utils;

import java.util.Collection;

/**
 * ���Ϲ���
 * @author �
 * @date 2019-03-21
 * @address ����
 *
 */
public class CollectionUtil {

	public static boolean isEmpty(Collection<Object> coll){
		if(coll == null || coll.size() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Collection<Object> coll){
		if(coll != null && coll.size() > 0){
			return true;
		}
		return false;
	}
}
