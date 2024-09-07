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

@WebServlet("/Counterparties")
public class Counterparties extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Create a JSON object for the response
		
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        ObjectMapper responseMapper = new ObjectMapper();
        ObjectNode responseNode = responseMapper.createObjectNode();
        System.out.println("this is counterparty");
        // Perform database operations
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection conn = null; // Declare connection variable outside try block
        try {
            conn = db.getConnection();

            // Execute SQL query to get distinct counterparties
            try (PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT Counterparty FROM icici.bills")) {
                ResultSet resultSet = stmt.executeQuery();

                // Process query results
                List<String> counterparties = new ArrayList<>();
                while (resultSet.next()) {
                    counterparties.add(resultSet.getString("Counterparty"));
                }

                // Convert counterparties to JSON array
                ArrayNode counterpartiesArray = responseMapper.createArrayNode();
                for (String counterparty : counterparties) {
                    counterpartiesArray.add(counterparty);
                }

                // Add counterparties to response JSON
                responseNode.set("counterparties", counterpartiesArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Set error response
            responseNode.put("error", "Error occurred while retrieving counterparties.");
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
		doGet(request, response);
	}

}
