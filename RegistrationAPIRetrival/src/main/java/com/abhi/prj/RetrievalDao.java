package com.abhi.prj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrievalDao {
	private String dbUrl = "jdbc:mysql:///abhi"; //url for db
	private String dbUname = "root";             //username 
	private String dbPassword = "root";			//password
	private String dbDriver = "com.mysql.cj.jdbc.Driver"; //driver name to load the driver
	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public static final String QUERY = "SELECT * FROM MEMBER";
	
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
	
	public List<Member> retrieveData() {
	    loadDriver(dbDriver);
	    Connection con = getConnection();
	    StringBuilder result = new StringBuilder("Retrieved Data:<br>");
	    List<Member> members = new ArrayList<>();
	    try {
	        PreparedStatement ps = con.prepareStatement(QUERY);
	        ResultSet rs = ps.executeQuery();

	        // Process the retrieved data
	        while (rs.next()) {
	        	Member member = new Member();
	        	member.setMemberName(rs.getString(1));
	        	member.setMemberPwd(rs.getString(2));
	        	member.setMemberEmail(rs.getString(3));
	        	member.setMemberPhone(rs.getString(4));
	        	
	        	members.add(member);
	        }
	    } catch (SQLException se) {
	        se.printStackTrace();
	        result.append("Error retrieving data!");
	    }

	    return members;
	}

}
