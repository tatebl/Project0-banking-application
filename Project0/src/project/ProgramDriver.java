package project;

import java.io.*;
import java.util.*;

/*
	Logins for testing:
	* Employee- susan1 pass- susan1
	* Admin- john123 pass- john12
*/

public class ProgramDriver {
	
	public static void main(String[] args) {
		
		welcome();			//show welcome page and return which type of login
		
		
	}

	//Welcome screen to allow users to choose login page
	public static void welcome()  {			
		Scanner s = new Scanner(System.in);
		int a = 0;
		
		System.out.println("  	        Welcome! ");
		System.out.println("  Please select the correct login page:\n");
		System.out.println("    (1) Customer");
		System.out.println("    (2) Employee");
		System.out.println("    (3) Admin");
		String option = s.nextLine();
		
		switch(option) {
			case "1":
				Customer.customerWelcome();
			case "2":
				Employee.employeeLogin();
			case "3":
				Admin.adminLogin();
			default:
				//if not a valid number
				System.out.println("\nPlease enter a number 1-3\n");
				welcome();
		}
	}
}
	
	
	
