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

@WebServlet("/TotalAmount")
public class TotalAmountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// Database connection
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection con = db.getConnection();

        // Initialize JSON array to hold yearly data
        JSONArray yearlyData = new JSONArray();

        // Get the year parameter from the request
        String yearParam = request.getParameter("year");

        if (yearParam != null && !yearParam.isEmpty()) {
            try {
                int year = Integer.parseInt(yearParam);

                // SQL query to retrieve yearly total amounts
                String query = "SELECT \r\n"
                        + "    YEAR(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) AS Year,\r\n"
                        + "    SUM(TotalAmount) AS Total_Amount\r\n"
                        + "FROM \r\n"
                        + "    icici.bills\r\n"
                        + "WHERE \r\n"
                        + "    YEAR(STR_TO_DATE(TransactionDate, '%d-%m-%Y')) = ?\r\n"
                        + "GROUP BY \r\n"
                        + "    YEAR(STR_TO_DATE(TransactionDate, '%d-%m-%Y'))";

                try {
                    // Prepare the statement
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, year); // Set the year parameter

                    // Execute the query
                    ResultSet rs = pstmt.executeQuery();

                    // Iterate through the result set to populate yearly data
                    while (rs.next()) {
                        JSONObject yearlyEntry = new JSONObject();
                        yearlyEntry.put("Year", rs.getInt("Year"));
                        yearlyEntry.put("TotalAmount", rs.getLong("Total_Amount"));
                        yearlyData.put(yearlyEntry);
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
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle invalid input for the year parameter
                // Respond with an appropriate error message
            }
        } else {
            // Handle case when "year" parameter is not provided
            // Respond with an appropriate error message
        }

        // Set the response type to JSON
        response.setContentType("application/json");

        // Get the response writer
        PrintWriter out = response.getWriter();

        // Write the JSON response
        out.print(yearlyData);

        // Flush and close the writer
        out.flush();
        out.close();
    }
}
