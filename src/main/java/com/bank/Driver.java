package com.bank;

import com.bank.models.Customer;
import com.bank.models.Account;

public class Driver {
	public static void main(String[] args) {
		Customer custom = new Customer("Juan Garcia", "Jgar","123qwer");
		custom.newAccount("My checking", 1, 123);
		custom.newAccount("My checking12", 2, 562);
		custom.newAccount("My checking1234", 3, 633);
		custom.showAccounts();
		System.out.print("balance " );
		custom.showBalance(1);
		custom.deposit(100,1);
		custom.showBalance(1);
		custom.withdraw(50.52,1);
		custom.showBalance(1);
	}
}
