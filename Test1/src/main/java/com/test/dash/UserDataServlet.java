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

@WebServlet("/UserData")
public class UserDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Database connection
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection con = db.getConnection();

        // SQL query to retrieve data from icici.dropdown table
        String query = "SELECT * FROM icici.dropdown";

        JSONArray jsonArray = new JSONArray();

        try {
            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(query);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and construct JSON response
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("customercode", rs.getInt("customercode"));
                jsonObject.put("start_date", rs.getString("start_date"));
                jsonObject.put("end_date", rs.getString("end_date"));
                jsonObject.put("status", rs.getString("status"));
                jsonObject.put("payment_mode", rs.getString("payment_mode"));
                jsonObject.put("Debit_acc", rs.getString("Debit_acc"));
                jsonObject.put("benf_name", rs.getString("benf_name"));
                jsonObject.put("bank", rs.getString("bank"));
                jsonObject.put("credit_acc", rs.getString("credit_acc"));
                jsonObject.put("state", rs.getString("state"));
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
        doGet(request, response);
    }

}