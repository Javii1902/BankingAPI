package com.bank.daos;

import com.bank.models.Account;

import exceptions.NoSQLResultsException;

import com.bank.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AccountDAO implements Dao<Account>{
	private List<Account> accounts;
	Connection connection;

	public AccountDAO(Connection conn) {
		accounts = new LinkedList<>();
		connection = conn;
	}

	@Override
	public Account get(int id) throws SQLException, NoSQLResultsException {
		String sql = "SELECT * FROM accounts WHERE account_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			Account row = new Account();
			row.setAccountID(rs.getInt("account_id"));
			row.setBalance(rs.getDouble("balance"));
			return row;
		} else {
			throw new NoSQLResultsException("ID: " + id + " not found.");
		}
	}

	@Override
	public List<Account> getAll() throws SQLException {
		String sql = "SELECT * FROM accounts";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Account> accountTableList = new ArrayList<>();

		while(rs.next()) {
			Account row = new Account();
			row.setAccountID(rs.getInt("account_id"));
			row.setBalance(rs.getDouble("balance"));
			accountTableList.add(row);
		}
		return accountTableList;
	}

	@Override
	public void save(Account account) throws SQLException {
		String sql = "INSERT INTO accounts (balance,customer_id) VALUES (?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setDouble(1, account.getBalance());
		pstmt.setInt(2, account.getCustomerID());
		pstmt.executeUpdate();
	}

	@Override
	public void update(Account account, int id)throws SQLException, NoSQLResultsException{
		String sql = "UPDATE accounts SET balance = ? WHERE account_id = ? ";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setDouble(1, account.getBalance());
		pstmt.setInt(2, id);
		
		if(pstmt.executeUpdate() == 0) {
			throw new NoSQLResultsException("ID: " + id + " not found.");
		}
	}

	@Override
	public void delete(int id) throws SQLException,NoSQLResultsException {
		String sql = "DELETE FROM accounts WHERE account_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1,id);
		if(pstmt.executeUpdate() == 0) {
			throw new NoSQLResultsException("ID: " + id + " not found.");
		}
	}
	
	public List<Account> getAllClientAccounts(int id) throws SQLException, NoSQLResultsException {
		String sql = "SELECT * FROM accounts WHERE customer_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1,id);
		ResultSet rs = pstmt.executeQuery();

		List<Account> accountTableList = new ArrayList<>();

		while(rs.next()) {
			Account row = new Account();
			row.setAccountID(rs.getInt("account_id"));
			row.setBalance(rs.getDouble("balance"));
			row.setCustomerID(rs.getInt("customer_id"));
			accountTableList.add(row);
		}
		return accountTableList;
	}
	
	public Account getClientAccount(int accountID, int customerID) throws SQLException, NoSQLResultsException {
		String sql = "SELECT * FROM accounts WHERE account_id = ? AND customer_id = ?; ";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, accountID);
		pstmt.setInt(2, customerID);
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			Account row = new Account();
			row.setAccountID(rs.getInt("account_id"));
			row.setBalance(rs.getDouble("balance"));
			row.setCustomerID(rs.getInt("customer_id"));
			return row;
			
		} else {
			throw new NoSQLResultsException("not found.");
		}
				
	}
}