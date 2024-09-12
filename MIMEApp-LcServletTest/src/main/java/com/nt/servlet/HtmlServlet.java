//HtmlServlet.java
package com.nt.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class HtmlServlet extends HttpServlet
{//2nd service(-,-) method overriding 
	protected void service(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException{
	 //set responce content type 
	 	res.setContentType("text/html");
		//get PrintWriter stream
		PrintWriter pw = res.getWriter();
		//display the content as db table 
		pw.println("<table align='center' border='1' bgcolor='pink'>");
		pw.println("<tr><th>TeamName</th> <th>year</th> <th>place</th></tr>");
		pw.println("<tr><td>India</td> <td>2007</td> <td>South Africa</td></tr>");
		pw.println("<tr><td>Pakistan</td> <td>2009</td> <td>England</td></tr>");
		pw.println("<tr><td>England</td> <td>2010</td> <td>West Indies</td></tr>");
		pw.println("<tr><td>West Indies</td> <td>2012</td> <td>Australia</td></tr>");
		pw.println("</table>");
		//set responce header "refresh" value
		res.setHeader("refresh", "10");
		//close the stream
		pw.println("<br><br><h1>Date and time::"+new java.util.Date()+"</h1>");
		pw.close();
	}

}