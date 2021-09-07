package com.bank.daos;

import com.bank.models.Customer;

import exceptions.NoSQLResultsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements Dao<Customer>{

	private List<Customer> customers;
	Connection connection;

	public CustomerDAO(Connection conn) {
		connection = conn;
	}

	@Override
	public Customer get(int id) throws SQLException, NoSQLResultsException {
		String sql = "Select * FROM customers WHERE customer_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			Customer row = new Customer();
			row.setCustomerID(rs.getInt("customer_id"));
			row.setName(rs.getString("name"));
			return row;
		} else {
			throw new NoSQLResultsException("ID: " + id + " not found.");
		}
	}

	@Override
	public List<Customer> getAll() throws SQLException {
		String sql = "SELECT * FROM customers";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Customer> customerList = new ArrayList<>();

		while(rs.next()) {
			Customer row = new Customer();
			row.setCustomerID(rs.getInt("customer_id"));
			row.setName(rs.getString("name"));
			customerList.add(row);
		}
		return customerList;
	}

	@Override
	public void save(Customer customer) throws SQLException {
		String sql = "INSERT INTO customers (name) VALUES (?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1,customer.getName());
		pstmt.executeUpdate();
	}

	@Override
	public void update(Customer customer, int id) throws SQLException, NoSQLResultsException{
		String sql = "UPDATE customers SET name = ? WHERE customer_id = ? ";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, customer.getName());
		pstmt.setInt(2, id);
		
		if(pstmt.executeUpdate() == 0) {
			throw new NoSQLResultsException("ID: " + id + " not found.");
		}
	}

	@Override
	public void delete(int id) throws SQLException,NoSQLResultsException {
		String sql = "DELETE FROM customers WHERE customer_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1,id);
		if(pstmt.executeUpdate() == 0) {
			throw new NoSQLResultsException("ID: " + id + " not found.");
		}
	}
}
