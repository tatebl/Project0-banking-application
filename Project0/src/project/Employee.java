package project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Employee implements java.io.Serializable {
	int empid;
	String firstName;
	String lastName;
	String username;
	
	//constructor
	public Employee() {
		this.empid = 0;
		this.firstName = "";
		this.lastName = "";
		this.username = "";
	}
	
	//constructor
	public Employee(int empid, String firstName, String lastName, String username) {
		this.empid = empid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}
	
	//prints list of all customers and their account info
	public static void viewAllAccounts() {
		ArrayList<Customer> accounts = new ArrayList<Customer>();
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/customers.txt"); //read contents
			ObjectInputStream in = new ObjectInputStream(fileIn);
			accounts = (ArrayList<Customer>)in.readObject();       //cast the contents into desired Object
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		System.out.println(accounts);
	}
	
	// if a new application is approved, create a new customer and user
	// removes application from the queue
	public static void approve() {
		Queue<AcctApplication> q = new LinkedList<>();		//empty queue to store applications
		ArrayList<Customer> accounts = new ArrayList<Customer>();
		Map<String,String> map = new HashMap<String,String>();

		try {
			FileInputStream fileIn = new FileInputStream("./src/data/applications.txt"); //read contents
			ObjectInputStream in = new ObjectInputStream(fileIn);
			q = (Queue<AcctApplication>)in.readObject();      
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		
		AcctApplication app = q.peek();
		q.remove();				//remove the application from the queue
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/applications.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(q);         // serialize queue object to applications.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		//create new Customer and Bank Account
		int newAcctNum = (int)(Math.random()*(9999999-1111111+1)+1111111); //generate random 7 digit number
		BankAccount newBankAccount = new BankAccount(app.acctType, newAcctNum, 0, app.isJoint);
		Customer newCustomer = new Customer(app.username, app.lastName, app.firstName, app.phone, app.address, newBankAccount);

		try {
			FileInputStream fileIn = new FileInputStream("./src/data/customers.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			accounts = (ArrayList<Customer>)in.readObject();       //read in list of customers
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		accounts.add(newCustomer);		//add new customer to array list and send back to file
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/customers.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(accounts);        
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}	
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/users.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map<String,String>)in.readObject();       		//read in <user,password> map
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		
		map.put(app.username, app.password);		//add username and password to map
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/users.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(map);         // serialize map object to users.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	//if an application is denied, simply remove from the queue 
	public static void deny() {
		Queue<AcctApplication> q = new LinkedList<>();		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/applications.txt"); 
			ObjectInputStream in = new ObjectInputStream(fileIn);
			q = (Queue<AcctApplication>)in.readObject();       //read in queue of applications
			in.close();
			fileIn.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		q.remove();		//remove the application from the queue
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/applications.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(q);         // serialize queue object to applications.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
		
	//allows employees to approve/deny applications one at a time as they appear in the queue
	public static void reviewApplications() {
		Scanner scan = new Scanner(System.in);
		AcctApplication.seeNextApplication();		// print first application in the queue
		String option;
		
		System.out.println("(A)pprove/(D)eny:");
		option = scan.nextLine().toUpperCase();
		
		switch(option) {
		case "A":	//if application is approved
			approve();
			System.out.println("Enter 'Y' to continue or 'Q' to return to home screen");
			option = scan.nextLine();
			option = option.toUpperCase();
			if(option.equals("Y"))
				reviewApplications();
			else
				employeeHome();
		case "D":	//if application is denied
			deny();
			System.out.println("Enter 'Y' to continue or 'Q' to return to home screen");
			option = scan.nextLine().toUpperCase();
			if(option.equals("Y"))
				reviewApplications();
			else
				employeeHome();
		default:
			System.out.println("Input not valid. Please enter either 'A' or 'D'\n");
			reviewApplications();
		}		
	}	
	
	//employee login page
	public static void employeeLogin() {
		String userName, password;
		boolean userFound= false, passFound = false;
		Scanner scan = new Scanner(System.in);
		Map<String,String> map = new HashMap<String,String>();
		
		System.out.println("\n      Employee Login\n");
		System.out.println("Username: ");
		userName = scan.nextLine();
		System.out.println("Password:");
		password = scan.nextLine();
		
		try {
			FileInputStream fileIn = new FileInputStream("./src/data/EmployeeUsers.txt"); //read contents
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map<String,String>)in.readObject();       //read in username/password map
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
		
		if(passFound&&userFound) {		//if username and password are found, allow login
			employeeHome();
		} else {
			System.out.println("\n   Invalid username/password\n");
			employeeLogin();
		}
	}
	
	//employee home screen
	public static void employeeHome() {
		Scanner scan = new Scanner(System.in);
		String option = "";
		System.out.println("\n                 Welcome!");
		System.out.println("      Please choose from the following: \n");
		System.out.println("    (1) View new account applications");
		System.out.println("    (2) Approve/Deny account applications");
		System.out.println("    (3) View all accounts");
		System.out.println("    (4) Logout");
		option = scan.nextLine();
		
		switch(option) {
		case "1":
			AcctApplication.seeApplications();
			employeeHome();
		case "2":
			reviewApplications();
		case "3":
			viewAllAccounts();
			employeeHome();
		case "4":
			System.out.println("Logging out...");
			System.exit(0);
		default:
			//if not a valid number
			System.out.println("\nPlease enter a number 1-4\n");
			employeeHome();
		}
	}
}
