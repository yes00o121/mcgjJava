package com.yc.spider.tieba.factory.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.yc.spider.tieba.factory.ISpiderFactory;
import com.yc.spider.tieba.util.ConnectionUtil;
import com.yc.spider.tieba.util.HttpClientUtil;
import com.yc.tieba.entity.Conversaion;

public class SpiderTieBaFactoryImpl implements ISpiderFactory{

	private String tiebaName;//贴吧名称
	
	private String type;//贴吧类型
	
	private String ip = "http://tieba.baidu.com/f?kw=";//贴吧请求路径
	
	private Connection conn;
	
	private List<String> conversationNames;//贴吧名称集合用于判断是否相同的贴吧
	
//	private Map<String,Object> map = new HashMap<String,Object>();//三层map对象
	
	//正则表达式
//	private String conversaionName = "";//贴吧签名正则 
	
	
	public SpiderTieBaFactoryImpl(){
		conn = ConnectionUtil.getConn();//获取连接
	}
	
	/**
	 * 获取贴吧数据
	 */
	@Override
	/**
	 * 
	 * @param param 贴吧名
	 * @param type  贴吧类型
	 */
	public void getDataList(String param,String type) {
		try {
			this.tiebaName = param;//获取要查询的贴吧名称
			this.type = type;//贴吧类型
			String res = HttpClientUtil.sendPost(this.ip + tiebaName, "");
			res = res.trim();
			getConversaion(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//处理贴吧数据
	private Conversaion getConversaion(String html) throws UnsupportedEncodingException{
		String str = HttpClientUtil.sendGet(this.serverIp+"/conversation/selectConversationByName","conversationName="+this.tiebaName);
		if(str ==null){
			throw new RuntimeException("查询本地贴吧异常");
		}
		String scuess = JSONObject.parseObject(str).getString("success");
		Boolean state = false;
		if("true".equals(scuess)){
			state = true;
		}
		//判断贴吧是否存在，存在只进行楼层操作，不新增贴吧数据
		if(state){
			//贴吧存在,只写入楼层数据
			System.out.println("贴吧存在。处理贴子数据");
			String id = JSONObject.parseObject(JSONObject.parseObject(str).getString("result")).getString("id");
			new TieZiService(this.serverIp,html,id,tiebaName).tiebaHandler();
		}else{
			//贴吧不存在,写入贴吧数据 
			Conversaion conversaion = new Conversaion();//贴吧对象
			//获取签名
			Pattern compile = Pattern.compile("<p class=\"card_slogan\">.*?</p>");
			Matcher matcher = compile.matcher(html);
			if(matcher.find()){
				String cur = matcher.group();
				cur = cur.replaceAll("<p.*?>", "").replaceAll("<.*?>", "");//贴吧签名
				cur = this.tranformString(cur);
				System.out.println("签名:"+cur);
				conversaion.setAutograph(cur);
			}
			//贴吧名称
			conversaion.setConversaionName(this.tiebaName);
			//贴吧头像
			compile = Pattern.compile("<img class=\"card_head_img\"(.*?)/.*?>");
			matcher = compile.matcher(html);
			if(matcher.find()){
				String cur = matcher.group();
				cur = cur.replaceAll("<img.*?src=\"", "").replaceAll("\".*?>", "").replaceAll("amp;", "");
				cur = this.tranformString(cur);
				System.out.println("贴吧头像:"+cur);
				//上传头像
				String result = HttpClientUtil.sendPost(this.serverIp+"/common/upNetWorkImg", "url="+cur);
				System.out.println("头像主键:"+result);
				conversaion.setPhoto(result);
			}
			
			//贴吧背景卡片
			compile = Pattern.compile("(<img.*?id=\"forum-card-banner\".*?>|<img.*?class=\"head_banner_img\".*?/>)");
			matcher = compile.matcher(html);
			if(matcher.find()){
				String cur = matcher.group();
				cur = cur.replaceAll("(<img.*?src='|<img.*?src=\")", "").replaceAll("((\'|\").*?>)", "");
				System.out.println("背景卡片:"+cur);
				if(cur != null){
					System.out.println(cur.startsWith("//"));
					//开头的卡片直接不要了
					if(!cur.startsWith("//")){
						//上传背景卡片
						String result = HttpClientUtil.sendPost(this.serverIp+"/common/upNetWorkImg", "url="+cur);
						System.out.println("背景卡片主键:"+result);
						conversaion.setCardBanner(result);
					}
				}
			}
			//插入贴吧数据
//			System.out.println("conversationName="+conversaion.getConversaionName()+"&conversationType"+this.type+"&conversationType=1&createUserId=1&currentManageUserId=1&photo="+conversaion.getPhoto()+"&cardBanner="+conversaion.getCardBanner()+"&autograph="+conversaion.getAutograph());
			String result = HttpClientUtil.sendGet(this.serverIp+"/spider/addConversation", "conversationName="+conversaion.getConversaionName()+"&conversationType"+this.type+"&conversationType="+this.type+"&createUserId=1&currentManageUserId=1&photo="+conversaion.getPhoto()+(conversaion.getCardBanner()!= null ? "&cardBanner="+conversaion.getCardBanner() : "" )+(conversaion.getAutograph() != null ? "&autograph="+conversaion.getAutograph()+"":""));
			JSONObject parseObject = JSONObject.parseObject(result);
			if(parseObject.getString("success").equals("false")){
				throw new RuntimeException("插入贴吧数据异常:"+parseObject.getString("message"));
			}
			//获取贴吧id传入贴子处理方法
			String id = JSONObject.parseObject(JSONObject.parseObject(result).getString("result")).getString("id");
			new TieZiService(this.serverIp,html,id,tiebaName).tiebaHandler();
		}
		return null;
	}
	
	
	
	@Override
	public List<Map<String, Object>> analysisData(String param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) throws ParseException {
		ISpiderFactory tiebaFactory = new SpiderTieBaFactoryImpl();
		tiebaFactory.getDataList("高圆圆","1");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		System.out.println(sdf.parse("2016-08-17 13:25"));;
	}
	
	/**
	 * 根据名称查询贴吧
	 * @return
	 */
	private String queryConversaionName(String conversaionName){
		return null;
	}

	private String tranformString(String str){
		str = str.replace("&", "%26");
		str = str.replace(" ", "%20");
		return str;
	}

}
