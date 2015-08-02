package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mydb {
	
	//private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String HOST = "localhost";
	
	private static final String PORT = "3306";
	
	private static final String DB = "test_db";
	
	private static final String DBURL =
	  "jdbc:mysql://"+ HOST +":"+ PORT +"/"+ DB;
	
	private static final String USER = "root";
	
	private static final String PASS = "asdf";
	
	public static Connection connect() throws SQLException {
		return DriverManager.getConnection(DBURL, USER, PASS);
	}

}
