package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WishMessageServlet extends HttpServlet {
	//second service(-,-) method
	public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		//get PrintWriter from res obj pointing res obj
		PrintWriter pw = res.getWriter();
		//set responce content type
		res.setContentType("text/html");
		//write B.L to generate wish message
		LocalDateTime ldt = LocalDateTime.now();
		//get current hour of the day
		int hour = ldt.getHour();
		//generate Wish Message
		String msg = null;
		if(hour < 12)
			msg = "Good Morning";
		else if(hour < 16)
			msg = "Good Afternoon";
		else if(hour < 20)
			msg = "Good Evening";
		else
			msg = "Good Night";
		//display generated output
		pw.println("<h1 style='color:orange;text-align:center'>"+msg+"</h1>");
		//add home hyperlink
		pw.println("<br><a href='page.html'>Home</a>");
		pw.close();
	}
}
