package com.abhi.prj;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Retrieval extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RetrievalDao rd = new RetrievalDao();
		rd.loadDriver(rd.getDbDriver());
		
		List<Member> members = rd.retrieveData();
		String responceUser = req.getParameter("response");
		if("yes".equals(responceUser)) {
			if (members != null && !members.isEmpty()) {
				// Data is available, forward to displayData.jsp
				req.setAttribute("members", members);
				req.getRequestDispatcher("/displayData.jsp").forward(req, res);
			}
		}
		else {
			// No data retrieved, display a message or redirect to a different page
			res.sendRedirect(req.getContextPath() + "/noData.jsp");
		}
	}
}
