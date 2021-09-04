package com.bank.daos;

import com.bank.models.Account;

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
	public Account get(int id) {
		return null;
	}

	@Override
	public List<Account> getAll() throws SQLException {
		String sql = "SELECT * FROM test_table";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        List<Account> accountTableList = new ArrayList<>();

        while(rs.next()) {
            Account row = new Account();
            row.setAccountID(rs.getInt("string_id"));
            row.setName(rs.getString("string"));
            accountTableList.add(row);
        }
        return accountTableList;
	}

	@Override
	public void save(Account account) throws SQLException {
		String sql = "INSERT INTO accounts (string) VALUES (?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, account.getAccountID());

		if(pstmt.executeUpdate() > 0) {
			pstmt.getResultSet();
		}
	}

	@Override
	public void update(Account account, String[] params) {

	}

	@Override
	public void delete(Account account) {

	}
}