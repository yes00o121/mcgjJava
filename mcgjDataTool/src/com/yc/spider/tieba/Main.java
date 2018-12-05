package com.yc.spider.tieba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yc.spider.tieba.util.HttpClientUtil;

public class Main extends JFrame{
	
	private String serverIp = "http://127.0.0.1:8090/mcgj";
	
	private static final int WIDTH = 600;
	
	private static final int HEIGHT = 500;
	
	public static JTextField url_text,path_text,bottom_text; //定义文本
	
	public static JComboBox<String> comboBox;
	
	public static JTextArea center_text;//文本域
	
	public static JButton top_select_btn,top_wodnload_btn,bottom_input_btn;//定义按钮
	
	public static String filePath = "";//下载的文件夹路径
	
	public static String downloadUrl = "";//爬取的网页地址
	
	public Main() {
		super("GUI");
		this.init();
//		getComboBox();
	}
	/**
	 * 初始化窗口方法
	 */
	private void init(){
		
		this.setTitle("爬虫工具");
		this.addWindowListener(new WindowListener() {//窗口监听
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {//关闭窗口监听
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {//窗口活跃监听
				
			}
		});
		this.setLayout(new BorderLayout());
		this.setBounds(600,400,WIDTH,HEIGHT);//
		this.LayoutLoad();
		this.setLocationRelativeTo(null);
		this.show();
	}
	/**
	 * 加载布局
	 */
	private void LayoutLoad(){
		this.topLayoutLoad();
		this.conterLayoutLoad();
		this.setVisible(true);
		
	}
	//获取贴吧类型下啦
	private JComboBox getComboBox(){
		JComboBox<Map<String,Object>> box = new JComboBox<Map<String,Object>>();
//		JComboBox = box = new JComboBox<String>();
		String json = HttpClientUtil.sendGet(this.serverIp+"/dict/selectDictByCode", "codeValue=conversation_type");
		JSONObject parseObject = JSONObject.parseObject(json);
		String result = parseObject.getString("result");//结果集
//		List<List> parseArray = JSONObject.parseArray(result, List.class);
		JSONArray parseArray = JSONObject.parseArray(result);
		for(Object obj:parseArray){
//			System.out.println(map);
			Map<String,Object> m = new HashMap<>();
			
			m.put("key", JSONObject.parseObject(obj.toString()).get("dictName"));
			m.put("value", JSONObject.parseObject(obj.toString()).get("dictType"));
			box.addItem(m);
		}
		return box;
//		System.out.println(parseArray.size());
//		Object[] array = parseArray.toArray();
//		System.out.println(parseArray);
//		Jsonob
//		JSONObject resultObj = JSONObject.parseObject(result);
//		HashMap javaObject = resultObj.toJavaObject(resultObj, HashMap.class);
//		System.out.println(resultObj);
//		Map<String,List<Object>> map = 
	}
	
	private void topLayoutLoad(){
		JPanel jp =new JPanel();
//		jp.setLayout(new GridLayout(1,6));  
		jp.setBorder(new TitledBorder("配置信息"));
		jp.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
		comboBox = new JComboBox<String>();
		comboBox.addItem("图片");
		comboBox.addItem("视频");
		JLabel url_label = new JLabel("贴吧名:");
		url_label.setHorizontalAlignment(url_label.RIGHT);
		JComboBox comboBox=new JComboBox();
		
		url_text = new JTextField(10);
		url_text.setBounds(8,10,100,100);
		JLabel port_label = new JLabel("路径:");
		port_label.setHorizontalAlignment(port_label.RIGHT);
		path_text = new JTextField(10);
		path_text.setEditable(false);//不可编辑
		top_select_btn = new JButton("选择文件夹");
		top_wodnload_btn = new JButton("开始下载");
		jp.add(getComboBox());
		jp.add(url_label);
		jp.add(url_text);
		jp.add(top_wodnload_btn);
		top_select_btn.addActionListener(new ActionListener() {//选择文件夹按钮监听
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io .File("."));
			    chooser.setDialogTitle("");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
			    	//获取用户选择的文件夹路径
			    	path_text.setText(chooser.getSelectedFile().toString()+"\\");
			    }
			}
		});
		top_wodnload_btn.addActionListener(new ActionListener() {//下载监听
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//判断参数是否不为空
				final String url = url_text.getText();
				if("".equals(url)){
					JOptionPane.showMessageDialog(null,"贴吧名不能为空","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//清空上次下载的日志
				String str = (String)comboBox.getSelectedItem();//获取用户选择的下载类型
				Main.BtnQuiet();//禁用按钮
				//重新开启一页线程执行，不然会导致窗口假死
//				new Thread(){
//					public void run(){
//						new Conversation(url);
//					}
//				}.start();
			}
		});
		this.add(jp,"North");
	}
	public void	conterLayoutLoad(){
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1,2));
		center_text = new JTextArea();
		center_text.setEditable(false);
		center_text.setBackground(new Color(255,255,255));
		JTextField a = new JTextField();
		a.setBackground(new Color(255,255,255));
		JScrollPane jp2 = new JScrollPane(center_text);
		jp2.setViewportView(center_text);
		jp.add(jp2,"West");
		this.add(jp,"Center");
	}
	public static void main(String[] args) {
		new Main();
	}
	//禁用btn按钮
	public  static void BtnQuiet(){
		//禁用下载和选择文件夹按钮
		top_select_btn.setEnabled(false);
		top_wodnload_btn.setEnabled(false);
	}
	//启用btn按钮
	public static void BtnActive(){
		//启用下载和选择文件夹按钮
		top_select_btn.setEnabled(true);
		top_wodnload_btn.setEnabled(true);
	}
}