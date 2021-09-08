package com.bank.controllers;

import java.sql.SQLException;
import java.util.List;

import com.bank.daos.AccountDAO;
import com.bank.daos.CustomerDAO;
import com.bank.models.Account;
import com.bank.models.Customer;
import com.bank.utils.ConnectionFactory;

import exceptions.NoSQLResultsException;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AccountController {
	private static Javalin javalin;
	
	public static void init(Javalin app) {
		javalin = app;
		//get
		app.get("/account/test", AccountController::testConnection);
		app.get("/accounts", AccountController::getAll);
		app.get("/accounts/:id", AccountController::getAccountByID);
		app.get("/clients/accounts/test", AccountController::testConnection);
		app.get("/clients/:id/accounts", AccountController::getCustomerAccounts);
		app.get("/clients/:clientid/accounts/:accountid", AccountController::getSpecificCustomerAccount);
		
		//post
		app.post("/accounts", AccountController::insertNewAccount);
		app.post("/clients/:id/accounts", AccountController::addNewAccount);
		
		//put
		app.put("/clients/:clientid/accounts/:accountid", AccountController::updateAccount);
		
		//delete
		app.delete("/accounts/:id", AccountController::deleteAccount);
		app.delete("/clients/:clientid/accounts/:accountid", AccountController::deleteSpecificAccount);
	}

	public static void testConnection(Context ctx) {
		ctx.status(200);
		ctx.result("Test Account Received!");
	}

	public static void insertNewAccount(Context ctx) throws SQLException {
		AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
		Account row = ctx.bodyAsClass(Account.class);
		dao.save(row);
		ctx.status(201);
	}

	public static void getAccountByID(Context ctx) throws SQLException {
		try {
			AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("id"));
			Account account = dao.get(id);
			ctx.json(account);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("No Account Found");
		}
	}
	
	public static void getAll(Context ctx) throws SQLException, NoSQLResultsException {
		AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
		List<Account> allAccounts = dao.getAll();
		ctx.json(allAccounts);
	}
	
	public static void updateAccount(Context ctx) throws SQLException {
		try {
			AccountDAO accountDAO = new AccountDAO(ConnectionFactory.getConnection());
			int accountID = Integer.parseInt(ctx.pathParam("accountid"));
			int customerID = Integer.parseInt(ctx.pathParam("clientid"));
			Account row = ctx.bodyAsClass(Account.class);
			accountDAO.updateClientAccount(row.getBalance(), accountID, customerID);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("Account not found");
		}  
	}
	
	public static void deleteAccount(Context ctx) throws SQLException{
		try {
			AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("id"));
			dao.delete(id);
			ctx.status(205);
		}catch(NoSQLResultsException e) {
			ctx.status(404);
			ctx.result("Account not found");
		}
	}
	public static void getCustomerAccounts(Context ctx) throws SQLException{
		AccountDAO accountDAO = new AccountDAO(ConnectionFactory.getConnection());
		CustomerDAO customerDAO = new CustomerDAO(ConnectionFactory.getConnection());

		int id = Integer.parseInt(ctx.pathParam("id"));
		
		String greaterThan = ctx.queryParam("amountGreaterThan");
		String lessThan = ctx.queryParam("amountLessThan");
		
		
		if(greaterThan != null || lessThan != null) {
			Double top = greaterThan == null ? null : Double.parseDouble(greaterThan);
			Double bottom = lessThan == null ? null : Double.parseDouble(lessThan);
			getAccountsInRange(ctx,top,bottom,id);
			return;
		}
		try {
			Customer customer = customerDAO.get(id);
			List<Account> allCustomerAccounts = accountDAO.getAllClientAccounts(customer.getCustomerID());
			ctx.status(201);
			ctx.json(allCustomerAccounts);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("Customer Not Found");
		}
	} 
	public static void addNewAccount(Context ctx)throws SQLException{
		AccountDAO accountDAO = new AccountDAO(ConnectionFactory.getConnection());
		CustomerDAO customerDAO = new CustomerDAO(ConnectionFactory.getConnection());

		Account row = ctx.bodyAsClass(Account.class);
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Customer customer = customerDAO.get(id);
			row.setCustomerID(customer.getCustomerID());
			accountDAO.save(row);
			ctx.status(201);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("Customer Not Found");
		}
	}
	public static void getSpecificCustomerAccount(Context ctx) throws SQLException{
		AccountDAO accountDAO = new AccountDAO(ConnectionFactory.getConnection());

		int clientID = Integer.parseInt(ctx.pathParam("clientid"));
		int accountID = Integer.parseInt(ctx.pathParam("accountid"));
		try {
			Account account = accountDAO.getClientAccount(accountID,clientID);
			ctx.json(account);
			ctx.status(201);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("Account Or Customer Not Found");
		}
	}
	public static void deleteSpecificAccount(Context ctx) throws SQLException {
		try {
			AccountDAO accountDAO = new AccountDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("accountid"));
			accountDAO.delete(id);
			ctx.status(205);
		}catch(NoSQLResultsException e) {
			ctx.status(404);
			ctx.result("Account not found");
		}
	}
	public static void getAccountsInRange(Context ctx, Double greaterThan, Double lessThan, int customerID) throws SQLException{
		AccountDAO accountDAO = new AccountDAO(ConnectionFactory.getConnection());
		CustomerDAO customerDAO = new CustomerDAO(ConnectionFactory.getConnection());
		
		try {
			Customer customer = customerDAO.get(customerID);
			List<Account> accountsInRange = accountDAO.getAccountsInRange(customer.getCustomerID(),greaterThan,lessThan);
			ctx.status(201);
			ctx.json(accountsInRange);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("Customer Not Found");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
