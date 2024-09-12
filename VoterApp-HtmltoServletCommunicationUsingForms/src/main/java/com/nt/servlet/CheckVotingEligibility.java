package com.nt.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
//CheckVotingEligibility.java
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckVotingEligibility extends HttpServlet {

	//using doXxx()
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Creating PrintWriter Stream from res obj pointing to res obj
		System.out.println("CheckVotingEligibility.doPost()");
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//read form data as req param value
		String tname = req.getParameter("pname");
		String taddrs = req.getParameter("paddrs");
		String tage = req.getParameter("page");
		int age = Integer.parseInt(tage);
		//building the Business Logic
		if(age>18)
			pw.println("<h1 style='color:green;text-align:center'>Mr/Miss/Mrs "+tname+" of "+taddrs+" you are eligible to vote"+"</h1>");
		else
			pw.println("<h1 style='color:red;text-align:center'>Mr/Miss/Mrs "+tname+" of "+taddrs+" you are not eligible to vote"+"</h1>");
		//end of B.logic
		//adding home hyperlink(graphical)
		pw.println("<center><a style='text-align:center' href='input.html'><img src='images/home.png'></a></center>");
		pw.close();
		
	}//doGet()
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//tracing
		System.out.println("CheckVotingEligibility.doGet()");
		//get Printwriter from res obj pointing to res obj
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//write output to browser
		pw.println("<h1 style='color:red;text-align:center>System Date and Time is:"+new java.util.Date()+"</h1>");
		//adding home hyperlink(graphical)
		pw.println("<center><a style='text-align:center' href='input.html'><img src='images/home.png'></a></center>");
		pw.close();		
	}//doGet()
}//class
