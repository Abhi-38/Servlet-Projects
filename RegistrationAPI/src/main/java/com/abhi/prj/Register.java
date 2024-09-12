package com.abhi.prj;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//we are collecting the data from form
		String uname = req.getParameter("uname");
		String passwd = req.getParameter("password");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		//now we are passing the data to the member
		Member mem = new Member(uname, passwd, email, phone);
		//for inserting data to the databse we are calling RegisterDao
		RegisterDao rd = new RegisterDao();
		//storing the result
		String result = null;
		//with this method we insert the data in memeber class private variables
		result = rd.insert(mem);
		//generate responce to browser
		res.getWriter().print(result);
	}

}
