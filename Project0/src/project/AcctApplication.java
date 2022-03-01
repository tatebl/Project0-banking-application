package project;

import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;

public class AcctApplication implements java.io.Serializable {
	String acctType;      //checking or savings
	boolean isJoint;
	String firstName;
	String lastName;
	String address;
	String phone;
	String username;
	String password;
	
	public AcctApplication() {
		this.acctType = "";
		this.isJoint = false;
		this.firstName = "";
		this.lastName = "";
		this.phone = "";
		this.address = "";
		this.username = "";
		this.password = "";
	}
	
	//overloaded constructor
	public AcctApplication(String acctType, boolean isJoint, String firstName, String lastName, String address, String phone, String username, 
			String password) {
		this.acctType = acctType;
		this.isJoint = isJoint;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
	  return getClass().getSimpleName() + ":[name= " + firstName + " "  + lastName + "] \n"
	  		+ "[Acount type= " + acctType + "]\n"
	  		+ "[Joint= " + isJoint + "]\n"
	  		+ "[Address= " + address + "]\n"
	  		+ "[Phone= " + phone + "]\n"
	  		+ "[Username= " + username + "]\n";
	}
	
	
	public static void seeApplications() {
		Queue<AcctApplication> q = new LinkedList<>();		//empty queue to store applications
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
		System.out.println(q);		//print queue of applications
	}
	
	public static void seeNextApplication() {
		Queue<AcctApplication> q = new LinkedList<>();		//empty queue to store applications
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
		System.out.println(q.peek());		//print first application in the queue
	}
	
	
	
}
