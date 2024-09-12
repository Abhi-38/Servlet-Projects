package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetCapitalServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//creating PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//read form data
		int countryindex = Integer.parseInt(req.getParameter("country"));
		//write B.logic
		String countries[] = {"New Delhi","Dhaka","Beging","Washington DC","London","Paris"};
		pw.println("<h1 style='color:red;text-align:center'>The Capital City Name is "+countries[countryindex]+"</h1>");
		
		//add home hyperlink
		pw.println("<h1 style='text-align:center'><a href= 'input.html'>home</a></h1>");
		//close stream
		pw.close();
	}//doPost(-,-)
}//class
