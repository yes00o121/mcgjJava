package com.yc.spider.tieba.factory.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.yc.spider.tieba.util.HttpClientUtil;

public class TieziLouchenService {
	
	private String serverIp;
	
	private String html;
	
	private String converstionChildId;//贴子的id
	
	private String userId;//楼主id
	
	private String imgIp = "http://localhost:8090/mcgj/common/image?imgId=";//图片地址
	
	private String page = "50";//获取的楼层页数,最多50页
	
	//正则表达式
		private String lc = "<div*.?id=\"post_content.*?>.*?</div>";//获取所有楼层div
	
	public TieziLouchenService(){}
	
	public TieziLouchenService(String serverIp,String html,String userId,String conversationChildName,String conversationChildId){
		this.serverIp = serverIp;
		this.html = html;
		this.userId = userId;
//		this.conversationChildName = conversationChildName;
		this.converstionChildId = conversationChildId;
	}
	
	/**
	 * 处理贴子的楼层数据
	 */
	public void handlerTieziLoucehn(){
		List<String> photo = new ArrayList<String>();//楼层头像
		List<String> username =  new ArrayList<String>();//楼层用户名称
		List<String> content = new ArrayList<>();//楼层内容
		List<String> date = new ArrayList<>();//楼层回复时间
		Pattern pattern = Pattern.compile(lc);
		Matcher matcher = pattern.matcher(html);
		//楼层数数据
		while(matcher.find()){
			content.add(this.tranformString(this.handlerTieziLoucen(matcher.group())));
		}
		//处理楼主头像
		pattern = Pattern.compile("<img username=\".*?src=\".*?>");
		matcher = pattern.matcher(html);
		while(matcher.find()){
			String img = matcher.group();
			photo.add(this.lcyh(img));
		}
		//处理用户名称
		pattern = Pattern.compile("<a data-field.*?>.*?</a>");
		matcher = pattern.matcher(html);
		while(matcher.find()){
			username.add(matcher.group().replaceAll("<a.*?>", "").replaceAll("</a>|<img.*?>", ""));
		}
		//获取楼层的时间
		pattern = Pattern.compile("<span class=\"tail-info\">.*?-.*?-.*?:.*?</span>|[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{2}");
		matcher = pattern.matcher(html);
		while(matcher.find()){
			String group = matcher.group();
//			System.out.println(group);
			String str = group.replaceAll(".*?tail-info\">", "").replaceAll("<.*?>", "");
//			System.out.println(str);
			
			//转为date后转换为时间戳
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try{
				Long time  = sdf.parse(str).getTime();
				date.add(time+"");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//		System.out.println(username.size()+"-"+photo.size()+"-"+content.size()+"-"+date.size());
//		System.out.println(photo.size());
//		System.out.println(content.size());
		if(photo.size() == username.size() && username.size() == content.size() && content.size() == photo.size()){
			for(int i=0;i<photo.size();i++){
				this.insertLouchen( username.get(i),content.get(i), this.converstionChildId, photo.get(i),date.get(i));
			}
		}
		//处理下一页的数据
		//--------------------------------------------------------后续添加
		
	}
	
	//插入楼层数据
	private void insertLouchen(String username,String content,String conversationChildId,String photo,String time){
		//根据用户名称获取用户数据，判断用户是否存在，不存在插入数据库
		JSONObject json;
		String result = HttpClientUtil.sendGet(this.serverIp+"/spider/selectUserByAccount", "account="+username);
		if(result != null && !"".equals(result)){
			//用户存在
			json = JSONObject.parseObject(result);
		}else{
			//用户不存在
			String param = "photo="+photo+"&account="+username+"&admin=0&password=123456&userName="+username;
			result = HttpClientUtil.sendGet(this.serverIp+"/user/register", param);
			json = JSONObject.parseObject(JSONObject.parseObject(result).getString("result"));
		}
		String yhid = json.getString("id");//获取楼层用户的id
		String isManage = "0";//是否楼主
		//判断当前用户id是否是楼主
		if(this.userId.equals(yhid)){
			isManage = "1";
		}
		//插入楼层
		String param = "userId="+yhid+"&conversationChildId="+this.converstionChildId+"&content="+content+"&state=1&isLook=0&isManage="+isManage+"&time="+time;
		HttpClientUtil.sendGet(this.serverIp+"/spider/addConversationChildChild", param);
		System.out.println(this.serverIp+"/spider/addConversationChildChild?"+param);
		
	}
	
	//获取楼层头像
	private String lcyh(String img){
		//用户头像本地化
		String url = img.replaceAll(".*?data-tb-lazyload=\".*?|.*?data-tb-lazyload='.*?|.*?src=\".*?|.*?src='.*?", "").replaceAll("\".*?>|'.*?>", "");
		String result = HttpClientUtil.sendPost(this.serverIp+"/common/upNetWorkImg", "url="+url);
		return result;
	}
	
	//处理贴子的楼层内容
	private String handlerTieziLoucen(String div){
//		String element = div.replaceAll("<a.*?>|</a>|<br>|</br>", "");//去除所有a和br
		String element = div.replaceAll("<a.*?>|</a>", "");//去除所有a
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
	private String tranformString(String str){
		str = str.replace("&", "%26");
		str = str.replace(" ", "%20");
		str = str.replaceAll("，", ",");
//		str = str.replaceAll("?", "?");
		return str;
	}
}
