package com.yc.tieba;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSONObject;
import com.yc.Util;

/**
 * 贴吧对象
 * @author ad
 *下载的过程，指定贴吧名称，找到贴吧读取默认页数，对每一个贴子进行解析，进入楼层同时解析当前用户是否存在，如存在继续进行数据的写入，不存在将该用户入库，默认读取完所有楼层后继续下一个贴子抓取
 */
public class Conversation {
	
	private String address = "http://127.0.0.1:8090/mcgj";//本地服务器地址
	
	private String dwImg = "/common/image?imgId=";//下载图片
	
	private String upImg = "/common/uploadFile";//上传图片
	
	private String tiebaUrl = "https://tieba.baidu.com/f?fr=wwwt&kw=";//贴吧地址
	
	private String token = "20d98a33aa5236e2ccf3c35fd2b573fd";
	
//	private String tiebaUrl = "https://tieba.baidu.com/f?ie=utf-8&kw=";
	
//	private String tiebaUrl = "http://tieba.baidu.com/f?fr=wwwt&kw=%E6%96%B0%E5%9E%A3%E7%BB%93%E8%A1%A3&red_tag=h1105420967";//贴吧地址
	
	private String tiebaName;//贴吧名称
	
//	private static String HREF_REGULAR = "href=\"(/p.*\")";
//	private static String HREF_REGULAR = "href=\"/p/.*\">";
	private static String HREF_REGULAR = "href=\"/p([\"\']*)([^\"\']*[\"\'])";
	
	private Integer defaultLimit = 1;//默认下载的贴子数
	
