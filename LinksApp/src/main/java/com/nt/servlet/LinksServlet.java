package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LinksServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter stream using res obj
		PrintWriter pw = res.getWriter();
		//content type
		res.setContentType("text/html");
		//read additional key param value
		String p1Val = req.getParameter("p1");
		Locale locales[] = Locale.getAvailableLocales();
		//write the request processing logic
		if(p1Val.equalsIgnoreCase("link1")) {
			Set<String> langSet = new HashSet();
			for(Locale l : locales) {
				langSet.add(l.getDisplayLanguage());
			}
			pw.println("<h1 style='color:green;text-align:center'>All languages</h1>");
			langSet.forEach(lang-> pw.println(lang+","));//java 8 for each with LAMBDA expression
		}//if
		else if(p1Val.equalsIgnoreCase("link2")) {
			Set<String> countriesSet = new HashSet();
			for(Locale l:locales) {
				countriesSet.add(l.getDisplayCountry());
			}
			
			pw.println("<h1 style='color:green;text-align:center'>All countries are</h1>");
			countriesSet.forEach(country-> pw.println(country+","));
		}//else if
		else {
			//get system date and time
			LocalDateTime ldt = LocalDateTime.now();
			//get the month
			int month = ldt.getMonthValue();
			if(month>=3 && month<=6)
				pw.println("<h1 style='color:red;text-align:center'>Summer season</h1>");
			else if(month>=7 && month<=10)
				pw.println("<h1 style='color:blue;text-align:center'>Rainy Season</h1>");
			else
				pw.println("<h1 style='color:green;text-align:center'>Winter Season</h1>");
		}//else
		
		//add home hyperlink
		pw.println("<h1 style='color:green;text-align:center'><a href='input.html'>home</a></h1>");
		//close stream
		pw.close();
	}//doget(-,-)
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}//doPost
}//class
