package com.bank.controllers;

import java.sql.SQLException;
import java.util.List;

import com.bank.daos.AccountDAO;
import com.bank.models.Account;
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
		
		//post
		app.post("/accounts", AccountController::insertNewAccount);
		
		//put
		app.put("/accounts/:id", AccountController::updateAccount);
		
		//delete
		app.delete("/accounts/:id", AccountController::deleteAccount);
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
			AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("id"));
			Account row = ctx.bodyAsClass(Account.class);
			dao.update(row,id);
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
}