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
	public void createAccount(String name, int accountID, double balance) {
		Account account= new Account(name, accountID, balance);
		this.accounts.add(account);
	}
	public void deleteAccount(int accountID) {
		int index = 0;
		for(Account a:accounts) {
			if(a.getAccountID() == accountID)
				this.accounts.remove(index);
		}
	}

	public void showAccounts() {
		for (Account a:accounts) {
			System.out.println(a.getName());
		}
	}
	public void deposit(double amount, int accountID) {
		for (Account a:accounts) {
			if (a.getAccountID() == accountID)
				a.setBalance(a.getBalance() + amount);
		}
	}
	public void withdraw(double amount, int accountID) {
		
		for (Account a:accounts) {
			if (a.getAccountID() == accountID) {
				double temp = a.getBalance();
				if ((temp - amount) < 0) {
					System.out.println("That aint right bruh");
				}
				else {
					a.setBalance(a.getBalance() - amount);
				}
			}
		}
	}
	public void transfer(int firstAccount,int secondAccount, double amount) {
		for (Account a:accounts) {
			if (a.getAccountID() == firstAccount) {
				withdraw(amount,firstAccount);
				for(Account b: accounts) {
					if(b.getAccountID()== secondAccount) {
						deposit(amount,secondAccount);
					}
				}
			}
		}
	}

	public void showBalance(int accountID) {
		for (Account a:accounts) {
			if (a.getAccountID() == accountID)
				System.out.println(a.getBalance());
		}
	}
	public void showAllAccountsBalance() {
		for (Account a:accounts) {
			System.out.println(a.getName() + " balance: " + a.getBalance());
		}
	}
}

