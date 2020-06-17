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
import com.revature.exceptions.ProhibitedUserException;
import com.revature.models.AbstractAccount;
import com.revature.models.AccountStatus;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.DepWithTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.TimeTemplate;
import com.revature.templates.TransferTemplate;

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
		res.setStatus(404);
		res.setContentType("text/plain");
		
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		
		String[] portions = URI.split("/");
		
		try {
			switch(portions[0]) {
			case "users":
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
						break;
					}
					
				} else if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					System.out.println(id);
					AuthService.guardAccount(req.getSession(false), id, "Employee","Admin");
					AbstractAccount a = accountController.findAccountById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(a));
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
		res.setStatus(404);
		res.setContentType("text/plain");
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
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
				if (portions.length == 2) {
					BufferedReader amountReader = req.getReader();
					
					StringBuilder amountBuilder = new StringBuilder();
					
					String dataLine;
					
					
					while ((dataLine = amountReader.readLine()) != null) {
						amountBuilder.append(dataLine);
					}
					String amountBody = amountBuilder.toString();
					switch(portions[1]) {
					case "deposit":
						DepWithTemplate depTemplate = om.readValue(amountBody, DepWithTemplate.class);
						AuthService.guardAccount(aSession, depTemplate.getAccountId(), "Admin");
						
						AbstractAccount depositingAccount = accountController.findAccountById(depTemplate.getAccountId());
						if (depositingAccount == null) {
							res.setStatus(400);
							res.getWriter().println(om.writeValueAsString(new MessageTemplate("Invalid Account ID")));
						}
						else {
							try {
								accountController.deposit(depositingAccount,depTemplate.getAmount());
								res.setStatus(200);
								String depositMessage = "$" + depTemplate.getAmount() + " has been deposited into Account #" + depositingAccount.getAccountId();
								res.getWriter().println(om.writeValueAsString(new MessageTemplate(depositMessage)));
							} catch (IllegalArgumentException e) {
								res.setStatus(400);
								res.getWriter().println(om.writeValueAsString(new MessageTemplate("Cannot deposit $0 or less.")));
							}
						}
							
						break;
					case "withdraw":
						DepWithTemplate withTemplate = om.readValue(amountBody, DepWithTemplate.class);
						AuthService.guardAccount(aSession, withTemplate.getAccountId(), "Admin");
						
						AbstractAccount withdrawingAccount = accountController.findAccountById(withTemplate.getAccountId());
						if (withdrawingAccount == null) {
							res.setStatus(400);
							res.getWriter().println(om.writeValueAsString(new MessageTemplate("Invalid Account ID")));
						}
						else {
							if (withdrawingAccount.getBalance() < withTemplate.getAmount()) {
								res.setStatus(400);
								res.getWriter().println(om.writeValueAsString(new MessageTemplate("Cannot withdraw more than $" + withdrawingAccount.getBalance())));
							} else {
								try {
									accountController.withdraw(withdrawingAccount,withTemplate.getAmount());
									res.setStatus(200);
									String depositMessage = "$" + withTemplate.getAmount() + " has been withdrawn from Account #" + withdrawingAccount.getAccountId();
									res.getWriter().println(om.writeValueAsString(new MessageTemplate(depositMessage)));
								} catch (IllegalArgumentException e) {
									res.setStatus(400);
									res.getWriter().println(om.writeValueAsString(new MessageTemplate("Cannot withdraw $0 or less.")));
								}
							}
						}
						break;
					case "transfer":
						TransferTemplate transTemplate = om.readValue(amountBody, TransferTemplate.class);
						AuthService.guardAccount(aSession, transTemplate.getSourceAccountId(), "Admin");
						
						AbstractAccount sender = accountController.findAccountById(transTemplate.getSourceAccountId());
						AbstractAccount receiver = accountController.findAccountById(transTemplate.getTargetAccountId());
						if (sender == null || receiver == null) {
							res.setStatus(400);
							res.getWriter().println(om.writeValueAsString(new MessageTemplate("Invalid Account ID")));
						}
						else {
							if (sender.getBalance() < transTemplate.getAmount()) {
								res.setStatus(400);
								res.getWriter().println(om.writeValueAsString(new MessageTemplate("Cannot withdraw more than $" + sender.getBalance())));
							} else {
								try {
									accountController.transfer(sender,receiver,transTemplate.getAmount());
									res.setStatus(200);
									String depositMessage = "$" + transTemplate.getAmount() + " has been transferred from Account #" + sender.getAccountId()
									+ " to Account #" + receiver.getAccountId();
									res.getWriter().println(om.writeValueAsString(new MessageTemplate(depositMessage)));
								} catch (IllegalArgumentException e) {
									res.setStatus(400);
									res.getWriter().println(om.writeValueAsString(new MessageTemplate("Cannot transfer $0 or less.")));
								}
							}
						}
						break;
					}
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
			case "passtime":
				AuthService.guard(req.getSession(false),"Admin");
				BufferedReader timeReader = req.getReader();
				
				StringBuilder timeBuilder = new StringBuilder();
				
				String curLine;
				
				while ((curLine = timeReader.readLine()) != null) {
					timeBuilder.append(curLine);
				}
				
				String timeBody = timeBuilder.toString();
				TimeTemplate time = om.readValue(timeBody, TimeTemplate.class);
				int months = time.getNumOfMonths();
				List<AbstractAccount> all = accountController.findAllAccounts();
				for(int count = 0; count < months; count++) {
					for(AbstractAccount acc : all) {
						if(acc.getType().getId() == 2) {
							System.out.println("Original balance: " + acc.getBalance());
							acc.setBalance(acc.getBalance() + (acc.getBalance() * SavingsAccount.annualInterest / 12.0));
							System.out.println("Final balance: " + acc.getBalance());
							accountController.updateAccount(acc);
						}
					}
				}
				res.setStatus(200);
				break;
			case "premium":
				try {AuthService.guard(req.getSession(false),"Standard");}
				catch(ProhibitedUserException e) {
					res.getWriter().println(om.writeValueAsString(new MessageTemplate("You're already at or above Premium user status.")));
					res.setContentType("application/json");
					res.setStatus(401);
					return;
				}
				res.setStatus(200);
			}
		} catch (NotLoggedInException e) {
			e.printStackTrace();
			res.setStatus(401);
			res.getWriter().println(om.writeValueAsString(new MessageTemplate("You're not logged in.")));
			res.setContentType("application/json"); 
			}
			catch (AuthorizationException e) {
			e.printStackTrace();
			res.setStatus(401);
			res.getWriter().println(om.writeValueAsString(new MessageTemplate("Access denied")));
			res.setContentType("application/json");
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
		res.setStatus(404);
		res.setContentType("text/plain");
		final String URI = req.getRequestURI().replaceAll("/project-nKlapatch", "").replaceFirst("/","");
		String portions[] = URI.split("/");
		PrintWriter writer = res.getWriter();
		try {
			HttpSession session = req.getSession();
			
			BufferedReader reader = req.getReader();
			
			StringBuilder sb = new StringBuilder();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String body = sb.toString();
			switch(portions[0]) {
			case "users":
				
				
				User dataForUser = om.readValue(body,User.class);
				
				AuthService.guard(session, dataForUser.getId(), "Admin");
				
				
				
				if(userController.updateUser(dataForUser) == 0) {
					User changedUser = userController.findUserById(dataForUser.getId());
					res.setStatus(200);
					writer.println(om.writeValueAsString(changedUser));
					
				}
				else {
					res.setStatus(400);
					writer.println(om.writeValueAsString(new MessageTemplate("No user was updated. The entered user may not exist.")));
				}
				res.setContentType("application/json");
				break;
			case "accounts":
				
				AbstractAccount dataForAccount = om.readValue(body,AbstractAccount.class);
				
				AuthService.guard(session, "Admin");
				
				
				
				if(accountController.updateAccount(dataForAccount) == 0) {
					AbstractAccount changedAccount = accountController.findAccountById(dataForAccount.getAccountId());
					res.setStatus(200);
					writer.println(om.writeValueAsString(changedAccount));
					
				}
				else {
					res.setStatus(400);
					writer.println(om.writeValueAsString(new MessageTemplate("No account was updated. The entered account may not exist.")));
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
