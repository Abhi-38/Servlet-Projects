package com.test.dash;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DropDownServlet")
public class DropDownServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve filter parameters from the request
        FilterParameters filterParams = extractFilterParameters(request);
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());

        // Fetch data from the dropdown table based on filter parameters
        String query = null;
        try {
            query = constructQuery(db.getConnection(), filterParams);
            fetchDataAndSendResponse(query, filterParams, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while fetching dropdown data.");
        }
    }

    private FilterParameters extractFilterParameters(HttpServletRequest request) {
        FilterParameters filterParams = new FilterParameters();
        filterParams.setCustomerCode(request.getParameter("customerCode"));
        filterParams.setFromDate(request.getParameter("fromDate"));
        filterParams.setToDate(request.getParameter("toDate"));
        filterParams.setStatus(request.getParameter("status"));
        filterParams.setPaymentMode(request.getParameter("paymentMode"));
        filterParams.setDebitAccountNumber(request.getParameter("debitAccountNumber"));
        filterParams.setBank(request.getParameter("bank"));
        filterParams.setCreditAccountNumber(request.getParameter("creditAccountNumber"));
        filterParams.setBankState(request.getParameter("bankState"));
        return filterParams;
    }

    private String constructQuery(Connection conn, FilterParameters filterParams) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM icici.dropdown WHERE 1=1");

        // Add WHERE clause based on filter parameters
        if (filterParams.getCustomerCode() != null)
            queryBuilder.append(" AND customercode = ?");
        if (filterParams.getFromDate() != null)
            queryBuilder.append(" AND start_date >= ?");
        if (filterParams.getToDate() != null)
            queryBuilder.append(" AND end_date <= ?");
        if (filterParams.getStatus() != null)
            queryBuilder.append(" AND status = ?");
        if (filterParams.getPaymentMode() != null)
            queryBuilder.append(" AND payment_mode = ?");
        if (filterParams.getDebitAccountNumber() != null)
            queryBuilder.append(" AND Debit_acc = ?");
        if (filterParams.getBank() != null)
            queryBuilder.append(" AND bank = ?");
        if (filterParams.getCreditAccountNumber() != null)
            queryBuilder.append(" AND credit_acc = ?");
        if (filterParams.getBankState() != null)
            queryBuilder.append(" AND state = ?");

        // Add semicolon to end the query
        queryBuilder.append(";");

        return queryBuilder.toString();
    }

    private void fetchDataAndSendResponse(String query, FilterParameters filterParams, HttpServletResponse response)
            throws SQLException, IOException {
        ConnectDb db = new ConnectDb();
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set filter parameter values
            int paramIndex = 1;
            if (filterParams.getCustomerCode() != null)
                pstmt.setString(paramIndex++, filterParams.getCustomerCode());
            if (filterParams.getFromDate() != null)
                pstmt.setString(paramIndex++, filterParams.getFromDate());
            if (filterParams.getToDate() != null)
                pstmt.setString(paramIndex++, filterParams.getToDate());
            if (filterParams.getStatus() != null)
                pstmt.setString(paramIndex++, filterParams.getStatus());
            if (filterParams.getPaymentMode() != null)
                pstmt.setString(paramIndex++, filterParams.getPaymentMode());
            if (filterParams.getDebitAccountNumber() != null)
                pstmt.setString(paramIndex++, filterParams.getDebitAccountNumber());
            if (filterParams.getBank() != null)
                pstmt.setString(paramIndex++, filterParams.getBank());
            if (filterParams.getCreditAccountNumber() != null)
                pstmt.setString(paramIndex++, filterParams.getCreditAccountNumber());
            if (filterParams.getBankState() != null)
                pstmt.setString(paramIndex++, filterParams.getBankState());

            // Execute query and process results
            try (ResultSet rs = pstmt.executeQuery()) {
                JSONArray jsonArray = new JSONArray();
                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        String columnName = rs.getMetaData().getColumnName(i);
                        String columnValue = rs.getString(i);
                        jsonObject.put(columnName.toUpperCase(), columnValue);
                    }
                    jsonArray.put(jsonObject);
                }
                sendJsonResponse(jsonArray, response);
            }

        }
    }

    private void sendJsonResponse(JSONArray jsonArray, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonArray.toString());
        out.flush();
    }

}
