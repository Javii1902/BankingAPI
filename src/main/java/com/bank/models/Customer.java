package com.bank.models;

public class Customer {
	private String name;
	private int customerID;
	
	//@JsonIgnore
	//private ArrayList<Account> accounts = new ArrayList<>();
	
	public Customer() {

	}
	public Customer(String name,String userName, String password) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public ArrayList<Account> getAccount() {
//		return accounts;
//	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
//	public void createAccount(String name, int accountID, double balance) {
//		Account account= new Account(name, accountID, balance);
//		this.accounts.add(account);
//	}
//	public void deleteAccount(int accountID) {
//		int index = 0;
//		for(Account a:accounts) {
//			if(a.getAccountID() == accountID)
//				this.accounts.remove(index);
//		}
//	}
//
//	public void showAccounts() {
//		for (Account a:accounts) {
//			System.out.println(a.getName());
//		}
//	}
//	public void deposit(double amount, int accountID) {
//		for (Account a:accounts) {
//			if (a.getAccountID() == accountID)
//				a.setBalance(a.getBalance() + amount);
//		}
//	}
//	public void withdraw(double amount, int accountID) {
//		
//		for (Account a:accounts) {
//			if (a.getAccountID() == accountID) {
//				double temp = a.getBalance();
//				if ((temp - amount) < 0) {
//					System.out.println("That aint right bruh");
//				}
//				else {
//					a.setBalance(a.getBalance() - amount);
//				}
//			}
//		}
//	}
//	public void transfer(int firstAccount,int secondAccount, double amount) {
//		for (Account a:accounts) {
//			if (a.getAccountID() == firstAccount) {
//				withdraw(amount,firstAccount);
//				for(Account b: accounts) {
//					if(b.getAccountID()== secondAccount) {
//						deposit(amount,secondAccount);
//					}
//				}
//			}
//		}
//	}
//
//	public void showBalance(int accountID) {
//		for (Account a:accounts) {
//			if (a.getAccountID() == accountID)
//				System.out.println(a.getBalance());
//		}
//	}
//	public void showAllAccountsBalance() {
//		for (Account a:accounts) {
//			System.out.println(a.getName() + " balance: " + a.getBalance());
//		}
//	}
}

