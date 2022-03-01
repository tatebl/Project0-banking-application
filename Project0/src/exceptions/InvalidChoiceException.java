package exceptions;

/*
 * 	Thrown when the user enters an invalid option when prompted
 * 
 */

public class InvalidChoiceException extends Exception {
	
	public InvalidChoiceException(String errorMessage) {			
		super(errorMessage);	
	}

}
