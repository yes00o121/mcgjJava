package com.mcgj.utils;

/**
 * �ַ���������
 * @author �
 *@create_date 2018-12-05 21:47
 *@address �Ϸ�
 */
public class StringUtil {
	
	/**
	 * �Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(str != null && !"".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
}
