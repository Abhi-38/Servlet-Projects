package com.test.dash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/FilterDeSelect")
public class FilterDeSelect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the JSON data from the request body
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        // Parse the JSON data
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonBody.toString());
        JsonNode filterIdNode = rootNode.get("filterId");

        // Create a JSON object for the response
        ObjectMapper responseMapper = new ObjectMapper();
        JsonNode responseNode;

        // Check if filterIdNode is null or not
        if (filterIdNode != null && filterIdNode.isTextual()) {
            // Extract filter ID as a String
            String filterId = filterIdNode.asText();

            // Log the received filter ID
            System.out.println("Received filterId: " + filterId);

            // Perform database update
            ConnectDb db = new ConnectDb();
            db.loadDriver(db.getDbDriver());
            Connection conn = null; // Declare connection variable outside try block
            try {
                conn = db.getConnection();
                // Disable auto-commit to start a transaction
                conn.setAutoCommit(false);

                // Construct SQL query to update status for selected filter
                String updateQuery = "UPDATE icici.filters SET status = 0 WHERE filter_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    // Set the filter ID parameter
                    updateStmt.setInt(1, Integer.parseInt(filterId));
                    updateStmt.executeUpdate();
                }

                // Commit the transaction
                conn.commit();

                // Set success response
                responseNode = responseMapper.createObjectNode().put("message", "Status updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                // Set error response
                responseNode = responseMapper.createObjectNode().put("error", "Error occurred while updating status.");
                // Rollback the transaction in case of an error
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
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
        } else {
            // Handle the case when filterIdNode is null or not a textual value
            // Set error response
            responseNode = responseMapper.createObjectNode().put("error", "No valid filter ID provided.");
        }

        // Set response content type to JSON
        response.setContentType("application/json");
        // Write the JSON response to the output stream
        response.getWriter().write(responseMapper.writeValueAsString(responseNode));
    }
}
