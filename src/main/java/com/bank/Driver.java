package com.bank;

import java.sql.Connection;

import com.bank.controllers.AccountController;
import com.bank.controllers.CustomerController;
import com.bank.controllers.NonStaticController;
import com.bank.controllers.TestController;
import com.bank.utils.ConnectionFactory;

import io.javalin.Javalin;

public class Driver {
	public static void main(String[] args) {

		Javalin app = Javalin.create().start(7000);
		Connection conn = ConnectionFactory.getConnection();
		NonStaticController nonStaticController = new NonStaticController(app, conn);
		TestController.init(app);	
		AccountController.init(app);
		CustomerController.init(app);
	}
}
