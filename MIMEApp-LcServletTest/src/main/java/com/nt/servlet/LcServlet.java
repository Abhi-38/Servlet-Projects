package com.nt.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
/*import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;*/
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LcServlet extends HttpServlet {
	static {
		System.out.println("LcServlet::static");
	}
	
	public LcServlet() {
		System.out.println("LcServlet::0-param constructor");
	}
	
	@Override
	public void init(ServletConfig cs) {
		System.out.println("LcServlet::init()");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("LcServlet::doPost(-,-)");
		
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//writing business logic
		Date date = new Date();
		//writing the output with res obj
		pw.println("<h1 style='color:green;text-align:center'>Date and Time::: "+date+"</h1>");
		//close the stream
		pw.close();
	}//service(-,-)
	
	@Override
	public void destroy() {
		System.out.println("LcServlet::destroy()");
	}//destroy()
}//class
