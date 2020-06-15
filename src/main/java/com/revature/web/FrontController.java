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
import com.revature.models.AbstractAccount;
import com.revature.models.AccountStatus;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;

public class FrontController extends HttpServlet {
	
	private static final UserController userController = new UserController();
	private static final UserService uServ = new UserService();
	private static final AccountController accountController = new AccountController();
	private static final ObjectMapper om = new ObjectMapper();
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		if(AbstractAccount.statusMap.isEmpty()) {
			AbstractAccount.statusMap.put(1,AccountStatus.Pending);
			AbstractAccount.statusMap.put(2, AccountStatus.Open);
			AbstractAccount.statusMap.put(3, AccountStatus.Closed);
			AbstractAccount.statusMap.put(4, AccountStatus.Denied);
		}
		
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		
		String[] portions = URI.split("/");
		
		for(String portion : portions) {
			System.out.println(portion);
		}
		
		try {
			switch(portions[0]) {
			case "users":
				System.out.println("Users entered");
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
			case "accounts":
				System.out.println("Accounts entered");
				if(portions.length == 3) {
					List<AbstractAccount> all;
					switch(portions[1]) {
					case "status":
						AuthService.guard(req.getSession(false), "Employee","Admin");
						all = accountController.findAccountsByStatus(Integer.parseInt(portions[2]));
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
						break;
					case "owner":
						int id = Integer.parseInt(portions[2]);
						AuthService.guard(req.getSession(false), id, "Employee","Admin");
						all = accountController.findAccountsByOwner(id);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
					}
					
				} else if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					System.out.println(id);
					AuthService.guard(req.getSession(false), id, "Employee","Admin");
					User u = userController.findUserById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
				} else {
					AuthService.guard(req.getSession(false), "Employee","Admin");
					List<AbstractAccount> all = accountController.findAllAccounts();
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
		if(AbstractAccount.statusMap.isEmpty()) {
			AbstractAccount.statusMap.put(1,AccountStatus.Pending);
			AbstractAccount.statusMap.put(2, AccountStatus.Open);
			AbstractAccount.statusMap.put(3, AccountStatus.Closed);
			AbstractAccount.statusMap.put(4, AccountStatus.Denied);
		}
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		System.out.println(URI);
		String[] portions = URI.split("/");
		try {
			switch(portions[0]) {
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
			case "accounts":
				HttpSession aSession = req.getSession();
				System.out.println("Adding account...");
				if(portions.length == 3) {
					switch(portions[1]) {
					case "withdraw":
						break;
					}
					
				} else if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					System.out.println(id);
					AuthService.guard(req.getSession(false), id, "Employee","Admin");
					res.setStatus(200);
					//res.getWriter().println(om.writeValueAsString(u));
				} else {
					AuthService.guard(req.getSession(false), "Standard","Premium","Employee","Admin");
					User currentUser = (User) aSession.getAttribute("currentUser");
					BufferedReader aReader = req.getReader();
					
					StringBuilder asb = new StringBuilder();
					
					String curLine;
					
					while ((curLine = aReader.readLine()) != null) {
						asb.append(curLine);
					}
					
					String accountBody = asb.toString();
					
					AbstractAccount AccountData = om.readValue(accountBody,AbstractAccount.class);
					int newAccountId = accountController.insert(AccountData, currentUser);
					
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(accountController.findAccountById(newAccountId)));
				}
				res.setContentType("application/json");
				break;
			}
		} catch (AuthorizationException e) {
			e.printStackTrace();
			res.setStatus(401);
			res.getWriter().println(om.writeValueAsString(new MessageTemplate("The incoming token has expired.")));
		}
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if(AbstractAccount.statusMap.isEmpty()) {
			AbstractAccount.statusMap.put(1,AccountStatus.Pending);
			AbstractAccount.statusMap.put(2, AccountStatus.Open);
			AbstractAccount.statusMap.put(3, AccountStatus.Closed);
			AbstractAccount.statusMap.put(4, AccountStatus.Denied);
		}
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		String portions[] = URI.split("/");
		PrintWriter writer = res.getWriter();
		try {
			switch(portions[0]) {
			case "users":
				HttpSession session = req.getSession();
				
				BufferedReader reader = req.getReader();
				
				StringBuilder sb = new StringBuilder();
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				
				String body = sb.toString();
				
				User dataForUser = om.readValue(body,User.class);
				
				AuthService.guard(session, dataForUser.getId(), "Admin");
				
				
				
				if(userController.updateUser(dataForUser) == 0) {
					User changedUser = userController.findUserById(dataForUser.getId());
					res.setStatus(200);
					writer.println(om.writeValueAsString(changedUser));
					
				}
				else {
					res.setStatus(401);
					writer.println(om.writeValueAsString(new MessageTemplate("No user was updated. The entered user may not exist.")));
				}
				res.setContentType("application/json");
				break;
			}
		} catch(AuthorizationException e) {
			res.setStatus(401);
			writer.println("Access Denied");
		}
	}
}
