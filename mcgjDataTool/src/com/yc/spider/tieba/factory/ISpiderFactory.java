package com.yc.spider.tieba.factory;

import java.util.List;
import java.util.Map;

public interface ISpiderFactory {
	
	public static  String serverIp = "http://127.0.0.1:8090/mcgj";
	
	/**
	 * ��ȡ���ݼ���
	 * @param param������Ĳ���
	 * @return
	 */
	void getDataList(String param,String type);
	
	
	/**
	 * ����ҳ������
	 * @param param
	 */
	List<Map<String,Object>> analysisData(String param);
	
}
