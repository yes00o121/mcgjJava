package com.mcgj.web.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

/**
 * webSocket������
 * @author ad
 *
 */
//@ServerEndpoint("/websocket")
@ServerEndpoint(value = "/websocket", encoders = { ServerEncoder.class })  
public class WebSocketServer {
	
	private static Logger  log = Logger.getLogger(WebSocketServer.class);

	private static int onlineCount = 0;// ������
	
	// concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
//	public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
	
	//keyΪ�û���token,����token�������û�����ͨѶ
	public static Map<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<String,WebSocketServer>();
	
	// ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private Session session;
    
    /**
     * ���ӳɹ�����
     */
    @OnOpen
    public void onOpen(Session session) {
//        System.out.println("��������.......................��Ծ�û�����:" + onlineCount);
        System.out.println(session.getQueryString());
        String token =session.getQueryString().split("=")[1];
    	this.session = session;
    	webSocketMap.put(token,this);
//    	sendMessage(token, new Message("��Ϣ���Ͳ��ԡ���������������������������������������",1));
        addOnlineCount();// ׷����������
    }
    
    /**
     * �����û����͵���Ϣ
     * 
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("�յ��ͻ��˵���Ϣ����������������������������" + message);

    }
    
    /**
     * �û�����ʱ����
     */
    @OnClose
    public void onClose(Session session) {
        WebSocketServer.webSocketMap.remove(session.getQueryString().split("=")[1]);// ɾ��set�е�socket
        removeOnlineCount();// ������������
//        System.out.println("�Ͽ����ӡ�������������������������������");
    }
    
//    @OnError
//    public void onError(){
//    	System.out.println("���ӳ��ִ��󡣡�����������������������������");
//    }
    
    /**
     * ����������������
     */
    private void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * ����������������
     */
    private void removeOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    
    private static WebSocketServer getWebSocketConnection(String token){
    	return webSocketMap.get(token);
    }
    
    /**
     * ������Ϣ��ָ���û�
     */
    public static void sendMessage(String token,Message message){
    	try {
    		WebSocketServer wss = getWebSocketConnection(token);
    		if(wss != null){
    			wss.session.getBasicRemote().sendObject(message);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
    	
    }
    
}
