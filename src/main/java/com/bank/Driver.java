package com.bank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bank.controllers.NonStaticController;
import com.bank.controllers.TestController;
import com.bank.daos.AccountDAO;
import com.bank.utils.ConnectionFactory;

import io.javalin.Javalin;

public class Driver {
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(7000);
		Connection conn = ConnectionFactory.getConnection();
		NonStaticController nonStaticController = new NonStaticController(app, conn);
		TestController.init(app);
		
		AccountDAO accountDAO = new AccountDAO(conn);
		//app.get("/hello",ctx -> ctx.html("Hello, Javalin!"));
		
		
		
//		//"jdbc:mariadb://<RDS ENDPOINT FROM AWS RDS SERVICE>:<port>/<DATABASE NAME>?USER=<USER NAME>&password=<PASSWORD>"
//
//		String sql = "SELECT * FROM test_table";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//
//            System.out.println("================ test_table =================");
//            while(rs.next()) {
//                System.out.println("string_id: ["
//                        + rs.getInt("string_id")
//                        + "]   string: ["
//                        + rs.getString("string")
//                        + "]");
//            }
//            System.out.println("=============== /test_table =================");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
	}	
}
