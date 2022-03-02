package project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin extends Employee implements BankingInterface {

	@Override
	public void withdraw(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transfer(String fromUsername, String toUserName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deposit(String username) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer thisCustomer = new Customer();
		Scanner scan = new Scanner(System.in);
		String option = "";
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/customers.txt"); //read contents
			ObjectInputStream in = new ObjectInputStream(fileIn);
			customers = (ArrayList<Customer>)in.readObject();       //cast the contents into desired Object
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		
		System.out.println("   Enter deposit amount: ");
		option = scan.nextLine();
		int dep = Integer.parseInt(option);
		
		for(Customer a: customers) {
			if(a.username != null && a.username.equals(username))
				a.bankAcct.balance += dep;    //add amount to balance
		}
				
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/customers.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(customers);         // serialize queue object to applications.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("$" + dep + " deposited...\n");
		adminHome();
		
	}
	
	private void deleteAccount(String username) {
		
	}
	
	public static void adminLogin() {
		String userName, password;
		boolean userFound= false, passFound = false;
		Scanner scan = new Scanner(System.in);
		Map<String,String> map = new HashMap<String,String>();
		
		System.out.println("\n       Admin Login\n");
		System.out.println("Username: ");
		userName = scan.nextLine();
		System.out.println("Password:");
		password = scan.nextLine();
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/adminUsers.txt"); //read contents
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map<String,String>)in.readObject();       //cast the contents into desired Object
			in.close();
			fileIn.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch(ClassNotFoundException e1) {
			e1.printStackTrace();
			}
		
		if(map.containsKey(userName))
			userFound = true;
		if(map.containsValue(password))
			passFound = true;
		
		if(passFound&&userFound) {
			adminHome();
		} else {
			System.out.println("\n   Invalid username/password\n");
			adminLogin();
		}
	
	}
	
	public static void adminHome() {
		Scanner scan = new Scanner(System.in);
		String option = "";
		System.out.println("\n                 Welcome!");
		System.out.println("      Please choose from the following: \n");
		System.out.println("    (1) View new account applications");
		System.out.println("    (2) Approve/Deny account applications");
		System.out.println("    (3) View all accounts");
		System.out.println("    (4) Deposit/Withdraw/Transfer from accounts");
		System.out.println("    (5) Delete an account");
		System.out.println("    (4) Logout");
		option = scan.nextLine();
		
		switch(option) {
		case "1":
			AcctApplication.seeApplications();
			adminHome();
		case "2":
			reviewApplications();
		case "3":
			viewAllAccounts();
			adminHome();
		case "4":
			adminTransactions();
		case "5":
			deleteAccount();
		case "6":
			System.exit(0);
			System.out.println("Logging out...");
		default:
			//if not a valid number
			//have welcome() recursively call itself if it makes it past
			//throw new InvalidChoiceException("Option invalid");
			System.out.println("\nPlease enter a number 1-2\n");
			adminHome();
		}
		
	}
	
	public static void adminTransactions() {
		String option;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("      Please choose from the following: \n");
		System.out.println("    (1) Withdraw from an account");
		System.out.println("    (2) Deposit to account");
		System.out.println("    (3) Transfer between accounts");
		option = scan.nextLine();
		
		
		switch(option) {
		case "1":
			
			//withdraw
		case "2":
			System.out.println("Enter repipient username: ");
			option = scan.nextLine();
			deposit(option);
		case "3":
			//transfer
		default:
		}
	}
	
	
}
