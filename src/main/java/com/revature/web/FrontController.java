package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorizations.AuthService;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;

public class FrontController extends HttpServlet {
	
	private static final UserController userController = new UserController();
	private static final UserService uServ = new UserService();
	private static final ObjectMapper om = new ObjectMapper();
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		
		String[] portions = URI.split("/");
		
		for(String portion : portions) {
			System.out.println(portion);
		}
		
		try {
			switch(portions[0]) {
			case "users":
				System.out.println("Users enterd");
				if(portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					System.out.println(id);
					AuthService.guard(req.getSession(false), id, "Employee","Admin");
					User u = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
				} else {
					AuthService.guard(req.getSession(false), "Employee","Admin");
					List<User> all = userController.findAllUsers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}
				res.setContentType("application/json");
				break;
			}
		} catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("You aren't logged in.");
			res.getWriter().println(om.writeValueAsString(message));
		} 
		catch(AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("Access denied.");
			res.getWriter().println(om.writeValueAsString(message));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		System.out.println(URI);
		try {
			switch(URI) {
			case "login":
				HttpSession session = req.getSession();
				
				User current = (User) session.getAttribute("currentUser");
				
				if(current != null) {
					res.setStatus(400);
					res.getWriter().println("You have already logged in as user " + current.getUsername());
					return;
				}
				
				BufferedReader reader = req.getReader();
				
				StringBuilder sb = new StringBuilder();
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				
				String body = sb.toString();
				
				LoginTemplate lt = om.readValue(body,LoginTemplate.class);
				
				User u = uServ.login(lt);
				PrintWriter writer = res.getWriter();
				
				if(u == null) {
					res.setStatus(400);
					
					writer.println("Username or password was incorrect. Try again.");
					return;
				}
				
				session.setAttribute("currentUser", u);
				
				res.setStatus(200);
				writer.println(om.writeValueAsString(u));
				res.setContentType("application/json");
				break;
			case "logout":
				if(userController.logout(req.getSession(false))) {
					res.setStatus(200);
					res.getWriter().println("You have been successfully logged out.");
				} else {
					res.setStatus(400);
					res.getWriter().println("You were not logged in to begin with.");
				}
				break;
			}
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
	}
}
