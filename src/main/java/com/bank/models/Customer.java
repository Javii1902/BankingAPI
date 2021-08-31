package com.bank.models;

import java.util.ArrayList;

public class Customer {
	private String name;
	private ArrayList<Account> accounts = new ArrayList<>();
	private int customerID;
	private String userName;
	private String password;

	public Customer() {

	}
	public Customer(String name,String userName, String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Account> getAccount() {
		return accounts;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void newAccount() {
		Account account = new Account();
		this.accounts.add(account);
	}
	public void newAccount(String name, int accountID, double balance) {
		Account account= new Account(name, accountID, balance);
		this.accounts.add(account);
	}
	public void showAccounts() {
		for (Account a:accounts) {
			System.out.println(a.getName());
		}
	}
	public void addMoney(int accountID,double amount) {
		for(Account a: accounts) {
			if(a.getAccountID() == accountID) {
				a.deposit(amount);
			}
		}
	}

}

