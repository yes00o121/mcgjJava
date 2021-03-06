package com.yc;

import it.sauronsoftware.base64.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.yc.tieba.Main;


public class Util {
	
//	private static final String IMAGE_REGULAR = "<img.*?(.*?>)";
//	private static final String IMAGE_REGULAR = "<img.*?(.*?>)";//图片正则
	
//	private static final String IMAGE_REGULAR_BASE64 = "data:image.*?('|\")";//图片匹配base64的图片
	
//	private static String hostpath = "";//根目录
	
//	private static String fileName = "E://spider//test//";
	
	
	/**
	 * get absPath获取网页根路径
	 * @param url
	 * @return
	 */
	public static String getAbsPath(String url){	
		try {
			URI uri = new URI(url);
			URI u = uri.resolve(url);
//			return "http://"+u.getHost()+"/";
			return "http://"+u.getHost()+"/";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	
		return null;
	}
	/**
	 * get fileOutputStream得到文件对象
	 * @return
	 */
	public static FileOutputStream getFile(String fileNamePath){
		FileOutputStream fos = null;
		try{
			if(fileNamePath == null || "".equals(fileNamePath))
				return null;
			fos = new FileOutputStream(fileNamePath);
			return fos;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(fos != null){
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	/**
	 * get html stream..得到html页面
	 */
	public static String getHTML(String url){
//		getAbsPath(url);
//		BufferedReader br = null;
		Main.center_text.append("开始下载:"+url+"\n");
		Main.center_text.setSelectionStart(Main.center_text.getText().length());
		InputStream is= null;
		ByteArrayOutputStream baos = null;
		String sb = "";
		if(url == null || "".equals(url))
			return null;
		try{
			System.out.println(url);
			System.out.println("开始请求？？？？");
			URL u = new URL(url);
			URLConnection openConnection = u.openConnection();//create connection..
			HttpURLConnection huc = (HttpURLConnection)openConnection;
			huc.setReadTimeout(5000);
//			huc.setConnectTimeout(200);
			int code = huc.getResponseCode();//return state
			System.out.println(code);
			if(code == 301){
				System.out.println("请求重定向 。。。。。");
				String location = huc.getHeaderField("Location");
				System.out.println(location);
				System.out.println("重定向后重新调用。。。");
				String html = getHTML(location);//递归
//				System.out.println("结束");
//				System.out.println(html);
				return html;
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				InputStream inputStream = huc.getInputStream();
//				byte[] buff = new byte[4086];
//				int bt =0;
//				while(true){
//					int read = bt= inputStream.read(buff);
//					if(read == -1){
//						break;
//					}
//					baos.write(buff,0,read);
//				}
//				System.out.println(new String(baos.toByteArray(),"utf-8"));
			}
			if(code != 200){
				throw new RuntimeException("getHtml Error...");
			}
//			System.out.println("请求终了。。");
//			
			baos = new ByteArrayOutputStream();
			is = huc.getInputStream();
			byte[] buff = new byte[4086];
			int bt =0;
			while(true){
				int read = bt= is.read(buff);
				if(read == -1){
					break;
				}
				baos.write(buff,0,read);
			}
			return new String(baos.toByteArray(),"utf-8").toString();
//			return new String(baos.toByteArray(),"utf-8").toString();
//			br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"utf-8"));//get inputstream
//			StringBuffer sb = new StringBuffer();//html element
			
//			String line= "";
//			while((line = br.readLine()) != null){
////				System.out.println(line);
////			line = br.readLine();
////				System.out.println(line== null);
////				sb.append(line.getBytes());//data write and code tranform
//				sb+=line;
//			}
//			System.out.println(sb);
//			System.out.println("请求终了。。");
////			return new String(new String(sb).getBytes(),"utf-8");
////			return new String(new String(sb).getBytes("utf-8"));
//			System.out.println(sb);
			
//			return new String(sb.toString());
//			return sb;
//			return "ssss";
		}catch(Exception e){
			e.printStackTrace();
//			连接失败，重新连接
			getHTML(url);
//			JOptionPane.showMessageDialog(null,"地址无法解析","提示",JOptionPane.ERROR_MESSAGE);
//			Main.BtnActive();//活跃按钮
//			return null;
		}finally{
			try {
				if(is != null)
				is.close();
				if(baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println(sb);
		return sb;
	}
	/**
	 * get html stream..得到html页面
	 */
	public static String getHTML2(String url){
		getAbsPath(url);
		BufferedReader br = null;
		if(url == null || "".equals(url))
			return null;
		try{
			URL u = new URL(url);
			URLConnection openConnection = u.openConnection();//create connection..
			HttpURLConnection huc = (HttpURLConnection)openConnection;
			int code = huc.getResponseCode();//return state
			if(code != 200){
				throw new RuntimeException("getHtml Error...");
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream inputStream = huc.getInputStream();
			byte[] buff = new byte[1024];
			int bt =0;
			while((bt= inputStream.read(buff)) != -1){
				System.out.println(bt);
				baos.write(buff);
			}
			return new String(baos.toByteArray(),"utf-8").toString();
			
		}catch(MalformedURLException mue){
			System.out.println("An unsupported agreement..");
			mue.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"地址无法解析","提示",JOptionPane.ERROR_MESSAGE);
			Main.BtnActive();//活跃按钮
			return null;
		}finally{
			try{
				if(br != null){
					br.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 分析所有的img标签，得到最后的图片地址
	 * @return
	 */
//	public static  List<String> getImgElementUrl(List<String> imgs){
//		if(imgs == null || imgs.size() == 0)
//			return null;
//		try{
//			for(String img:imgs){
//				//判断是否为base64的图片，如果是直接下载图片，不必进行后续分析
//				if(img.startsWith("data:image/jpeg;base64,")){
//					img = img.replace("data:image/jpeg;base64,","");//清空开头
//					//可以进行下载
//					InputStream is =new ByteArrayInputStream(new Base64().decode(img.getBytes()));//将base64转换为输入流
//					writeData(is,new FileOutputStream(fileName+new Date().getTime()+".jpg"));
//					continue;
//				}
//				//判断图片标签是否包含src，如果没有直接舍弃
//				if(!img.contains("src"))
//					continue;
//				img = img.split("src=\"")[1];//获取url
//				int stratChar = img.indexOf("\"");//得到开始的"号
//				img = img.substring(0,stratChar);
//				//判断图片地址是否为已知的类型
//				if(img.startsWith("http")){//http的图片直接进行下载
//					writeData(getImgStream(img),new FileOutputStream(fileName+subImageName(img)));
//					continue;
//				}
//				if(img.startsWith("//")){// //开头，前面补充http:,一般为静态图片
//					img = "http:"+img;
//					writeData(getImgStream(img),new FileOutputStream(fileName+subImageName(img)));
//					continue;
//				}
//				if(img.startsWith("/")){// /开头可能是相对路径，前面添加页面的根目录
//					img = hostpath+img;
//					writeData(getImgStream(img),new FileOutputStream(fileName+subImageName(img)));
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		//截取标签的src的地址
////		.replace("data:image/jpeg;base64,","")
//		return null;
//	}
	/**
	 * 获取图片流
	 * @return
	 */
//	public static InputStream getImgStream(String img){
//		try {
//			URL url = new URL(img);
//			HttpURLConnection huc =(HttpURLConnection)url.openConnection();
//			if(huc.getResponseCode() == 200){
//				return huc.getInputStream();
//			}
//			throw new RuntimeException("获取图片异常");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	/**
	 * 截图图片的名称
	 * @return
	 */
	public static String subImageName(String img){
		//判断图片名称是否合法，不合法随机生成一个新名称返回
		if(img.contains("?") || img.contains("&") || !img.contains("[.]")){
			//名称不合法，随机生成一个名称返回
			return new Date().getTime()+".gif";//默认为gif格式
		}
		return  img.substring(img.lastIndexOf("/")+1,img.length());
	}
	
//	/**
//	 * 得到页面中所有img标签
//	 */
//	public static void analyseHtmlStreamToImageElement(String html){
//		try{
//			if(html == null)
//				return;
//			List<String> imgs = new ArrayList<String>();
//			Pattern pattern = Pattern.compile(IMAGE_REGULAR);
//			Pattern patternBase64 = Pattern.compile(IMAGE_REGULAR_BASE64);
//			Matcher matcher = pattern.matcher(html);
//			Matcher matcherBase64 = patternBase64.matcher(html);//查看网页中是否有base64的图片
//			while(matcher.find()){
//				imgs.add(matcher.group());
//			}
//			while(matcherBase64.find()){
//				imgs.add(matcherBase64.group().replace("'",""));//追加并且清除最后的一个点
//			}
//			getImgElementUrl(imgs);//解析img标签
////			return imgs;
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try{
//				
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
////		return null;
//	}
	/**
	 * write data
	 * 将流写入指定的文件内
	 * @param is
	 * @param fos
	 * @return
	 */
	public static void writeData(InputStream fileStream,FileOutputStream fos,String img){
		try{
			if(fileStream == null){
				throw new RuntimeException("下载异常");
			}
			byte[] bts = new byte[1024];
			int len = 0;
//			System.out.println("开始下载.."+fos.);
//			System.out.println("开始下载。。");
//			Main.center_text.setText("开始下载...");
			Main.center_text.append("开始下载"+img+"\n");
			while((len = fileStream.read(bts, 0, bts.length)) != -1){
				fos.write(bts, 0, len);
			}
			Main.center_text.append("下载完成...\n");
			Main.center_text.setSelectionStart(Main.center_text.getText().length());
//			Main.center_text.setText("开始下载...");
//			System.out.println("下载结束....");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(fileStream != null)
					fileStream.close();
				if(fos != null )
					fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//		return null;
	}
	public static void main(String[] args) throws IOException {
//		analyseHtmlStreamToImage(getHTML("http://tu.duowan.com/tu"));
//		analyseHtmlStreamToImageElement(getHTML("http://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=%E5%9B%BE%E7%89%87"));
//		analyseHtmlStreamToImageElement(getHTML("https://www.pengfu.com/qutu_1.html"));
//		String base64 = "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KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKaGJYjaQABz2P+f60AOooooAKKKKACiiigAooooAKKKKACiiigCKe3juAgkBIRw4AYjkHI6dee1I0cTzrM8KmWPKo5UFgGxnB6gHA/KpqYsMazPKEUSOArNjkgZwPwyfzNACCFQrj5sOST8x/T0/CmGIxuhjIRN2XG3JfjAH8vyxU9N+YOSSCpAAGOh5yc/lQA6o5o2kCANtAYMeuSAc44IqSgjNABRTY9wQbyC2OcDAp1ABVeG2eO8uJ2uJHWUIFiP3YwAenuScn8PSpiilw5UbgCAccgHr/ACH5U6gBCAwwRmloooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigCpfyPHHCUYrunjU49CwzVuiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD/2Q==";
//		Base64 b = new Base64();
//		FileOutputStream fos = new FileOutputStream("E://spider//test//sss.jpg");
//		byte[] bt = b.decode(base64.getBytes());//base64字符串转换为base64字节数组
//		InputStream is = new ByteArrayInputStream(bt);
//		int len = 0;
//		while((len = is.read(bt,0,bt.length))!= -1){
//			fos.write(bt);
//		}
////		fos.write(bt);
//		fos.close();
	}
}
