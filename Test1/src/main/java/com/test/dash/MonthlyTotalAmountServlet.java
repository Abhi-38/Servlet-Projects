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

@WebServlet("/MonthlyTotalAmount")
public class MonthlyTotalAmountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the selected year from the request
        String selectedYear = request.getParameter("selectedYear");

        // Initialize variables to store monthly total amounts
        JSONArray monthlyTotalAmounts = new JSONArray();

        // Database connection
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection con = db.getConnection();

        // SQL query to retrieve monthly total amounts for the selected year
        String query = "SELECT MONTH(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) AS Month, "
                + "SUM(TotalAmount) AS TotalAmount FROM icici.bills WHERE YEAR(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) = ? "
                + "GROUP BY MONTH(STR_TO_DATE(TransactionDate, '%d-%m-%Y'));";

        try {
            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, selectedYear);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Retrieve monthly total amounts from the result set
            while (rs.next()) {
                JSONObject monthlyAmount = new JSONObject();
                monthlyAmount.put("Month", rs.getInt("Month"));
                monthlyAmount.put("TotalAmount", rs.getLong("TotalAmount"));
                monthlyTotalAmounts.put(monthlyAmount);
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
        out.print(monthlyTotalAmounts);
        out.flush();
    }
}
