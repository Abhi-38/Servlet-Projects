package com.nt.servlet;
//ArithmeticServlet.java
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArithmeticServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set Content type
		res.setContentType("text/html");
		//add additional req param
		String s1val = req.getParameter("s1");
		//read text box values and convert them into floating point values when clicked on submit
		float val1=0.0f,val2=0.0f;
		if(!s1val.equalsIgnoreCase("link1") && !s1val.equalsIgnoreCase("link2")) {
			val1 = Float.parseFloat(req.getParameter("t1"));
			val2 = Float.parseFloat(req.getParameter("t2"));
		}
		//get local system date and time
		LocalDateTime ldt = LocalDateTime.now();
		//differentiate the logic of different hyperlinks
		if(s1val.equalsIgnoreCase("add")) {
			pw.println("<h1 style='color:red;text-align:center'>Addition is::: "+(val1+val2)+"</h1>");
		}//addition
		else if(s1val.equalsIgnoreCase("sub")) {
			pw.println("<h1 style='color:red;text-align:center'>Substraction is::: "+(val1-val2)+"</h1>");
		}//substraction
		else if(s1val.equalsIgnoreCase("mul")) {
			pw.println("<h1 style='color:red;text-align:center'>Multiplication is::: "+(val1*val2)+"</h1>");;
		}//multiplication
		else if(s1val.equalsIgnoreCase("div")){
			pw.println("<h1 style='color:red;text-align:center'>Division is::: "+(val1/val2)+"</h1>");
		}//division
		else if(s1val.equalsIgnoreCase("link1")) {
			//get the current hour
			int hour = ldt.getHour();
			if(hour<12)
				pw.println("<h1 style='color:orange;text-align:center'>Good Morning</h1>");
			else if(hour<16)
				pw.println("<h1 style='color:red;text-align:center'>Good Afternoon</h1>");
			else if(hour<20)
				pw.println("<h1 style='color:pink;text-align:center'>Good Evening</h1>");
			else
				pw.println("<h1 style='color:grey;text-align:center'>Good Night</h1>");
		}//GetWishMessage
		else {
			//get the current month
			int month = ldt.getMonthValue();
			//logic
			if(month>=3 && month<6)
				pw.println("<h1 style='color:orange;text-align:center'>Summer season</h1>");
			else if(month>=7 && month<11)
				pw.println("<h1 style='color:green;text-align:center'>Rainy Season</h1>");
			else
				pw.println("<h1 style='color:cyan;text-align:center'>Winter Season</h1>");
		}
		//adding home page
		pw.println("<br><br> <a href='input.html'>home</a>");
		pw.close();
	}//doget
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);	
	}//dopost
}//class
