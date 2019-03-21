package com.mcgj.utils;

import java.util.Map;

/**
 * �ַ�������
 * @author �
 * @address ����
 * @date 2019-02-25
 *
 */
public class CharUtil {
	
	//���������ַ�
	private static String[][] FilterChars={{"<","&lt;"},{">","&gt;"},{" ","&nbsp;"},{"\"","&quot;"},{"&","&amp;"},   
            {"/","&#47;"},{"\\","&#92;"},{"\n","<br>"}};   
	
	//����ͨ��javascript�ű������ύ���ַ�   
	private static String[][] FilterScriptChars={{"\n","\'+\'\\n\'+\'"},   
	                                {"\r"," "},{"\\","\'+\'\\\\\'+\'"},   
	                                        {"\'","\'+\'\\\'\'+\'"}};   
	
	public static String urlFilter(String url){
		for(String[] str:FilterChars){
			if(url.contains(str[0])){
				url = url.replaceAll(str[0], str[1]);
			}
		}
		return url;
	}
	
	/**
	 * �滻str�е��ĸ��ֽ��ַ�
	 * @param str �ַ���
	 * @param replace ��Ҫ�滻������
	 * @return
	 */
	public static String filterStr4Char(String str,String replace){
		if(StringUtil.isEmpty(str)){
			return null;
		}
		if(StringUtil.isEmpty(replace)){
			return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		}
		return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", replace);
	}
	
	/**
	 * �滻str�е��ĸ��ֽ��ַ�
	 * @param str �ַ���
	 * @param replace ��Ҫ�滻������
	 * @return
	 */
	public static Map<String,Object> filterStr4Char(Map<String, Object> map,String replace){
		if(MapUtil.isEmpty(map)){
			return null;
		}
		for(String str:map.keySet()){
			if(map.get(str) == null){
				continue;
			}
			Object obj = map.get(str);
			//������ַ�������
			if(obj instanceof String[]){
				String[] strArr = (String[])obj;
				String[] newArr = new String[strArr.length];
				int i;
				for(i = 0;i < strArr.length;i++){
					if(StringUtil.isNotEmpty(strArr[i])){
						newArr[i] = CharUtil.filterStr4Char(strArr[i], replace);
					}
				}
				map.put(str, newArr);
				continue;
			}
			//������ַ���
			if(obj instanceof String){
				map.put(str, CharUtil.filterStr4Char(obj.toString(), replace));
				continue;
			}
			throw new RuntimeException("�޷�����Object�еĶ���,�Ҳ���"+obj.getClass().getName() + "��ȷ���Ƿ��н��и����͵Ľ���������");
		}
		return map;
	}
	
	
	public static void main(String[] args) {
//		String url = "http://127.0.0.1:8090/mcgj/conversation/addConversation?conversationType=1&createUserId=1&currentManageUserId=1&conversationName=%E5%90%8D%E4%BE%A6%E6%8E%A2%E6%9F%AF%E5%8D%97&background=&cardBanner=https://imgsa.baidu.com/forum/pic/item/800a19d8bc3eb135bdcf962dab1ea8d3fd1f441f.jpg&photo=5c06301d91263a1026f06313&autograph=%E7%9C%9F%E7%9B%B8%E6%B0%B8%E8%BF%9C%E5%8F%AA%E6%9C%89%E4%B8%80%E4%B8%AA&createDate=1551078711.0701652";
//		System.out.println(url);
	}

}
