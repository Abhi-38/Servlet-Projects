package com.test.dash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginApi")
public class LoginApi extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String QUERY = "SELECT COUNT(*) FROM ICICI.USERS WHERE USERNAME = ? AND PASSWORD = ?";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Authenticate user against the database
        boolean isValid = authenticateUser(username, password);

        if (isValid) {
            // Redirect to the dashboard page on successful login
            response.sendRedirect("dashboard.html");
        } else {
            // Display an error message on invalid login
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Invalid login. Please try again.');</script>");
            response.getWriter().println("<script>window.location.href = 'login.html';</script>");
        }
    }

    private boolean authenticateUser(String username, String password) {
        ConnectDb db = new ConnectDb();
        db.loadDriver(db.getDbDriver());
        Connection con = db.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(QUERY)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            // Log the exception (you can use a logging framework like Log4J)
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
