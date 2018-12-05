package com.yc.spider.tieba.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionUtil {
	
	public static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/mcgj_db";
	    String username = "root";
	    String password = "root123";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	        conn.setAutoCommit(false);//不自动提交
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public static void insertData(Connection conn,PreparedStatement ps){
		if(conn != null){
			try{
//				PreparedStatement prepareStatement = conn.prepareStatement(SQL);
//				ResultSet executeQuery = prepareStatement.executeQuery();//执行sql
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			throw new RuntimeException("获取连接异常");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getConn());
	}
}