	public Conversation(){
		
	}
	public Conversation(String tiebaName){
		this.tiebaName = tiebaName;
		this.analysisConversation(tiebaName);//解析贴吧
	}
	/*
	//运行下载贴吧数据
	private void running(){
		
		String url = Util.getHTML(this.tiebaUrl+this.tiebaName+"&pn=0");//贴吧主页的url
		List<String> analysisUrl = this.analysisUrl(url);//获取当前贴吧当前页所有的url
//		analysisUrl.get(0);
		
		String html = Util.getHTML("https://tieba.baidu.com"+analysisUrl.get(0));
		this.analysisFloorOne(html);
	}
	*/
	//解析当前贴吧的中所有帖子的url
	private List<String> analysisUrl(String url){
		try {
			List<String> imgs = new ArrayList<String>();
			Pattern pattern = Pattern.compile(HREF_REGULAR);
//			Pattern patternBase64 = Pattern.compile(IMAGE_REGULAR_BASE64);
			Matcher matcher = pattern.matcher(url);
			while(matcher.find()){
				String a = matcher.group();
				String address = a.split("=\"")[1];
				address = address.substring(0,address.length()-1);//删除最后一个"
				imgs.add(address);
//				System.out.println(address);
			}
			return imgs;
//			System.out.println(imgs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//解析楼层数据,标题为h3
	private List<String> analysisFloorOne(String html){
		
		try {
			String html2 = Util.getHTML(html);
			html2 = html2.trim();
			String h3 = "<h3.*?title=.*?(.*?\">)";
			Pattern pattern = Pattern.compile(h3);
			Matcher matcher = pattern.matcher(html2);
//			System.out.println(html2);
			String title= null;//标题
			String content = null;//内容
			String conversationName = null;//贴吧名
			String userName = null;//用户名
			String photo = null;//头像
			//获取贴子标题
			if(matcher.find()){
				title = matcher.group().split("title=\"")[1];
				title = title.split("\"")[0];
				System.out.println(title);
				String name = "title=\".*?><返回";
				Pattern pattern3 = Pattern.compile(name);
				Matcher matcher3 = pattern3.matcher(html2);
				if(matcher3.find()){
					String m3 = matcher3.group();
					String[] strs = m3.split("\"><返回")[0].split("title=\"");
					conversationName = strs[strs.length-1];
					System.out.println(conversationName);
				}
			}else{
				//标题获取不到启动二号方案
				this.analysisFloorTwo(html2,html);
				return null;//标题获取不到直接退出
			}
			//获取一楼所有的内容
			String floor = "<div class=\"l_post l_post_bright j_l_post clearfix  \".*?pb_tpoint.*?>";
			Pattern pattern2 = Pattern.compile(floor);
			Matcher matcher2 = pattern2.matcher(html2);
			List<String> list = new ArrayList<String>();
			while(matcher2.find()){
				String a = matcher2.group();
				String[] str = a.split("data-field=\'");
				if(str.length == 2){
					String str2 = str[1].replace("&quot;", "\"").replace("&lt;","<").replace("&gt;",">").replace("⭐","").replace("🙇","");
					str2 = str2.substring(0,str2.length()-3);
					System.out.println(str2);
					list.add(str2);
				}
			}
			List<String> list2 = new ArrayList<String>();
			List<String> list3 = new ArrayList<String>();
			//获取当前楼层的用户名和头像
			String name = "<img username=\".*?/>";
			Pattern pattern3 = Pattern.compile(name);
			Matcher matcher3 = pattern3.matcher(html2);
			System.out.println("+++");
			while(matcher3.find()){
				String n = matcher3.group();
				String p =null;
				if(n.indexOf("data-tb-lazyload") != -1){
					p= n.split("data-tb-lazyload=\"")[1].split("\"")[0];
				}else{
					p = n.split("src=\"")[1].split("\"")[0];
				}
				n =n.split("username=\"")[1].split("\"")[0];
				list2.add(n);
				list3.add(p);
			}
			String oneFloor = list.get(0).toString();
			userName = list2.get(0).toString();
			photo = list3.get(0).toString();
			System.out.println(userName);
			System.out.println(photo);
			String userId = this.getUserId(userName,this.imgDownload(photo).split("imgId=")[1]);
			JSONObject parseObject = JSONObject.parseObject(oneFloor);
			JSONObject parseObject2 = JSONObject.parseObject(parseObject.get("content").toString());
			content = parseObject2.get("content").toString();
			content = this.contentHandler(content);
//			对content内容进行处理将里面的img标签的图片下载到本地库中进行替换
			String childId = this.writeConversationSurface(title, content, conversationName,userId);//获取贴子id
			System.out.println("进行楼层呢操作了。。。。。");
			for(int i=1;i<list2.size();i++){
				JSONObject parseObject3 = JSONObject.parseObject(list.get(i));
				JSONObject parseObject4 = JSONObject.parseObject(parseObject3.get("content").toString());
				content = parseObject4.get("content").toString();
				content = this.contentHandler2(content);
				System.out.println("楼层操作。。。");
				System.out.println(list3.get(i));
				String imgId = this.imgDownload(list3.get(i));
				String id = this.getUserId(list2.get(i),imgId.split("imgId=")[1]);
				this.writeFloor(childId, content, id,userId);
			}
			
			//获取的当前贴子的页数
			String total = "共<span.*?</span>页";
			Pattern pattern4 = Pattern.compile(total);
			Matcher matcher4 = pattern4.matcher(html2);
			int number = 0;//贴子总页数
			if(matcher4.find()){
				String group = matcher4.group();
				String[] split = group.split("\">")[1].split("</span");
				number = Integer.parseInt(split[0]);
			}
			System.out.println("总页数为。。。。。。。"+number);
			for(int i=1;i<=number;i++){
				String html3 = Util.getHTML(html+"?pn="+i);
				System.out.println("................................"+html+"?pn="+i);
				
				String html4 = html3.trim();
				//获取当前楼层的用户名和头像
				String name2 = "<img username=\".*?/>";
				Pattern pattern5 = Pattern.compile(name2);
				Matcher matcher5 = pattern5.matcher(html4);
				List<String> list4 = new ArrayList<String>();
				List<String> list5=  new ArrayList<String>();
				System.out.println("+++");
				while(matcher5.find()){
//					System.out.println(matcher3.group());
					String n = matcher5.group();
					String p =null;
					if(n.indexOf("data-tb-lazyload") != -1){
						p= n.split("data-tb-lazyload=\"")[1].split("\"")[0];
					}else{
						p = n.split("src=\"")[1].split("\"")[0];
					}
					n =n.split("username=\"")[1].split("\"")[0];
					list4.add(n);
					list5.add(p);
				}
				String floor2 = "<div class=\"l_post l_post_bright j_l_post clearfix  \".*?pb_tpoint.*?>";
				Pattern pattern6 = Pattern.compile(floor2);
				Matcher matcher6 = pattern6.matcher(html4);
				List<String> list6 = new ArrayList<String>();
				while(matcher6.find()){
					String a = matcher6.group();
					String[] str = a.split("data-field=\'");
					if(str.length == 2){
						String str2 = str[1].replace("&quot;", "\"").replace("&lt;","<").replace("&gt;",">").replace("⭐","").replace("🙇","");
						str2 = str2.substring(0,str2.length()-3);
						list6.add(str2);
					}
				}
				for(int j=1;j<list4.size();j++){
					JSONObject parseObject3 = JSONObject.parseObject(list6.get(j));
					JSONObject parseObject4 = JSONObject.parseObject(parseObject3.get("content").toString());
					content = parseObject4.get("content").toString();
					content = this.contentHandler2(content);
					System.out.println("楼层操作。。。");
					String imgId = this.imgDownload(list5.get(j));
					String id = this.getUserId(list4.get(j),imgId.split("imgId=")[1]);
					this.writeFloor(childId, content, id,userId);
				}
				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//写贴子楼层数据
	private void writeFloor(String childId,String content,String userId,String userId2){
		try {
			URL url = new URL(this.address+"/spider/addFloorDataSpider");
			URLConnection openConnection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)openConnection;
			httpConn.setDoOutput(true);   //需要输出
		    httpConn.setDoInput(true);   //需要输入
		    httpConn.setUseCaches(false);  //不允许缓存
		    httpConn.setRequestMethod("POST");   //设置POST方式连接
		    //设置请求属性
		    httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		    httpConn.setRequestProperty("Charset", "UTF-8");
		    //建立输入流，向指向的URL传入参数
		    DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
		    dos.writeBytes("conversationChildId="+Integer.parseInt(childId)+"&userId="+Integer.parseInt(userId)+"&content="+URLEncoder.encode(content,"utf-8")+"&userId2="+Integer.parseInt(userId2)+"&token="+token+"");
		    dos.flush();
		    dos.close();
		    httpConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getUserId(String userName,String photo){
		ByteArrayOutputStream baos = null;
		InputStream is = null;
		try {
			URL url  = new URL(this.address+"/user/selectIsExists?userName="+URLEncoder.encode(userName,"utf-8")+"&photo="+photo);
			URLConnection openConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
			if(httpURLConnection.getResponseCode() != 200)
				throw new RuntimeException("获取用户失败");
			is = httpURLConnection.getInputStream();
			baos = new ByteArrayOutputStream();
			int read = 0;
			byte[] bts = new byte[1024];
			while((read= is.read(bts)) != -1){
				baos.write(bts,0,read);
			}
			String id = new String(baos.toByteArray());
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(baos!= null)
					baos.close();
				if(is != null)
					is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "1";
	}
	//百度贴吧不同的页面，标题为h1
	private List<String> analysisFloorTwo(String html,String url){
		try {
			String title = "";//标题
			String content = "";//内容
			String conversationName = "";//贴吧名称
			String h1 = "<h1.*?title.*?>";
			Pattern pattern = Pattern.compile(h1);
			Matcher matcher = pattern.matcher(html);
			if(matcher.find()){
				title = matcher.group();
				title = title.split("title=\"")[1].split("\"")[0];
				System.out.println("标题为。。。。。。。"+title);
			}else{
				//找不到h1的标题
			}
			List<String> floors = new ArrayList<String>();//楼层内容
			String cc = "<cc.*?</cc>";
			Pattern pattern2 = Pattern.compile(cc);
			Matcher matcher2 = pattern2.matcher(html);
			while(matcher2.find()){
				String str = matcher2.group();
				str = str.split("clearfix\">")[1].trim().split("</div><br></cc>")[0].replace("⭐","").replace("🙇","");
//				System.out.println(str);
				floors.add(str);
			}
			//获取楼主的发言
			String floor = floors.get(0);
//			System.out.println(floor.replace("🙇",""));
			content = this.contentHandler(floor);
			System.out.println(content);
			//获取贴吧名称
			String name = "fname=.*?<title>";
			Pattern pattern3 = Pattern.compile(name);
			Matcher matcher3 = pattern3.matcher(html);
			if(matcher3.find()){
				conversationName = matcher3.group().replace("⭐","").replace("🙇","").split("\"><meta")[0].split("\"")[1].replace("&amp;","").replace("&lt;","<").replace("&gt;",">").trim();
				System.out.println(conversationName);
			}
			
			List<String> list2 = new ArrayList<String>();
			List<String> list3 = new ArrayList<String>();
			//获取当前楼层的用户名和头像
			String name1 = "<img username=\".*?/>";
			Pattern pattern4 = Pattern.compile(name1);
			Matcher matcher4 = pattern4.matcher(html);
			while(matcher4.find()){
//				System.out.println(matcher3.group());
				String n = matcher4.group();
				String p =null;
				if(n.indexOf("data-tb-lazyload") != -1){
					p= n.split("data-tb-lazyload=\"")[1].split("\"")[0];
				}else{
					p = n.split("src=\"")[1].split("\"")[0];
				}
				n =n.split("username=\"")[1].split("\"")[0];
				list2.add(n);
				list3.add(p);
			}
			String userName = list2.get(0);
			String photo = list3.get(0);
			String userId = this.getUserId(userName,this.imgDownload(photo).split("imgId=")[1]);
			//对content内容进行处理将里面的img标签的图片下载到本地库中进行替换
			String childId = this.writeConversationSurface(title, content, conversationName,userId);
			for(int i=1;i<list2.size();i++){
				System.out.println("!!!!-----------");
				System.out.println(list2.get(i));
				System.out.println(list3.get(i));
				String imgId = this.imgDownload(list3.get(i));//下载图片
//				System.out.println("楼层操作。。。");
				String id = this.getUserId(list2.get(i), imgId.split("imgId=")[1]);
				String con = floors.get(i);
				con = this.contentHandler2(con);//处理楼层内容
				this.writeFloor(childId, con, id,userId);
			}
			
			//获取后续楼层
			//获取的当前贴子的页数
			String total = "共<span.*?</span>页";
			Pattern pattern5 = Pattern.compile(total);
			Matcher matcher5 = pattern5.matcher(html);
			int number = 0;//贴子总页数
			if(matcher5.find()){
				String group = matcher5.group();
				String[] split = group.split("\">")[1].split("</span");
				number = Integer.parseInt(split[0]);
			}
			System.out.println("总页数为。。。。。。。"+number);
			
			for(int i=2;i<=number;i++){
				String html3 = Util.getHTML(url+"?pn="+i);
				System.out.println("................................"+url+"?pn="+i);
				
				String html4 = html3.trim();
				//获取当前楼层的用户名和头像
				String name2 = "<img username=\".*?/>";
				Pattern pattern6 = Pattern.compile(name2);
				Matcher matcher6 = pattern6.matcher(html4);
				List<String> list4 = new ArrayList<String>();
				List<String> list5=  new ArrayList<String>();
				System.out.println("+++");
				while(matcher6.find()){
//					System.out.println(matcher3.group());
					String n = matcher6.group();
					String p =null;
					if(n.indexOf("data-tb-lazyload") != -1){
						p= n.split("data-tb-lazyload=\"")[1].split("\"")[0];
					}else{
						p = n.split("src=\"")[1].split("\"")[0];
					}
					n =n.split("username=\"")[1].split("\"")[0];
					list4.add(n);
					list5.add(p);
				}
				List<String> floor2 = new ArrayList<String>();//楼层内容
				String cc2 = "<cc.*?</cc>";
				Pattern pattern7 = Pattern.compile(cc2);
				Matcher matcher7 = pattern7.matcher(html3);
				while(matcher7.find()){
					String str = matcher7.group();
					str = str.split("clearfix\">")[1].trim().split("</div><br></cc>")[0].replace("⭐","").replace("🙇","");
					floor2.add(str);
				}
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				for(int j=0;j<list4.size();j++){
					System.out.println("楼层操作。。。");
					String imgId = this.imgDownload(list5.get(j));
					String id = this.getUserId(list4.get(j),imgId.split("imgId=")[1]);
					String con = this.contentHandler2(floor2.get(j));
					this.writeFloor(childId, con, id,userId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//内容处理
	private String contentHandler(String content){
		try {
			String img = "<img.*?(.*?>)";
			System.out.println(content);
			Pattern compile = Pattern.compile(img);
			Matcher matcher = compile.matcher(content);
			while(matcher.find()){
//				System.out.println(matcher.group());
				String str  = matcher.group();
				String str2 = str.split("src=\"")[1].split("\"")[0];
				if(str2.indexOf("https://gsp0.baidu.com") == -1 && str2.indexOf("static.tieba.baidu.com") == -1 && str2.indexOf("tb2.bdstatic") == -1 && str2.indexOf("height=\"20\"")== -1){
					System.out.println(str2);
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!@@@@@");
					String imgId = imgDownload(str2);//下载图片
					System.out.println(imgId);
					//替换当前content中的图片
					content = content.replace(str,"<img src=\""+this.address+imgId.trim()+"\">");
				}else{
					System.out.println("不存在。。。。");
					System.out.println(str2);
					//替换当前content中的图片
					content = content.replace(str,"");//如果图片不存在清空图片
				}
			}
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	//内容处理
		private String contentHandler2(String content){
			try {
				String img = "<img.*?(.*?>)";
				Pattern compile = Pattern.compile(img);
				Matcher matcher = compile.matcher(content);
				while(matcher.find()){
					String str  = matcher.group();
					String str2 = str.split("src=\"")[1].split("\"")[0];
					if(str2.indexOf("imgsrc.baidu")!=-1){
						System.out.println(str2);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!@@@@@");
						String imgId = imgDownload(str2);//下载图片
						System.out.println(imgId);
						//替换当前content中的图片
						content = content.replace(str,"<img src=\""+this.address+imgId.trim()+"\">");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return content;
		}
	private String imgDownload(String img){
		ByteArrayOutputStream baos = null;
		InputStream is3 = null;
		try {
			//先将百度的图片下载下来
			URL url2 = new URL(img);
			URLConnection openConnection = url2.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
	        InputStream is = httpURLConnection.getInputStream();
	        String end = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        String newName = "image.jpg";
	        URL url3 = new URL(this.address+this.upImg);
			URLConnection openConnection3 = url3.openConnection();
			HttpURLConnection httpURLConnection3 = (HttpURLConnection)openConnection3;
			httpURLConnection3.setDoInput(true);  
	        httpURLConnection3.setDoOutput(true);  
	        httpURLConnection3.setUseCaches(false);  
	        /* 设置传送的method=POST */
	        httpURLConnection3.setRequestMethod("POST");
	        /* setRequestProperty */
	        httpURLConnection3.setRequestProperty("Connection", "Keep-Alive");
	        httpURLConnection3.setRequestProperty("Charset", "UTF-8");
	        httpURLConnection3.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "*****");
	        /* 设置DataOutputStream */
	        DataOutputStream ds = new DataOutputStream(httpURLConnection3.getOutputStream());
	        ds.writeBytes(twoHyphens + boundary + end);
	        ds.writeBytes("Content-Disposition: form-data; "+ "name=\"file\";filename=\"" + newName + "\"" + end);
	        ds.writeBytes(end);
	        baos = new ByteArrayOutputStream();
	        int read = 0;
	        byte[] bts = new byte[1024];
	        while((read = is.read(bts)) != -1){
	        	baos.write(bts,0,read);
	        }
	        ds.write(baos.toByteArray());
	        ds.writeBytes(end);
	        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);  
	        ds.flush();
	        ds.close();
	        is3 = httpURLConnection3.getInputStream();
	        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
	        int read3 =0;
	        byte[] bts3 = new byte[1024];
	        while((read3 = is3.read(bts3)) != -1){
	        	baos2.write(bts3);
	        }
	        String imgId = new String(baos2.toByteArray());
	        baos2.close();
	        return imgId;//图片id
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(baos != null)
					baos.close();
				if(is3 != null)
					is3.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
				
		}
		
		return null;
	}
	private String writeConversationSurface(String title,String content,String conversationName,String userId){
		System.out.println(title);
		System.out.println(content);
		System.out.println(conversationName);
		ByteArrayOutputStream btas = null;
		ByteArrayOutputStream btas2 = null;
		InputStream is= null;
		InputStream is2 = null;
		//根据贴吧名查询贴吧相关数据
		try {
//			System.out.println(new String(conversationName.getBytes(),"utf-8"));
			URL url = new URL(this.address+"/conversation/selectConversationByName?conversationName="+URLEncoder.encode(conversationName,"utf-8"));
			URLConnection openConnection = url.openConnection();
			HttpURLConnection huc = (HttpURLConnection)openConnection;
			is = huc.getInputStream();
			int read = 0;
			byte[] buff = new byte[1024];
			btas = new ByteArrayOutputStream();
			while((read = is.read(buff)) != -1){
				btas.write(buff,0,read);
			}
			String str = new String(btas.toByteArray(),"utf-8");
			System.out.println(str);
			JSONObject parseObject = JSONObject.parseObject(str);
			Object conversationName2 = parseObject.get("result");
			if(conversationName2 == null){
				JOptionPane.showMessageDialog(null,"系统没创建该贴吧","提示",JOptionPane.ERROR_MESSAGE);
				Main.BtnActive();
				return null;
			}
				
			JSONObject parseObject2 = JSONObject.parseObject(conversationName2.toString());
			String id = parseObject2.get("id").toString();
			System.out.println(id);
			//数据插入库里，默认用户id为14
			URL url2 = new URL(this.address+"/conversationChild/addConversationChild");
			URLConnection openConnection2 = url2.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)openConnection2;
			httpConn.setDoOutput(true);   //需要输出
		    httpConn.setDoInput(true);   //需要输入
		    httpConn.setUseCaches(false);  //不允许缓存
		    httpConn.setRequestMethod("POST");   //设置POST方式连接
		    //设置请求属性
		    httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		    httpConn.setRequestProperty("Charset", "UTF-8");
		    //建立输入流，向指向的URL传入参数
		    DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
		    dos.writeBytes("conversationId="+id+"&userId="+Integer.parseInt(userId)+"&content="+URLEncoder.encode(content,"utf-8")+"&title="+URLEncoder.encode(title,"utf-8")+"&token="+token+"");
		    dos.flush();
		    dos.close();
			if(httpConn.getResponseCode() == 200)
				System.out.println("数据插入成功。。。");
			is2 = httpConn.getInputStream();//获取返回的数据流
			int read2 = 0;
			byte[] bts = new byte[1024];
			btas2 = new ByteArrayOutputStream();
			while((read2 = is2.read(bts)) != -1){
				btas2.write(bts,0,read2);
			}
			String json = new String(btas2.toByteArray());
			Object childId = JSONObject.parseObject(json).get("result");
			return childId.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(btas != null)
					btas.close();
				if(is != null)
					is.close();
				if(is2 != null)
					is2.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 解析贴吧
	 * @param conversationName贴吧名
	 */
	public void  analysisConversation(String conversationName){
		try {
			if(!this.isConversationExists(conversationName)){
				JOptionPane.showMessageDialog(null,"贴吧不存在，无法爬取数据","提示",JOptionPane.ERROR_MESSAGE);
				Main.BtnActive();
				return;
			}
			Main.center_text.append("开始下载...\n");
			Main.center_text.setSelectionStart(Main.center_text.getText().length());
			System.out.println("---------------------------------------------------------");
			int pn = 0;
			for(int i=0;i<defaultLimit;i++){
				String html = Util.getHTML(this.tiebaUrl+URLEncoder.encode(conversationName)+"&pn="+pn);//当前贴吧的页面
				List<String> list = analysisUrl(html);//存放当前页的所有贴子路径
				for(String l:list){
					new Thread(){//开启多个线程处理
						public void run(){
							analysisFloorOne("https://tieba.baidu.com"+l);
						}
					}.start();;
					
				}
				pn+=50;
			}
			Main.center_text.append("下载完毕...\n");
			Main.center_text.setSelectionStart(Main.center_text.getText().length());
			Main.BtnActive();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"贴吧数据爬取失败，请重试","提示",JOptionPane.ERROR_MESSAGE);
			Main.BtnActive();
			return;
		}
	}
	//判断贴吧是否存在
	private Boolean isConversationExists(String conversationName){
		try {
//			/conversation/selectConversationByName
			URL url2 = new URL(this.address+"/conversation/selectConversationByName?conversationName="+URLEncoder.encode(new String(conversationName.getBytes("utf-8"))));
			URLConnection openConnection2 = url2.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)openConnection2;
			InputStream is = httpConn.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = 0;
			byte[] bts = new byte[1024];
			while((read = is.read(bts)) != -1){
				baos.write(bts,0,read);
			}
			String json = new String(baos.toByteArray());
			JSONObject parseObject = JSONObject.parseObject(json);
			Object result = parseObject.get("result");
			if(result != null){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		Conversation c = new Conversation();
//		List<String> analysisFloor = c.analysisFloorOne("https://tieba.baidu.com/p/3499034331");//问道
//		List<String> analysisFloor = c.analysisFloorOne("http://tieba.baidu.com/p/5175163008");//新垣结衣
//		List<String> analysisFloor = c.analysisFloorOne("https://tieba.baidu.com/p/5584115736");//海贼王
//		List<String> analysisFloor = c.analysisFloorOne("http://tieba.baidu.com/p/5280160421");//渡边麻友
//		List<String> analysisFloor = c.analysisFloorOne("http://tieba.baidu.com/p/5581288677");//古天乐
		List<String> analysisFloor = c.analysisFloorOne("http://tieba.baidu.com/p/4082155050");//日本
		
		
//		c.analysisConversation("足球1");
	}
}
