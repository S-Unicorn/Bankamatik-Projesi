package com.ecodation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.java.Log;

@Log

public class DatabaseConnectionDb {

	private String url="jdbc:mysql://localhost:3306/bankamatikschema?useUnicode=yes&characterEncoding=UTF-8";
	private String user="root";
	private String password="12qwert34";
	private Connection connection;
	
	
	public Connection getConnection() {
		
		try {
			if (this.connection==null || this.connection.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver yuklendi");
				log.info("Driver yuklendi"); 
				connection=DriverManager.getConnection(url, user, password);
			    log.info("Baglanti kuruldu");
			} else {
				return connection;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			log.warning("Hata! Baglanmada sorun yasandi." + DatabaseConnectionDb.class);
	        log.warning("date" +NowDateUtil.DateUtil());
		}
		
		return connection;
	}
}
