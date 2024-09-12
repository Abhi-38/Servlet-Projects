package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AllFormCompServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Get PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//read form data
		String name = req.getParameter("cname");
		float age = Float.parseFloat(req.getParameter("cage"));
		String address = req.getParameter("caddrs");
		String gender = req.getParameter("gender");
		String ms = req.getParameter("ms");
		if(ms == null) {
			ms="single";  ///handling non checked state for checkbox
		}
		String qlfy = req.getParameter("qualify");
		if(qlfy == null)
			qlfy = "No qualification Selected"; //handling non selected state
		String hobbies[] = req.getParameterValues("hobbies");
		
		if(hobbies==null)
			hobbies = new String[] {"no hobbies selected"};
		
		String favCousins[] = req.getParameterValues("fav-cuisine");
		if(favCousins==null)
			favCousins = new String[] {"no cuisine selected"};
		
		int luckyno = Integer.parseInt(req.getParameter("lucky"));
		String favColor = req.getParameter("favcolor");
		int salary = Integer.parseInt(req.getParameter("salary"));
		String email = req.getParameter("email");
		String dob = req.getParameter("dob");
		String tob = req.getParameter("tob");
		String doj = req.getParameter("doj");
		long contact = Long.parseLong(req.getParameter("contact"));
		String month = req.getParameter("month");
		String week = req.getParameter("favweek");
		String url = req.getParameter("insturl");
		String ss = req.getParameter("sString");
		
		//WRITE B.LOGIC
		if(gender.equalsIgnoreCase("M")) {
			if(age<5)
				pw.println("<h1>Master."+name+" u r a baby boy</h1>");
			else if(age<=12)
				pw.println("<h1>Master."+name+" u r a small boy</h1>");
			else if(age<=19)
				pw.println("<h1>Mr."+name+"u r teenage boy</h1>");
			else if(age<=35)
				pw.println("<h1>Mr."+name+"u r a young man</h1>");
			else if(age<=50)
				pw.println("<h1>Mr."+name+"u r a middle aged man</h1>");
			else 
				pw.println("<h1>Mr."+name+"u r budda</h1>");
		}else {
			if(age<5)
				pw.println("<h1>Miss."+name+" u r a baby girl</h1>");
			else if(age<=12)
				pw.println("<h1>Miss."+name+" u r a small girl</h1>");
			else if(age<=19) {
				if(ms.equalsIgnoreCase("married"))
					pw.println("<h1>Mrs."+name+"u r teenage married girl</h1>");
				else
					pw.println("<h1>Miss."+name+"u r teenage girl</h1>");
				}
			else if(age<=35) {
					if(ms.equalsIgnoreCase("married"))
						pw.println("<h1>Mrs."+name+"u r young married woman</h1>");
					else
						pw.println("<h1>Miss."+name+"u r young woman</h1>");
					}
			else if(age<=50)
				if(ms.equalsIgnoreCase("married"))
					pw.println("<h1>Mrs."+name+"u r middle aged married woman</h1>");
				else
					pw.println("<h1>Miss."+name+"u r middle aged woman</h1>");
			else 
				if(ms.equalsIgnoreCase("married"))
					pw.println("<h1>Mrs."+name+"u r married buddi</h1>");
				else
					pw.println("<h1>Miss."+name+"u r buddi</h1>");
		}//else
		//display data from the form
		pw.println("<h1>form data is</h1><br>");
		pw.println("<b>Name::"+name+"</b><br>");
		pw.println("<b>age::"+age+"</b><br>");
		pw.println("<b>address::"+address+"</b><br>");
		pw.println("<b>gende::"+gender+"</b><br>");
		pw.println("<b>MS::"+ms+"</b><br>");
		pw.println("<b>Qulaification::"+qlfy+"</b><br>");
		pw.println("<b>Hobbies::"+Arrays.asList(hobbies)+"</b><br>");
		pw.println("<b>Fav-ccousins::"+Arrays.asList(favCousins)+"</b><br>");
		pw.println("<b>Lucky Number::"+luckyno+"</b><br>");
		pw.println("<b>Fav Color::"+favColor+"</b><br>");
		pw.println("<b>salary::"+salary+"</b><br>");
		pw.println("<b>email::"+email+"</b><br>");
		pw.println("<b>DOB::"+dob+"</b><br>");
		pw.println("<b>TOB::"+tob+"</b><br>");
		pw.println("<b>DOJ::"+doj+"</b><br>");
		pw.println("<b>Mobile::"+contact+"</b><br>");
		pw.println("<b>Fav Month::"+month+"</b><br>");
		pw.println("<b>Fav week day::"+week+"</b><br>");
		pw.println("<b>Insta url::"+url+"</b><br>");
		pw.println("<b>Search::"+ss+"</b><br>");
		//add home hyperlink
		pw.println("<br><br><a href='all_form_comps.html'>home</a>");
		pw.close();
	}//doget
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}//class