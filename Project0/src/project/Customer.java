package project;

import java.io.*;
import java.util.*;

public class Customer implements java.io.Serializable, BankingInterface {

		String username;
		String lastName;
		String firstName;
		String address;
		String phone;
		BankAccount bankAcct;
		
		public Customer() {
			this.username = "";
			this.lastName = "";
			this.firstName = "";
			this.address = "";
			this.phone = "";
			bankAcct = new BankAccount();
		}
		
		public Customer(String username, String lastName, String firstName, String phone, String address, BankAccount acct) {
			this.username = username;
			this.firstName= firstName;
			this.lastName = lastName;
			this.phone = phone;
			this.address = address;
			this.bankAcct = acct;	
		}
		
		//allows us to print out all info for a Customer
		@Override
		public String toString() {
		  return "\n" + getClass().getSimpleName() + ":    " + firstName + " " + lastName +  "\n"
		  		+ "Username:    " + username + "\n"
		  		+ "Address:     " + address + "\n"
		  		+ "Phone:       " + phone + "\n"
		  		+ "Account No:  " + bankAcct.acctNumber + "\n"
		  		+ "Joint:       " + bankAcct.isJoint + "\n"
		  		+ "Balance:     " + bankAcct.balance + "\n";
		}
		
		//customer starting screen
		public static void customerWelcome() {
			Scanner s = new Scanner(System.in);
			
			System.out.println("\n      Customer Login\n     Would you like to:\n");
			System.out.println("    (1) Apply for a new bank account");
			System.out.println("    (2) Login");
			
			String option = s.nextLine();
			
			switch(option) {
				case "1":
					makeNewApplication();
				case "2":
					customerLogin();
				default:
					System.out.println("\nPlease enter a number 1-2\n");
					customerWelcome();
			}
		}
		
		
		static void makeNewApplication() {
			Scanner scan = new Scanner(System.in);
			AcctApplication myApp = new AcctApplication();			//create a new application object
			String type = "";
			Queue<AcctApplication> q = new LinkedList<>();		//empty queue to store applications
			
			System.out.println("     New Account Registration\n");
			System.out.println("Enter first name:");
			myApp.firstName = scan.nextLine();
			System.out.println("Enter last name:");
			myApp.lastName = scan.nextLine();
			System.out.println("Do you want to open a Checking (1) or Savings (2)?");
			type = scan.nextLine();
			if(type.equals("1")) {
				myApp.acctType = "checking";
			} else
				myApp.acctType = "savings";
			System.out.println("Is this a joint account? (Y/N):");
			type = scan.nextLine();
			if(type.toUpperCase()=="Y") 		//this is set to false by default
				myApp.isJoint = true;
			System.out.println("Enter mailing address:");
			myApp.address = scan.nextLine();
			System.out.println("Enter phone number:");
			myApp.phone = scan.nextLine();
			System.out.println("Enter a unique username:");
			myApp.username = scan.nextLine();
			System.out.println("Enter a password:");
			myApp.password = scan.nextLine();
		
			//grab queue of applications and add, send back to file	
			try {
				FileInputStream fileIn = new FileInputStream("./src/data/applications.txt"); 
				ObjectInputStream in = new ObjectInputStream(fileIn);
				q = (Queue<AcctApplication>)in.readObject();       
				in.close();
				fileIn.close();
				}catch(IOException ex) {
					ex.printStackTrace();
				}catch(ClassNotFoundException e1) {
					e1.printStackTrace();
				}	
			q.add(myApp);		//add newly created AcctApplication to the queue to be reviewed by admin
			try {
				FileOutputStream fileOut = new FileOutputStream("./src/data/applications.txt"); 
				ObjectOutputStream out = new ObjectOutputStream(fileOut);  
				out.writeObject(q);         // serialize queue object to applications.txt
				out.close();
				fileOut.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
			System.out.println("\nThank you! You will hear back shortly if we approve/deny your application.");
			System.exit(0);		//quit application after submitting
		}
		
		
		//customer login screen
		public static void customerLogin() {
			String userName, password;
			boolean userFound= false, passFound = false;
			Scanner scan = new Scanner(System.in);
			Map<String,String> map = new HashMap<String,String>();
			
			System.out.println("\n      Customer Login\n");
			System.out.println("Username: ");
			userName = scan.nextLine();
			System.out.println("Password:");
			password = scan.nextLine();
			
			try {
				FileInputStream fileIn = new FileInputStream("./src/data/users.txt"); //read username/pass file
				ObjectInputStream in = new ObjectInputStream(fileIn);
				map = (Map<String,String>)in.readObject();       //read in map of username,password
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
			
			if(passFound&&userFound) {			//if username and password are found, allow access to home screen
				customerHome(userName);
			} else {
				System.out.println("\n   Invalid username/password\n");
				customerLogin();
			}
		}
		
		//customer home screen
		private static void customerHome(String userName) {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			Customer thisCustomer = new Customer();
			Scanner scan = new Scanner(System.in);
			String option = "";
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
				if(a.username != null && a.username.equals(userName))
					thisCustomer = a;		//copy over customer object to utilize variables
			}
			
			//print account info for customer
			System.out.println("\n\t" + thisCustomer.firstName + " " + thisCustomer.lastName);
			System.out.println("\t" + thisCustomer.bankAcct.acctNumber + "\t" + thisCustomer.bankAcct.type);
			System.out.println("\tBalance: $" + thisCustomer.bankAcct.balance);
		
			System.out.println("\n      Please choose from the following: \n");
			System.out.println("    (1) Make a deposit");
			System.out.println("    (2) Make a withdrawal");
			System.out.println("    (3) Make a transfer");
			System.out.println("    (4) Logout");
			option = scan.nextLine();
			
			switch(option) {
			case "1":
				thisCustomer.deposit(userName);
			case "2":
				thisCustomer.withdraw(userName);
			case "3":
				System.out.println("Enter the username you would like to transfer to: ");
				option = scan.nextLine();
				thisCustomer.transfer(userName, option);
			case "4":
				System.out.println("Logging out...");
				System.exit(0);
			default:
				System.out.println("\nPlease enter a number 1-4\n");
				customerHome(userName);
			}
		}

		//method to deposit into personal account
		@Override
		public void deposit(String username) {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			Customer thisCustomer = new Customer();
			Scanner scan = new Scanner(System.in);
			String option = "";
			
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
			
			System.out.println("   Enter deposit amount: ");
			option = scan.nextLine();					
			int dep = Integer.parseInt(option);
			
			if(dep<=0) {
				System.out.println("\nInput invalid - negative amount");
				customerHome(username);
			}
			
			for(Customer a: customers) {
				if(a.username != null && a.username.equals(username))		//find the correct customer
					a.bankAcct.balance += dep;    //add amount to balance
			}
					
			try {
				FileOutputStream fileOut = new FileOutputStream("./src/data/customers.txt"); 
				ObjectOutputStream out = new ObjectOutputStream(fileOut);  
				out.writeObject(customers);         
				out.close();
				fileOut.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
			
			System.out.println("$" + dep + " deposited...\n");
			customerHome(username);
		}

		//method to withdraw, will prompt and return to home screen if amount is greater than your balance
		//or if a negative is entered
		@Override
		public void withdraw(String username) {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			Customer thisCustomer = new Customer();
			Scanner scan = new Scanner(System.in);
			String option = "";
			
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
			
			System.out.println("   Enter amount to withdraw: ");
			option = scan.nextLine();
			int with = Integer.parseInt(option);
			
			if(with<=0) {
				System.out.println("\nInput invalid - negative amount");	//validate
				customerHome(username);
			}
			
			for(Customer a: customers) {
				if(a.username != null && a.username.equals(username)) {
					if(a.bankAcct.balance>=with)
						a.bankAcct.balance -= with;    //withdraw amount from balance
					else {
						System.out.println("Cannot withdraw $" + with + ". Current balance: " + a.bankAcct.balance);
						customerHome(username);
					}
				}	
			}
					
			try {
				FileOutputStream fileOut = new FileOutputStream("./src/data/customers.txt"); 
				ObjectOutputStream out = new ObjectOutputStream(fileOut);  
				out.writeObject(customers);         
				out.close();
				fileOut.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
			
			System.out.println("\n$" + with + " withdrawn...\n");
			customerHome(username);
		}

		//allows customer to transfer to another user
		//validates non-negative number and amount is less that fromUser balance
		@Override
		public void transfer(String fromUser, String toUser) {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			Customer thisCustomer = new Customer();
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
			int amt = Integer.parseInt(option);
			
			if(amt<=0) {
				System.out.println("\nInput invalid - negative amount");
				customerHome(fromUser);
			}
			
			for(Customer a: customers) {
				if(a.username != null && a.username.equals(fromUser))
					if(amt<=a.bankAcct.balance) 
						a.bankAcct.balance -= amt;							//take out amount
					else {
						System.out.println("Cannot withdraw $" + amt + ". Current balance: " + a.bankAcct.balance);
						customerHome(fromUser);
					}
			}
			for(Customer a: customers) {
				if(a.username != null && a.username.equals(toUser)) 
					a.bankAcct.balance += amt;					//add amount to repipient's balance
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
			
			System.out.println("\n$" + amt + " transferred to " + toUser + "...\n");
			customerHome(fromUser);
		}

}
