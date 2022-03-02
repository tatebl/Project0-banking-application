package project;

import java.io.*;
import java.util.*;
import exceptions.*;

public class ProgramDriver {
	
	private static int welcome()  {			//throws InvalidChoiceException
		Scanner s = new Scanner(System.in);
		int a = 0;
		
		System.out.println("  	     Welcome! ");
		System.out.println("Please select the correct login page:\n");
		System.out.println("    (1) Customer");
		System.out.println("    (2) Employee");
		System.out.println("    (3) Admin");
		
		
		String option = s.nextLine();
		
		switch(option) {
			case "1":
				return 1;
			case "2":
				return 2;
			case "3":
				return 3;
			default:
				//if not a valid number
				//have welcome() recursively call itself if it makes it past
				//throw new InvalidChoiceException("Option invalid");
				System.out.println("\nPlease enter a number 1-3\n");
				a = welcome();
		}
		return a;
	}

	
	
	public static void main(String[] args) {
		//ArrayList<Admin> emps = new ArrayList<Admin>();
		
		/*try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/admins.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(emps);         // serialize arrayList object to bankAccounts.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}*/
		
		/*emps.add(new Admin(134,"John","Smith", "john123"));
		emps.add(new Admin(136,"Ashley","Wilson", "ashley1"));
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/admins.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(emps);         // serialize arrayList object to bankAccounts.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}*/
		
		
		
		//Customer.customerWelcome();
		
		//Employee.employeeHome();
		
		
		//AcctApplication.seeNextApplication();
		
		
		int choice = welcome();
		
		
		switch(choice) {
		case 1:
			Customer.customerWelcome();
		case 2:
			Employee.employeeLogin();
		case 3:
			Admin.adminLogin();
		default:
			break;
		}
		
		
		System.out.println("Done");
		
	}

}
