package project;

import java.io.*;
import java.util.*;
import exceptions.*;

public class ProgramDriver {
	
	public static void main(String[] args) {
		
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
	}

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
				System.out.println("\nPlease enter a number 1-3\n");
				a = welcome();
		}
		return a;
	}
}
	
	
	
