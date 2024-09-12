package com.abhi.prj;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

@WebServlet("/")
public class PieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Set the chart data as a request attribute
        req.setAttribute("chartData", getChartData());

        // Forward the request to the "charts.jsp" page
        req.getRequestDispatcher("charts.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Forward the request to the doGet method
        doGet(req, res);
    }

    private String getChartData() {
        JSONArray jsonArray = new JSONArray();

        // Add header row
        JSONArray headerRow = new JSONArray();
        headerRow.add("Task");
        headerRow.add("Hours per Day");
        jsonArray.add(headerRow);

        // Add data rows
        jsonArray.add(createDataRow("Work", 11));
        jsonArray.add(createDataRow("Eat", 2));
        jsonArray.add(createDataRow("Commute", 2));
        jsonArray.add(createDataRow("Watch TV", 2));
        jsonArray.add(createDataRow("Sleep", 7));

        // Print the JSON array to the console for debugging
        System.out.println("ChartData: " + jsonArray.toString());

        return jsonArray.toString();
    }

    private JSONArray createDataRow(String task, int hours) {
        JSONArray row = new JSONArray();
        row.add(task);
        row.add(hours);
        return row;
    }
}
