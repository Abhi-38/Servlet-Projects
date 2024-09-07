package com.test.dash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Servlet implementation class TrxData
 */
@WebServlet("/TrxData")
public class TrxData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Create a JSON object for the response
        ObjectMapper responseMapper = new ObjectMapper();
        ObjectNode responseNode = responseMapper.createObjectNode();

        // Perform database operations
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection conn = null; // Declare connection variable outside try block
        try {
            conn = db.getConnection();

            // Execute SQL queries
            try (PreparedStatement maxStmt = conn.prepareStatement("SELECT MAX(TotalAmount) FROM icici.bills");
                 PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) FROM icici.bills");
                 PreparedStatement avgStmt = conn.prepareStatement("SELECT AVG(TotalAmount) FROM icici.bills")) {

                ResultSet maxResult = maxStmt.executeQuery();
                ResultSet countResult = countStmt.executeQuery();
                ResultSet avgResult = avgStmt.executeQuery();

                // Process query results
                double maxAmount = 0;
                int totalCount = 0;
                double avgAmount = 0;

                if (maxResult.next()) {
                    maxAmount = maxResult.getDouble(1);
                }

                if (countResult.next()) {
                    totalCount = countResult.getInt(1);
                }

                if (avgResult.next()) {
                    avgAmount = avgResult.getDouble(1);
                }

                // Construct JSON response
                responseNode.put("highestTransactionAmount", maxAmount);
                responseNode.put("avgTransactionAmount", avgAmount);
                responseNode.put("totalTransactions", totalCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Set error response
            responseNode.put("error", "Error occurred while executing SQL queries.");
        } finally {
            // Close the connection in the finally block
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // Set response content type to JSON
        response.setContentType("application/json");
        // Write the JSON response to the output stream
        response.getWriter().write(responseMapper.writeValueAsString(responseNode));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
