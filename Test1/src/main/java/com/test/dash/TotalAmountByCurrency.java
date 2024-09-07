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


@WebServlet("/TotalAmountByCurrency")
public class TotalAmountByCurrency extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Database connection
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection con = db.getConnection();

        // SQL query to retrieve the total amount by Currency
        String query = "SELECT Currency, SUM(TotalAmount) AS TotalAmount FROM icici.bills GROUP BY Currency";

        JSONArray jsonArray = new JSONArray();

        try (// Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(query);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();){
            

            // Iterate through the result set and construct JSON response
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Currency", rs.getString("Currency"));
                jsonObject.put("TotalAmount", rs.getLong("TotalAmount"));
                jsonArray.put(jsonObject);
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

        // Set the response type to JSON
        response.setContentType("application/json");

        // Get the response writer
        PrintWriter out = response.getWriter();

        // Write the JSON response
        out.print(jsonArray);

        // Flush and close the writer
        out.flush();
        out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
