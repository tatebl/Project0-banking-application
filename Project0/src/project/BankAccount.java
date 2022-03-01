package project;

import java.io.Serializable;

public class BankAccount implements java.io.Serializable {
	String type;
	int acctNumber;
	float balance;
	boolean isJoint;
	
	
	public BankAccount() {
		this.type = "";
		this.acctNumber = 0;
		this.balance = 0;
		this.isJoint = false;
	}
	
	
	public BankAccount(BankAccount bank) {
		this.type = bank.type;
		this.acctNumber = bank.acctNumber;
		this.balance = bank.balance;
		this.isJoint = bank.isJoint;
	}
	
	public BankAccount(String type, int acctNumber, float balance, boolean isJoint) {
		this.type = type;
		this.acctNumber = acctNumber;
		this.balance = balance;
		this.isJoint = isJoint;
	}
	
	
}
