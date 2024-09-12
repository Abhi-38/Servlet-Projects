package com.abhi.prj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
	private String dbUrl = "jdbc:mysql:///abhi"; //url for db
	private String dbUname = "root";             //username 
	private String dbPassword = "root";			//password
	private String dbDriver = "com.mysql.cj.jdbc.Driver"; //driver name to load the driver
	public static final String QUERY = "INSERT INTO MEMBER VALUES(?,?,?,?)";
	public void loadDriver(String dbDriver) //method to load the driver
	{
		try {
			Class.forName(dbDriver);
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	public Connection getConnection()  //connection to the db
	{
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl,dbUname,dbPassword);
		}catch(SQLException se) {
			se.printStackTrace();
		}
		return con;
	}
	
	public String insert(Member mem) //insert method to insert the data in the table
	//we have passed member class obj here in order to get the form data 
	{
		//first we will load the driver
		loadDriver(dbDriver);
		//then we get connection
		Connection con = getConnection();
		//to check whether data is entered or not
		String result = "Data entered Sucessfully";
		//now we go for query
		try
		{
			PreparedStatement ps = con.prepareStatement(QUERY);
			//here we are setting pre-compiled query params
			ps.setString(1, mem.getUname());
			ps.setString(2,mem.getPassword());
			ps.setString(3, mem.getEmail());
			ps.setString(4, mem.getPhone());
			//now we are executing the query
			ps.executeUpdate();
		}catch(SQLException se) {
			se.printStackTrace();
			//in case data insertion fails print this
			result = "Data not entered !!!";
		}
		return result;
		
	}
}
