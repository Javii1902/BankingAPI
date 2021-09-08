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
	public List<Account> getAccountsInRange(int customerID, Double bottom, Double top) throws SQLException, NoSQLResultsException {
		StringBuilder sqlBuilder = new StringBuilder(String.format("SELECT * FROM accounts WHERE customer_id = %d ", customerID));
		if(bottom != null) {
			sqlBuilder.append(String.format("AND balance >= %f ", bottom));
		}
		if(top != null) {
			sqlBuilder.append(String.format("AND balance <= %f ",top));
		}
		PreparedStatement pstmt = connection.prepareStatement(sqlBuilder.toString());
		ResultSet rs = pstmt.executeQuery();
		
		List<Account> accountsInRangeList = new ArrayList<>();
		
		while(rs.next()) {
			Account row = new Account();
			row.setAccountID(rs.getInt("account_id"));
			row.setBalance(rs.getDouble("balance"));
			row.setCustomerID(rs.getInt("customer_id"));
			accountsInRangeList.add(row);
		}
		return accountsInRangeList;
	}
	public void deposit(Account account,int accountID, int customerID) throws SQLException, NoSQLResultsException{
		String sql1 = "SELECT balance FROM accounts WHERE account_id = ?";
		PreparedStatement pstmt1 = connection.prepareStatement(sql1);
		pstmt1.setInt(1,accountID);
		ResultSet rs = pstmt1.executeQuery();
		if(rs.next()) {
			Account row = new Account();
			row.setBalance(rs.getDouble("balance"));
			
			double newBal = row.getBalance() + account.getDeposit();
			
			updateClientAccount(newBal,accountID,customerID);
		}
	}
	public void withdraw(Account account,int accountID, int customerID) throws SQLException, NoSQLResultsException{
		String sql1 = "SELECT balance FROM accounts WHERE account_id = ?";
		PreparedStatement pstmt1 = connection.prepareStatement(sql1);
		pstmt1.setInt(1,accountID);
		ResultSet rs = pstmt1.executeQuery();
		if(rs.next()) {
			Account row = new Account();
			row.setBalance(rs.getDouble("balance"));
			
			double newBal = row.getBalance() - account.getWithdraw();
			
			updateClientAccount(newBal,accountID,customerID);
		}
	}
	
	public void transfer(Account account, int accountID1,int accountID2,int customerID1) throws SQLException, NoSQLResultsException {
		//withdraw from account 1
		String sql1 = "SELECT balance FROM accounts WHERE account_id = ?";
		PreparedStatement pstmt1 = connection.prepareStatement(sql1);
		pstmt1.setInt(1,accountID1);
		ResultSet rs1 = pstmt1.executeQuery();
		if(rs1.next()) {
			Account row1 = new Account();
			row1.setBalance(rs1.getDouble("balance"));
			double newBal1 = row1.getBalance() - account.getTransfer();
			updateClientAccount(newBal1,accountID1,customerID1);
		}
		
		//deposit into account 2
		String sql2 = "SELECT balance FROM accounts WHERE account_id = ?";
		PreparedStatement pstmt2 = connection.prepareStatement(sql2);
		pstmt2.setInt(1,accountID2);
		ResultSet rs2 = pstmt2.executeQuery();
		if(rs2.next()) {
			Account row2 = new Account();
			row2.setBalance(rs2.getDouble("balance"));
			double newBal2 = row2.getBalance() + account.getTransfer();
			updateClientAccount(newBal2,accountID2,customerID1);
		}
		
	}
	
	
	
	public void updateClientAccount(double balance,int accountID, int customerID) throws SQLException, NoSQLResultsException {
		String sql = "UPDATE accounts SET balance = ? WHERE account_id = ? AND customer_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setDouble(1, balance);
		pstmt.setInt(2, accountID);
		pstmt.setInt(3, customerID);
		
		if(pstmt.executeUpdate() == 0) {
			throw new NoSQLResultsException("Failed To Update");
		}		
	}
}