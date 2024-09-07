package com.test.dash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@WebServlet("/Years")
public class Years extends HttpServlet {
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

            // Execute SQL query to get distinct years
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT DISTINCT(YEAR(STR_TO_DATE(TransactionDate, '%d-%m-%Y'))) AS YEAR FROM ICICI.BILLS")) {
                ResultSet resultSet = stmt.executeQuery();

                // Process query results
                List<Integer> years = new ArrayList<>();
                while (resultSet.next()) {
                    years.add(resultSet.getInt("YEAR"));
                }

                // Convert years to JSON array
                ArrayNode yearsArray = responseMapper.createArrayNode();
                for (Integer year : years) {
                    yearsArray.add(year);
                }

                // Add years to response JSON
                responseNode.set("years", yearsArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Set error response
            responseNode.put("error", "Error occurred while retrieving years.");
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
