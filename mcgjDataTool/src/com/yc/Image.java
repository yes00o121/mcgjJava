package com.yc;

import it.sauronsoftware.base64.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 下载图片
 * @author ad
 *
 */
public class Image {
	
	private String url;//下载路径
	
	private String filePath;//保存路径
	
	private String hostpath = "";//根目录
	
	private static final String IMAGE_REGULAR = "<img.*?(.*?>)";//图片正则
	
	private static final String IMAGE_REGULAR_BASE64 = "data:image.*?('|\")";//图片匹配base64的图片
	
	
	public Image(){
		
	}
	public Image(String url,String path){
		this.url = url;
		this.filePath = path;
		this.hostpath = Util.getAbsPath(url);//获取根目录
		this.ImageDownLoad();
	}
	/**
	 * 图片下载方法
	 */
	private void ImageDownLoad(){
		String html = Util.getHTML(this.url);//解析url,获取html页面
		this.analyseHtmlStreamToImageElement(html);//分析获取所有的图片标签
	}
	
	/**
	 * 得到页面中所有img标签
	 */
	private void analyseHtmlStreamToImageElement(String html){
		try{
			if(html == null)
				return;
			List<String> imgs = new ArrayList<String>();
			Pattern pattern = Pattern.compile(IMAGE_REGULAR);
			Pattern patternBase64 = Pattern.compile(IMAGE_REGULAR_BASE64);
			Matcher matcher = pattern.matcher(html);
			Matcher matcherBase64 = patternBase64.matcher(html);//查看网页中是否有base64的图片
			while(matcher.find()){
				imgs.add(matcher.group());
			}
			while(matcherBase64.find()){
				imgs.add(matcherBase64.group().replace("'",""));//追加并且清除最后的一个点
			}
			getImgElementUrl(imgs);//解析img标签
//			return imgs;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//		return null;
	}
	/**
	 * 分析所有的img标签，得到最后的图片地址
	 * @return
	 */
	private  List<String> getImgElementUrl(List<String> imgs){
		System.out.println(imgs);
		if(imgs == null || imgs.size() == 0)
			return null;
		try{
			for(String img:imgs){
				//判断是否为base64的图片，如果是直接下载图片，不必进行后续分析
				if(img.startsWith("data:image/jpeg;base64,")){
					img = img.replace("data:image/jpeg;base64,","");//清空开头
					//可以进行下载
					InputStream is =new ByteArrayInputStream(new Base64().decode(img.getBytes()));//将base64转换为输入流
					Util.writeData(is,new FileOutputStream(this.filePath+new Date().getTime()+".jpg"),img);
					continue;
				}
				//判断图片标签是否包含src，如果没有直接舍弃
				if(!img.contains("src"))
					continue;
				if(img.split("src=\"").length<2)
					continue;//截图的图片路径不符合标准，进行过滤
				img = img.split("src=\"")[1];//获取url
				int stratChar = img.indexOf("\"");//得到开始的"号
				img = img.substring(0,stratChar);
				//判断图片地址是否为已知的类型
				if(img.startsWith("http")){//http的图片直接进行下载
					Util.writeData(getImgStream(img),new FileOutputStream(this.filePath+Util.subImageName(img)),img);
					continue;
				}
				if(img.startsWith("//")){// //开头，前面补充http:,一般为静态图片
					img = "http:"+img;
					Util.writeData(getImgStream(img),new FileOutputStream(this.filePath+Util.subImageName(img)),img);
					continue;
				}
				if(img.startsWith("/")){// /开头可能是相对路径，前面添加页面的根目录
					img = hostpath+img;
					Util.writeData(getImgStream(img),new FileOutputStream(this.filePath+Util.subImageName(img)),img);
				}
			}
			//所有图片下载完毕
			Main.center_text.append("下载结束,共检测到"+imgs.size()+"张图片");
			Main.BtnActive();
		}catch(Exception e){
			e.printStackTrace();
			Main.center_text.append("下载失败"+e.getMessage());
			//下载失败后还原下载和选择文件夹按钮
			Main.BtnActive();
		}
		//截取标签的src的地址
//		.replace("data:image/jpeg;base64,","")
		return null;
	}
	/**
	 * 获取图片流
	 * @return
	 */
	public InputStream getImgStream(String img){
		try {
			URL url = new URL(img);
			HttpURLConnection huc =(HttpURLConnection)url.openConnection();
			if(huc.getResponseCode() == 200){
				return huc.getInputStream();
			}
			throw new RuntimeException("获取图片异常");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
