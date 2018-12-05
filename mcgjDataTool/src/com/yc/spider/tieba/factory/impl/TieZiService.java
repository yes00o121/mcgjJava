package com.yc.spider.tieba.factory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.yc.spider.tieba.util.HttpClientUtil;

/**
 * 贴子处理
 * @author Administrator
 *
 */
public class TieZiService {
	
	private int page = 10;//获取10页的贴子数据
	
	private String serverIp;
	
	private String html;
	
	private String converstionId;//贴吧id
	
	private String conversationChildId;//当前贴子id
	
	private String tiebaName;//贴吧名称
	
	private String baiduIp = "http://tieba.baidu.com";//百度ip
	
	private String imgIp = "http://localhost:8090/mcgj/common/image?imgId=";//图片地址
	
	//正则表达式
	private String lc = "<div*.?id=\"post_content.*?>.*?</div>";//获取所有楼层div
	
	public TieZiService(){}
	
	public TieZiService(String serverIp,String html,String converstionId,String tiebaName){
		this.serverIp = serverIp;
		this.html = html;
		this.converstionId = converstionId;
		this.tiebaName = tiebaName;
	}
	/**
	 * 处理贴吧数据
	 * @param html 页面
	 * @param conversationId 贴吧主键
	 * @return
	 */
	public Boolean tiebaHandler(){
//		int index = 0;
		int pn = 0;//贴吧页数,一页50
//		List<String> listHtml = new ArrayList<String>();
		for(int i=0;i<page;i++){
			String html = this.getHtml(this.baiduIp+"/f?kw="+this.tiebaName+"&pn="+pn);
//			listHtml.add(html);
			new Thread(){
				public void run(){
//					System.out.println("开启线程.............");
					List<String> urls = new ArrayList<String>();
					Pattern pattern = Pattern.compile("<a rel=\"noreferrer\" href=\"/p.*?title.*?class=\"j_th_tit \">");
					Matcher matcher = pattern.matcher(html);
					while(matcher.find()){
						String group = matcher.group();
						String url = baiduIp+group.replaceAll(".*?href=\"", "").replaceAll("\".*?>", "");
						urls.add(url);
					}
					analysisTieziUrl(urls);
				}
			}.start();
			pn+=50;
		}
		//遍历指定页数的数据
//		for(String html:listHtml){
//			//开启指定页数的线程进行处理
//			new Thread(){
//				public void run(){
//					List<String> urls = new ArrayList<String>();
//					Pattern pattern = Pattern.compile("<a rel=\"noreferrer\" href=\"/p.*?title.*?class=\"j_th_tit \">");
//					Matcher matcher = pattern.matcher(html);
//					while(matcher.find()){
//						String group = matcher.group();
//						String url = baiduIp+group.replaceAll(".*?href=\"", "").replaceAll("\".*?>", "");
//						urls.add(url);
//					}
//					analysisTieziUrl(urls);
//				}
//			}.start();
//		}
		return null;
	}
	
	//解析贴子url
	private  List<String>  analysisTieziUrl(List<String> urls){
		for(String url:urls){
//			System.out.println("开始解析:"+url);
			new Thread(){
				public void run(){
					analysisHtml(getHtml(url));
				}
			}.start();
//			break;//测试
		}
		return null;
	}
	
	//获得楼层的页面
	private String getHtml(String url){
//		System.out.println("开始请求页面...........................");
		String html = HttpClientUtil.sendPost(url, "");
//		System.out.println("请求页面结束...........................");
		return html;
	}
	
	//解析html
	public  void analysisHtml(String html){
		this.handlerTiezi(html);
//		System.out.println(html);
	}
	
	//处理贴子数据,第一页,获取第一楼的数据保存到贴子表
	private void handlerTiezi(String html){
		String title = "";
		String content = "";//内容
		String username = "";//用户名称
		String photo = "";//用户头像
		Pattern pattern = Pattern.compile("<h3.*?core_title_txt.*?>|<h1.*?core_title_txt.*?>");
		Matcher matcher = pattern.matcher(html);
		if(matcher.find()){
			title = matcher.group().replaceAll(".*?title=\"", "").replaceAll("\".*?>", "");
		}
		//获取楼主数据
		pattern = Pattern.compile(lc);
		matcher = pattern.matcher(html);
		//获取楼主div
		if(matcher.find(0)){
			content = this.handlerTieziLoucen(matcher.group(0));
		}
		
		//处理楼主头像
		pattern = Pattern.compile("<img username=\".*?src=\".*?>");
		matcher = pattern.matcher(html);
		if(matcher.find(0)){
			String img = matcher.group(0);
			photo = this.lcyh(img);
		}
		//处理用户名称
		pattern = Pattern.compile("<a data-field.*?>.*?</a>");
		matcher = pattern.matcher(html);
		if(matcher.find(0)){
			username = matcher.group(0).replaceAll("<a.*?>", "").replaceAll("</a>|<img.*?>", "");
		}
		String yh = this.insertYh(photo,username);//插入用户数据
		String id = JSONObject.parseObject(yh).getString("id");//获取用户主键
		boolean state = this.insertTiezi(title,content,id);//插入贴子数据
		if(!state){
			System.out.println("贴子存在不处理"+title);
			return;//贴子存在不处理楼层
		}
		//处理楼层数据
		new TieziLouchenService(this.serverIp, html, id, username,this.conversationChildId).handlerTieziLoucehn();
		
	}
	
