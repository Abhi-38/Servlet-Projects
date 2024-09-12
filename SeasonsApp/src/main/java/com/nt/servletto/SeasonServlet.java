package com.nt.servletto;
//SeasonServlet.java
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SeasonServlet extends HttpServlet {
	//2nd service(-,-) method for using http features
	public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		//get PrintWriter from res obj pointing towards res obj
		PrintWriter pw = res.getWriter();
		//set responce content type
		res.setContentType("text/html");
		//writing BUssiness logic to get Season name 
		LocalDateTime ldt = LocalDateTime.now();
		//Registering the current Month
		int month = ldt.getMonthValue();
		//getting the season name
		String season = null;
		//Season Decider Logic
		if(month>1 && month<6)
			season = "This is Summer";
		else if(month>5 && month<8)
			season = "This is Rainy Season";
		else
			season = "This is Winter";
		//end of logic
		//printing the message
		if(month>1 && month<6)
			pw.println("<h1 style='color:orange;text-align:center'>"+season+"</h1>");
		else if(month>5 && month<8)
			pw.println("<h1 style='color:green;text-align:center'>"+season+"</h1>");
		else
			pw.println("<h1 style='color:cyan;text-align:center'>"+season+"</h1>");
		//adding home page
		pw.println("<br><a href='page.html'>Home</a>");
		pw.close();
	}//service(-,-)
}//class
