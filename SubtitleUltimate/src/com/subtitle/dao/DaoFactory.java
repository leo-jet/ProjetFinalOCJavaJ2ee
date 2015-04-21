package com.subtitle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DaoFactory {
	private String url; 
	private String username; 
	private String password;
	
	DaoFactory(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static DaoFactory getInstance(){
		
		//chargement du driver
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
		}
		DaoFactory instance = new DaoFactory("jdbc:mysql://127.0.0.1:3306/javadb", "root", "leoroot");
		
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		Connection connexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javadb", "root", "leoroot");
		connexion.setAutoCommit(false);
		return connexion;
	}
	
	
	public SubtitleDao getSubtitleDao(){
		return new SubtitleDaoImpl(this);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
}
