package com.mcgj.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ��ȡ�����ļ�������
 * @author ad
 *
 */
public class PropertiesUtil {
	
	private static Logger log =Logger.getLogger(PropertiesUtil.class);
	
	private static Properties properties = new Properties();
	
	/**
	 * ��ȡ�����е�value
	 * @param �ļ�·��
	 * @param key
	 * @return
	 */
	public static String get(String dirName,String key){
		try{
			InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(dirName);
			properties.load(is);//�����ļ���
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return (String)properties.get(key);
	}
	
	/**
	 * ��ȡ��¼�Ựʱ��
	 * @return
	 */
	public static Integer getLoginDelay(){
		return Integer.parseInt(PropertiesUtil.get("delay.properties","login_delay"));
	}
	
	/**
	 * ��ȡ��֤��Ựʱ��
	 * @return
	 */
	public static Integer getVerificationDelay(){
		return Integer.parseInt(PropertiesUtil.get("delay.properties","verification_delay"));
	}
}
