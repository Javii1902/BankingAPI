package com.bank.models;

public class Account {
	private String name;
	private int accountID;
	private String accountType;
	private double balance;

	public Account() {

	}
	public Account(String name, int accountID, String accountType, double balance) {
		this.name = name;
		this.accountID = accountID;
		this.accountType = accountType;
		this.balance = balance;
	}
	public Account(String name,int accountID, double balance) {
		this.accountID = accountID;
		this.name = name;
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void deposit(double deposit) {
		balance = balance + deposit;
	}

}
