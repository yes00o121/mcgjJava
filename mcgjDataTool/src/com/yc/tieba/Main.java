package com.yc.tieba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

public class Main extends JFrame{
	
	private static final int WIDTH = 600;
	
	private static final int HEIGHT = 500;
	
	public static JTextField url_text,path_text,bottom_text; //�����ı�
	
	public static JComboBox<String> comboBox;
	
	public static JTextArea center_text;//�ı���
	
	public static JButton top_select_btn,top_wodnload_btn,bottom_input_btn;//���尴ť
	
	public static String filePath = "";//���ص��ļ���·��
	
	public static String downloadUrl = "";//��ȡ����ҳ��ַ
	
	public Main() {
		super("GUI");
		this.init();
	}
	/**
	 * ��ʼ�����ڷ���
	 */
	private void init(){
		
		this.setTitle("���湤��");
		this.addWindowListener(new WindowListener() {//���ڼ���
			
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
			public void windowClosing(WindowEvent e) {//�رմ��ڼ���
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {//���ڻ�Ծ����
				
			}
		});
		this.setLayout(new BorderLayout());
		this.setBounds(600,400,WIDTH,HEIGHT);//
		this.LayoutLoad();
		this.setLocationRelativeTo(null);
		this.show();
	}
	/**
	 * ���ز���
	 */
	private void LayoutLoad(){
		this.topLayoutLoad();
		this.conterLayoutLoad();
		this.setVisible(true);
		
	}
	private void topLayoutLoad(){
		JPanel jp =new JPanel();
//		jp.setLayout(new GridLayout(1,6));  
		jp.setBorder(new TitledBorder("������Ϣ"));
		jp.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
		comboBox = new JComboBox<String>();
		comboBox.addItem("ͼƬ");
		comboBox.addItem("��Ƶ");
		JLabel url_label = new JLabel("������:");
		url_label.setHorizontalAlignment(url_label.RIGHT);
		url_text = new JTextField(10);
		url_text.setBounds(8,10,100,100);
		JLabel port_label = new JLabel("·��:");
		port_label.setHorizontalAlignment(port_label.RIGHT);
		path_text = new JTextField(10);
		path_text.setEditable(false);//���ɱ༭
		top_select_btn = new JButton("ѡ���ļ���");
		top_wodnload_btn = new JButton("��ʼ����");
//		jp.add(comboBox);
		jp.add(url_label);
		jp.add(url_text);
		//jp.add(port_label);
//		jp.add(path_text);
		//jp.add(top_select_btn);
		jp.add(top_wodnload_btn);
		top_select_btn.addActionListener(new ActionListener() {//ѡ���ļ��а�ť����
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io .File("."));
			    chooser.setDialogTitle("");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
			    	//��ȡ�û�ѡ����ļ���·��
			    	path_text.setText(chooser.getSelectedFile().toString()+"\\");
			    }
			}
		});
		top_wodnload_btn.addActionListener(new ActionListener() {//���ؼ���
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//�жϲ����Ƿ�Ϊ��
				final String url = url_text.getText();
//				final String path = path_text.getText();
//				String path = "E:\\spider\\test\\";
				/*
				if("".equals(url) || "".equals(path)){
					JOptionPane.showMessageDialog(null,"��ַ���ļ��в���Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
					return;
				}
				*/
				if("".equals(url)){
					JOptionPane.showMessageDialog(null,"����������Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//����ϴ����ص���־
				String str = (String)comboBox.getSelectedItem();//��ȡ�û�ѡ�����������
				Main.BtnQuiet();//���ð�ť
				//���¿���һҳ�߳�ִ�У���Ȼ�ᵼ�´��ڼ���
				new Thread(){
					public void run(){
						new Conversation(url);
					}
				}.start();
				if(str.equals("��Ƶ")){
					System.out.println("�û�ѡ������Ƶ");
				}
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
	//����btn��ť
	public  static void BtnQuiet(){
		//�������غ�ѡ���ļ��а�ť
		top_select_btn.setEnabled(false);
		top_wodnload_btn.setEnabled(false);
	}
	//����btn��ť
	public static void BtnActive(){
		//�������غ�ѡ���ļ��а�ť
		top_select_btn.setEnabled(true);
		top_wodnload_btn.setEnabled(true);
	}
}