	//处理贴子的楼层内容
	private String handlerTieziLoucen(String div){
//		String element = div.replaceAll("<a.*?>|</a>|<br>|</br>", "");//去除所有a和br
		String element = div.replaceAll("<a.*?>|</a>", "");//去除所有a
		element = element.replaceAll("<img.*?static.*?>", "");//去除贴吧表情
		element = element.replaceAll("<div.*?bdstatic.*?>", "");//去除不需要图片
		element = element.replaceAll("<img.*?gps0.*?>", "");//去除不需要图片
		//取出楼层的图片本地化
		Pattern pattern = Pattern.compile("<img.*?>");
		Matcher matcher = pattern.matcher(element);
	    int index = 0;//下标
		Long flag = new Date().getTime();//记录图片的序号,用于替换图片不出问题
		while(matcher.find()){
			String url = matcher.group();//图片地址
			element = element.replaceAll(url, flag+index+"");
			index++;
		}
		index = 0;
		pattern = Pattern.compile("<img.*?>");
		matcher = pattern.matcher(div);
		while(matcher.find()){
			//获取图片的地址,本地化
			String imgUrl = matcher.group().replaceAll(".*?src=\"|.*?src=\'", "").replaceAll("\".*?>|'.*?>", "");
			String result = HttpClientUtil.sendPost(this.serverIp+"/common/upNetWorkImg", "url="+imgUrl);
			//元素下的img替换为本地的img
			String imgElement = "<img src=\""+this.imgIp + result+"\" />";
			element = element.replaceAll(flag+index+"", imgElement);
			index++;
		}
		return element.replaceAll("id=\".*?>|id='.*?>", ">");
	}
	
	//获取楼层头像
	private String lcyh(String img){
		//用户头像本地化
		String url = img.replaceAll(".*?data-tb-lazyload=\".*?|.*?data-tb-lazyload='.*?|.*?src=\".*?|.*?src='.*?", "").replaceAll("\".*?>|'.*?>", "");
//		System.out.println(url);
		String result = HttpClientUtil.sendPost(this.serverIp+"/common/upNetWorkImg", "url="+url);
		return result;
	}
	
	//插入贴子数据
	private boolean insertTiezi(String title,String content,String userId){
		title = tranformString(title);
		content = tranformString(content);
		//使用标题判断贴吧是否存在,不存在插入数据，否则不做操作
		String result = HttpClientUtil.sendGet(this.serverIp+"/spider/selectConversationChild", "title="+title);
		if(result != null && !"".equals(result)){
			return false;//贴子存在，不继续操作
		}
		String param = "title="+title+"&content="+content+"&userId="+userId+"&state=1&conversationId="+this.converstionId;
//		System.out.println(param);
		result = HttpClientUtil.sendGet(this.serverIp+"/spider/addConversationChild", param);
//		System.out.println("插入贴吧结束");
//		System.out.println(result);
		String success = JSONObject.parseObject(result).getString("success");
		if("false".equals(success)){
			throw new RuntimeException(JSONObject.parseObject(result).getString("message"));
		}
		this.conversationChildId = JSONObject.parseObject(JSONObject.parseObject(result).getString("result")).getString("id");
		return true;
	}
	
	//插入用户数据
	private String  insertYh(String photo,String username){
		//判断用户是否存在，不存在进行插入操作
		String result = HttpClientUtil.sendGet(this.serverIp+"/spider/selectUserByAccount", "account="+username);
		if(result != null && !"".equals(result)){
			return JSONObject.parseObject(result).toString();
		}
		String param = "photo="+photo+"&account="+username+"&admin=0&password=123456&userName="+username;
//		System.out.println(param);
		result = HttpClientUtil.sendGet(this.serverIp+"/user/register", param);
//		System.out.println(result);
		String success = JSONObject.parseObject(result).get("success").toString();
		if("false".equals(success)){
//			throw new RuntimeException(JSONObject.parseObject(result).getString("message"));
			return null;
		}
		return JSONObject.parseObject(result).getString("result");
	}
	
	private String tranformString(String str){
		str = str.replace("&", "%26");
		str = str.replace(" ", "%20");
		str = str.replaceAll("，", ",");
		return str;
	}
}
