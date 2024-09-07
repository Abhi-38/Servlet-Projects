
package com.test.dash;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;


@WebServlet("/DailyTotalAmount")
public class DailyTotalAmountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Retrieve the selected year and month from the request
        int year = Integer.parseInt(request.getParameter("selectedYear"));
        int month = Integer.parseInt(request.getParameter("selectedMonth"));

        // Initialize variables to store daily total amounts
        JSONArray dailyTotalAmounts = new JSONArray();

        // Database connection
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection con = db.getConnection();

        // SQL query to retrieve daily total amounts for the selected year and month
        String query = "SELECT DAY(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) AS Day, "
                + "SUM(TotalAmount) AS TotalAmount FROM icici.bills "
                + "WHERE YEAR(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) = ? "
                + "AND MONTH(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) = ? "
                + "GROUP BY DAY(STR_TO_DATE(TransactionDate, '%d-%m-%Y'));";

        try {
            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Retrieve daily total amounts from the result set
            while (rs.next()) {
                JSONObject dailyAmount = new JSONObject();
                dailyAmount.put("Day", rs.getInt("Day"));
                dailyAmount.put("TotalAmount", rs.getLong("TotalAmount"));
                dailyTotalAmounts.put(dailyAmount);
            }

            // Close the result set and the statement
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            db.closeConnection(con);
        }

        // Write the JSON response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(dailyTotalAmounts);
        out.flush();
	}

}
