package com.test.dash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
    private String dbUrl = "jdbc:mysql:///icici"; // URL for db
    private String dbUname = "root";              // Username
    private String dbPassword = "root";           // Password
    private String dbDriver = "com.mysql.cj.jdbc.Driver"; // Driver name to load the driver

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public void loadDriver(String dbDriver) // Method to load the driver
    {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    public Connection getConnection() // Connection to the db
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return con;
    }

    public void closeConnection(Connection con) {  //closing resources
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
