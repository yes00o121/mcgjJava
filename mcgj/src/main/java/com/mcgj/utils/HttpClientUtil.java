package com.mcgj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.mcgj.web.websocket.Message;


public class HttpClientUtil {
	
	private static Logger log = Logger.getLogger(HttpClientUtil.class);
    
    /***
     * ʹ��get�����ȡ����
     * @param url �����������ַ
     * @param param �������
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error(e);
//            LoggerUtil.error(HttpClientUtil.class, e.getMessage(), e);
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	log.error(e2);
//                LoggerUtil.error(HttpClientUtil.class, e2.getMessage(), e2);
            }
        }
        return result;
    }
    
    /***
     * ʹ��post�����ȡ����
     * @param url �����������ַ
     * @param param �������
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error(e);
//            LoggerUtil.error(HttpClientUtil.class, e.getMessage(), e);
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
            	log.error(ex);
//                LoggerUtil.error(HttpClientUtil.class, ex.getMessage(), ex);
            }
        }
        return result;
    }
    
    /**
     * ��ȡԶ���ļ���
     * @param url
     * @return
     */
    public static InputStream getFileInputStream(String url){
    	try {
    		url = url.startsWith("//") ? url.replaceAll("//", "http://") : url;
    		url = url.startsWith("http://") ? url : url.replaceAll(url, "http://" + url);
    		URL realUrl = new URL(url);
    		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
    		System.out.println(conn.getResponseCode());
        	if(conn.getResponseCode() == 200){
        		return conn.getInputStream();
        	}else{
        		throw new RuntimeException(MessageUtil.MSG_GET_FILEINPUTSTREAM_ERROR);
        	}
		} catch (Exception e) {
			log.error(e);
		}finally {
			
		}
    	return null;
    }
    
    /**
     * ��ȡ��վ��host
     * @param url
     * @return
     */
    public static String getHost(String url){
    	try {
			URL ul = new URL(url);
			return ul.getHost();
		} catch (Exception e) {
			log.error(MessageUtil.MSG_UNKONW_ERROR);
		}
    	return null;
    }
    
    public static void main(String[] args) {
		String url = "http://tb1.bdstatic.com/tb/cms/frs/bg/default_head20141014.jpg";
		try {
			URL ul = new URL(url);
			System.out.println(ul.getHost());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println();
//		getFileInputStream(url);
	}
}
