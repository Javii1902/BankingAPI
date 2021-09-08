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

public class CustomerController {
	private static Javalin javalin;

	public static void init(Javalin app) {
		javalin = app;
		//all get
		app.get("/clients/test", CustomerController::testConnection);
		app.get("/clients", CustomerController::getAll);
		app.get("/clients/:id", CustomerController::getClientByID);
		
		//all post
		app.post("/clients", CustomerController::insertNewCustomer);

		//all put
		app.put("/clients/:id", CustomerController::updateCustomer);

		//delete
		app.delete("/clients/:id", CustomerController::deleteCustomer);

	}

	public static void testConnection(Context ctx) {
		ctx.status(200);
		ctx.result("Test Customer Received!");
	}

	public static void insertNewCustomer(Context ctx) throws SQLException {
		CustomerDAO dao = new CustomerDAO(ConnectionFactory.getConnection());
		Customer row = ctx.bodyAsClass(Customer.class);
		dao.save(row);    
		ctx.status(201);
	}

	public static void getAll(Context ctx) throws SQLException {
		CustomerDAO dao = new CustomerDAO(ConnectionFactory.getConnection());
		List<Customer> allCustomers = dao.getAll();
		ctx.json(allCustomers);
	}

	public static void getClientByID(Context ctx) throws SQLException {
		try {
			CustomerDAO dao = new CustomerDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("id"));
			Customer customer = dao.get(id);
			ctx.json(customer);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("No Customer Found");
		}
	}
	public static void updateCustomer(Context ctx) throws SQLException {
		try {
			CustomerDAO dao = new CustomerDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("id"));
			Customer row = ctx.bodyAsClass(Customer.class);
			dao.update(row,id);
		}catch(NoSQLResultsException e){
			ctx.status(404);
			ctx.result("Customer not found");
		}   	
	}
	public static void deleteCustomer(Context ctx) throws SQLException{
		try {
			CustomerDAO dao = new CustomerDAO(ConnectionFactory.getConnection());
			int id = Integer.parseInt(ctx.pathParam("id"));
			dao.delete(id);
			ctx.status(205);
		}catch(NoSQLResultsException e) {
			ctx.status(404);
			ctx.result("Customer not found");
		}
	} 
}
