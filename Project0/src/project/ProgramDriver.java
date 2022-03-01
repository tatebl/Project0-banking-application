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
		/*ArrayList<Employee> emps = new ArrayList<Employee>();
		
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("susan1", "susan1");
		map.put("mattyice", "mattyice");
		map.put("sean1", "sean1");
		map.put("marty1", "marty1");
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/employeeUsers.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(map);         // serialize arrayList object to bankAccounts.txt
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		*/
		
		Employee.employeeLogin();
		
		
		
		
		//Customer.customerWelcome();
		
		//Employee.employeeHome();
		
		
		//AcctApplication.seeNextApplication();
		
		/*
		int choice = welcome();
		//int choice2 = 0;
		
		switch(choice) {
		case 1:
			Customer.customerWelcome();
		//case 2:
			Employee.employeeLogin();
		//case 3:
			//return 3;
		default:
			break;
		}
		*/
		
		System.out.println("Done");
		
	}

}
