package project;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.io.*;
import java.util.*;

import java.math.BigDecimal;


public class Admin extends Employee implements BankingInterface {
	
	public Admin(int empid, String firstName, String lastName, String username) {
		this.empid = empid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}
	
	public Admin() {
		this.empid = 0;
		this.firstName = "";
		this.lastName = "";
		this.username = "";
	}
	
	public static float round(float d, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, ROUND_HALF_UP);
	    return bd.floatValue();
	}

	//Admin method to withdraw from an account
	@Override
	public void withdraw(String adminUser) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer thisCustomer = new Customer();
		Scanner scan = new Scanner(System.in);
		String option = "";
		float oldBalance = 0;
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/customers.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			customers = (ArrayList<Customer>)in.readObject();       
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		
		System.out.println("   Enter user you are withdrawing from:");
		String username = scan.nextLine();
		
		System.out.println("   Enter withdraw amount: ");
		option = scan.nextLine();
		float with = Float.parseFloat(option);
		if(with<=0) {			//ensure value is positive
			System.out.println("\nInput invalid - negative amount");	
			adminHome(adminUser);
		}
		
		for(Customer a: customers) {
			if(a.username != null && a.username.equals(username)) {
				if(a.bankAcct.balance>=with) {
					thisCustomer = a;
					a.bankAcct.balance -= with;    //withdraw amount from balance if amount is less
				}
				else {
					System.out.println("Cannot withdraw $" + with + ". Current balance: " + round(a.bankAcct.balance,2));
					adminHome(adminUser);
				}
			}	
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
		
		System.out.println("$" + with + " withdrawn from account " + thisCustomer.bankAcct.acctNumber + "\n");
		System.out.println("    Old balance: $" + round(oldBalance,2));
		System.out.println("    New balance: $" + round(thisCustomer.bankAcct.balance,2));

		adminHome(adminUser);
	}
	
	//admin method to transfer between accounts
	@Override
	public void transfer(String fromUsername, String toUserName) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer fromCustomer = new Customer();
		Customer toCustomer = new Customer();
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
		
		System.out.println("   Enter amount to transfer: ");
		option = scan.nextLine();
		float amt = Float.parseFloat(option);
		
		for(Customer a: customers) {
			if(a.username != null && a.username.equals(fromUsername))
				if(amt<a.bankAcct.balance) {
					fromCustomer = a;             
					a.bankAcct.balance -= amt;		  //take out amount
				}
				else {
					System.out.println("Cannot withdraw $" + amt + ". Current balance: " + round(a.bankAcct.balance,2));
				}
		}
		for(Customer a: customers) {
			if(a.username != null && a.username.equals(toUserName)) {
				toCustomer = a;
				a.bankAcct.balance += amt;					//add amount to repipient's balance
			}
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
		System.out.println("\n$" + amt + " transferred from account " + fromCustomer.bankAcct.acctNumber + " to " + toCustomer.bankAcct.acctNumber + "...\n");
		System.out.println("Account " + fromCustomer.bankAcct.acctNumber + " balance: $" + round(fromCustomer.bankAcct.balance,2));
		System.out.println("Account " + toCustomer.bankAcct.acctNumber + " balance: $" + round(toCustomer.bankAcct.balance,2) + "\n");

	}

	//admin method to deposit to an account
	@Override
	public void deposit(String adminUser) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer thisCustomer = new Customer();
		Scanner scan = new Scanner(System.in);
		String option = "";
		float oldBalance = 0;
		
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
		
		System.out.println("   Enter recipient username:");
		String username = scan.nextLine();
		
		System.out.println("   Enter deposit amount: ");
		option = scan.nextLine();
		float dep = Float.parseFloat(option);
		
		for(Customer a: customers) {
			if(a.username != null && a.username.equals(username)) {
				oldBalance = a.bankAcct.balance;
				thisCustomer = a;
				a.bankAcct.balance += dep;    //add amount to balance
			}	
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
		
		System.out.println("$" + dep + " deposited to account " + thisCustomer.bankAcct.acctNumber + "\n");
		System.out.println("    Old balance: $" + round(oldBalance,2));
		System.out.println("    New balance: $" + round(thisCustomer.bankAcct.balance,2));
		adminHome(adminUser);
		
	}
	
	//allows admin to approve or deny new account applications
	public static void reviewApplications(String username) {
		Scanner scan = new Scanner(System.in);
		AcctApplication.seeNextApplication();		// print first application in the queue
		String option;
		
		System.out.println("(A)pprove/(D)eny:");
		option = scan.nextLine().toUpperCase();
		
		switch(option) {
		case "A":
			approve();
			System.out.println("Enter 'Y' to continue or 'Q' to return to home screen");
			option = scan.nextLine();
			option = option.toUpperCase();
			if(option.equals("Y"))
				reviewApplications(username);
			else
				adminHome(username);
		case "D":
			deny();
			System.out.println("Enter 'Y' to continue or 'Q' to return to home screen");
			option = scan.nextLine();
			option = option.toUpperCase();
			if(option.equals("Y"))
				reviewApplications(username);
			else
				adminHome(username);
		default:
			System.out.println("Input not valid. Enter 'A' or 'D'\n");
			reviewApplications(username);
		}
		
		System.out.println("Enter 'Y' to continue to the next application");
		option = scan.nextLine().toUpperCase();
		if(option == "Y")
			reviewApplications(username);
		else
			adminHome(username);
	
	}
	
	//removes customer object from the ArrayList (deleting their bank account)
	private static void removeCustomer(String username) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer user = new Customer();
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/customers.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			customers = (ArrayList<Customer>)in.readObject();       
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
	

		for(Customer a: customers) {
			if(a.username != null && a.username.equals(username))
				user = a;    //have to remove outside of loop to avoid exception
		}
		customers.remove(user);//remove customer from arraylist
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/customers.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(customers);         
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	//removes the username and password of a specified user to prevent login
	private static void removeUser(String username) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/users.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map<String,String>)in.readObject();       
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
	
		map.remove(username);			//remove user from the map
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/users.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(map);         
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	//removes customer and user profile from the system
	private static void deleteAccount(String adminUser, String username) {
		Scanner s = new Scanner(System.in);
		String option = "";
		System.out.println("Are you sure you want to delete this account? (Y/N)"); //no going back after this
		option = s.nextLine();
		option = option.toUpperCase();
		
		switch(option) {
		case "Y":
			removeCustomer(username);
			removeUser(username);
			System.out.println("user " + username + " deleted...");
			adminHome(username);
		case "N":
			adminHome(adminUser);
		default:
			System.out.println("Invalid input");
			adminHome(adminUser);
		}
	}
	
	//admin login page
	public static void adminLogin() {
		String userName, password;
		boolean userFound= false, passFound = false;
		Scanner scan = new Scanner(System.in);
		Map<String,String> map = new HashMap<String,String>();
		
		System.out.println("\n              Admin Login\n");
		System.out.print("Username: ");
		userName = scan.nextLine();
		System.out.print("\nPassword: ");
		password = scan.nextLine();
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/adminUsers.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map<String,String>)in.readObject();      
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
		
		if(passFound&&userFound) {				//if username and password are found, allow login
			adminHome(userName);
		} else {
			System.out.println("\n   Invalid username/password\n");
			adminLogin();
		}
	
	}
	
	public static void adminHome(String username) {
		
		Scanner scan = new Scanner(System.in);
		String option = "";
		System.out.println("  ________________________________________");
		System.out.println("\n                 Welcome!");
		System.out.println("      Please choose from the following: \n");
		System.out.println("    (1) View new account applications");
		System.out.println("    (2) Approve/Deny account applications");
		System.out.println("    (3) View all accounts");
		System.out.println("    (4) Deposit/Withdraw/Transfer from accounts");
		System.out.println("    (5) Delete an account");
		System.out.println("    (6) Logout");
		System.out.println("  ________________________________________");
		option = scan.nextLine();
		
		switch(option) {
		case "1":
			AcctApplication.seeApplications();
			adminHome(username);
		case "2":
			reviewApplications(username);
		case "3":
			viewAllAccounts();
			adminHome(username);
		case "4":
			adminTransactions(username);
			adminHome(username);
		case "5":
			System.out.println("Enter user you would like to delete:");
			option = scan.nextLine();
			deleteAccount(username, option);
		case "6":
			System.out.println("\nLogging out...\n");
			ProgramDriver.welcome();
		default:
			System.out.println("\nPlease enter a number 1-6\n");
			adminHome(username);
		}
		
	}
	
	public static void adminTransactions(String userName) {
		ArrayList<Admin> admins = new ArrayList<Admin>();
		String option;
		Scanner scan = new Scanner(System.in);
		Admin thisAdmin = new Admin();
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/admins.txt"); //read contents
			ObjectInputStream in = new ObjectInputStream(fileIn);
			admins = (ArrayList<Admin>)in.readObject();       //cast the contents into desired Object
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		
		for(Admin a: admins) {
			if(a.username != null && a.username.equals(userName))	//copy this admin to use functionality
				thisAdmin = a;
		}
		
		System.out.println("      Please choose from the following: \n");
		System.out.println("    (1) Withdraw from an account");
		System.out.println("    (2) Deposit to account");
		System.out.println("    (3) Transfer between accounts");
		System.out.println("    (4) Return to home page");
		option = scan.nextLine();
		
		switch(option) {
		case "1":
			thisAdmin.withdraw(thisAdmin.username);
		case "2":
			thisAdmin.deposit(thisAdmin.username);
		case "3":
			System.out.println("Enter user you are transferring from:");
			String fromUser = scan.nextLine();
			System.out.println("Enter user you are transferring to:");
			String toUser = scan.nextLine();
			thisAdmin.transfer(fromUser, toUser);
		case "4":
			adminHome(userName);
		default:
		}
	}

}
