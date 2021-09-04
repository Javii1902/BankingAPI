package com.bank.controllers;

import java.sql.SQLException;

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
		app.get("/test", AccountController::testConnection);
		app.post("/test/:id", AccountController::insertTestData);
		app.get("/test/:id", AccountController::getById);

	}

	public static void testConnection(Context ctx) {
		ctx.status(200);
		ctx.result("Test Received!");
	}

	public static void insertTestData(Context ctx) throws SQLException {
		AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
		Account row = ctx.bodyAsClass(Account.class);
		dao.save(row);
	}

	public static void getById(Context ctx) throws SQLException, NoSQLResultsException {
		AccountDAO dao = new AccountDAO(ConnectionFactory.getConnection());
		Account row = dao.get(Integer.parseInt(ctx.pathParam("id")));
		ctx.json(row);
	}
}
