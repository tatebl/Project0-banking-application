package project;

import java.io.*;
import java.util.*;


public class ProgramDriver {
	
	public static void main(String[] args) {
		/*ArrayList<Admin> a = new ArrayList<Admin>();	
		a.add(new Admin(1122, "John", "Lunsford", "john123"));
		a.add(new Admin(1423, "Ashley", "Watson", "ashley12"));
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/admins.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(a);         
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("ashley12", "pass123");
		map.put("john123", "john12");
		
		try {
			FileOutputStream fileOut = new FileOutputStream("./src/data/adminUsers.txt"); 
			ObjectOutputStream out = new ObjectOutputStream(fileOut);  
			out.writeObject(map);         
			out.close();
			fileOut.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}*/
		
		int choice = welcome();			//show welcome page and return which type of login
			
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
	}

	//Welcome screen to allow users to choose login page
	private static int welcome()  {			
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
				System.out.println("\nPlease enter a number 1-3\n");
				a = welcome();
		}
		return a;
	}
}
	
	
	
