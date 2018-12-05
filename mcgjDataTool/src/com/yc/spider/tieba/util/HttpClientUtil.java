package com.yc.spider.tieba.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpClientUtil {
	
    /***
     * 使用get请求获取数据
     * @param url 请求服务器地址
     * @param param 请求参数
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            LoggerUtil.error(HttpClientUtil.class, e.getMessage(), e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
//                LoggerUtil.error(HttpClientUtil.class, e2.getMessage(), e2);
            }
        }
        return result;
    }
    
    /***
     * 使用post请求获取数据
     * @param url 请求服务器地址
     * @param param 请求参数
     */
    public static String sendPost(String url, String param) {
    	DataOutputStream  out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            out = new DataOutputStream(conn.getOutputStream());
            // 发送请求参数
            out.writeBytes(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            LoggerUtil.error(HttpClientUtil.class, e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
//                LoggerUtil.error(HttpClientUtil.class, ex.getMessage(), ex);
            }
        }
        return result;
    }
    
    /**
     * 上传图片方法
     * @return 图片主键
     */
//    public static String upImg(String url){
//    	HttpURLConnection conn = null;
//    	try {
//			URL requesturl = new URL(url);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
////    	String sendPost = HttpClientUtil.sendPost("https://gss3.bdstatic.com/84oSdTum2Q5BphGlnYG/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=562e2995b7aa3f1a930cf0eb447edace&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2F1b4c510fd9f9d72a25c62d9bd62a2834359bbb91.jpg","");
////    	System.out.println(sendPost);
////    	System.out.println("..");
//    	return null;
//    }
//    public static void main(String[] args) {
//		HttpClientUtil.upImg(null);
//	}
}
