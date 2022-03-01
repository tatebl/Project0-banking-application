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
		
		public Customer(String username, String lastName, String firstName, String phone, String address, BankAccount acct) {
			this.username = username;
			this.firstName= firstName;
			this.lastName = lastName;
			this.phone = phone;
			this.address = address;
			this.bankAcct = acct;		//had to make copy constructor
		}
		
		
		@Override
		public String toString() {
		  return getClass().getSimpleName() + ":[name= " + firstName + " "  + lastName + "] \n"
		  		+ "[Username= " + username + "]\n"
		  		+ "[Address= " + address + "]\n"
		  		+ "[Phone= " + phone + "]\n"
		  		+ "[Account Number= " + bankAcct.acctNumber + "]\n"
		  		+ "[Joint= " + bankAcct.isJoint + "]\n"
		  		+ "[Balance= " + bankAcct.balance + "]\n";
		}
		
		
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
					//if not a valid number
					//have welcome() recursively call itself if it makes it past
					//throw new InvalidChoiceException("Option invalid");
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
				
				FileInputStream fileIn = new FileInputStream("./src/data/applications.txt"); //read contents
				ObjectInputStream in = new ObjectInputStream(fileIn);
				q = (Queue<AcctApplication>)in.readObject();       //cast the contents into desired Object
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
			
			System.out.println("Thank you! You will hear back shortly if we approve/deny your application.");
		}
		
		
		
		private static void customerLogin() {
			System.out.println("\n    Customer Login\n");
			String userName, password;
			Scanner scan = new Scanner(System.in);
			System.out.println("Username: ");
			userName = scan.nextLine();
			
			System.out.println("Password:");
			password = scan.nextLine();
			
			
			
			/*
			 * 
			 *   need to check credentials to allow login
			 *   customerHome(userName);
			 */
		}
		
		private static void customerHome(String userName) {
			Scanner scan = new Scanner(System.in);
			String option = "";
			
			System.out.println("\n      Please choose from the following: \n");
			System.out.println("    (1) Make a deposit");
			System.out.println("    (2) Make a withdrawal");
			System.out.println("    (3) Make a transfer");
			System.out.println("    (4) Logout");
			option = scan.nextLine();
			
			switch(option) {
			case "1":
				//deposit();
			case "2":
				//withdraw();
			case "3":
				//transfer();
			case "4":
				System.out.println("Logging out...");
				System.exit(0);
			default:
				//if not a valid number
				//have welcome() recursively call itself if it makes it past
				//throw new InvalidChoiceException("Option invalid");
				System.out.println("\nPlease enter a number 1-2\n");
				customerHome(userName);
			}
		}


		@Override
		public void deposit() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void withdraw() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void transfer() {
			// TODO Auto-generated method stub
			
		}
	
}
