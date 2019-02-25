package com.mcgj.utils;

/**
 * 字符处理工具
 * @author 杨晨
 * @address 深圳
 * @date 2019-02-25
 *
 */
public class CharUtil {
	
	//过滤特殊字符
	private static String[][] FilterChars={{"<","&lt;"},{">","&gt;"},{" ","&nbsp;"},{"\"","&quot;"},{"&","&amp;"},   
            {"/","&#47;"},{"\\","&#92;"},{"\n","<br>"}};   
	
	//过滤通过javascript脚本处理并提交的字符   
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
	public static void main(String[] args) {
		String url = "http://127.0.0.1:8090/mcgj/conversation/addConversation?conversationType=1&createUserId=1&currentManageUserId=1&conversationName=%E5%90%8D%E4%BE%A6%E6%8E%A2%E6%9F%AF%E5%8D%97&background=&cardBanner=https://imgsa.baidu.com/forum/pic/item/800a19d8bc3eb135bdcf962dab1ea8d3fd1f441f.jpg&photo=5c06301d91263a1026f06313&autograph=%E7%9C%9F%E7%9B%B8%E6%B0%B8%E8%BF%9C%E5%8F%AA%E6%9C%89%E4%B8%80%E4%B8%AA&createDate=1551078711.0701652";
		System.out.println(url);
	}
}
