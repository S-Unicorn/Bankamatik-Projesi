package com.ecodation.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.ecodation.utils.DatabaseConnectionDb;

public interface IDaoImplements<T> {
	    //Crud
		public void create(T t);
		public void update(T t);
		public void delete(long id);
		public ArrayList<T> list();
		
		 static Connection getInterfaceConnection() {
	    	DatabaseConnectionDb connectionDb=new DatabaseConnectionDb();
			return connectionDb.getConnection();
		}
}
