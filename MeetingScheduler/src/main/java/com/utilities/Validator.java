package com.utilities;

import java.util.Scanner;

public class Validator {
	
	public String getAndValidateInput(Scanner s, String label, String type) {
		int test;
		String input, regex = ".", errorMsg = ".";
		
		if(type.equals("date")) { //format: dd/mm/yyyy
			regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
			errorMsg = "Invalid date format. Please re-enter.";
		}
		else if(type.equals("duration")) { //positive integer between 1-8
			regex = "^[1-9]+$";
			errorMsg = "Please enter a value between 1-9";
		}
		else if(type.equals("time")) { //format: hh:mm
			regex = "[0-9]{2}:[0-9]{2}";
			errorMsg = "Invalid time format. Please re-enter.";
		}
		else if(type.equals("YN")) {
			regex = "[NYny]{1}";
			errorMsg = "Please enter either Y or N character";
		}
		else if(type.equals("text")) {
			regex = "^(?!\\s*$).+";
			errorMsg = "This field cannot be empty";
		}
		else if(type.equals("number")) {
			regex = "[0-9]";
			errorMsg = "Invalid time format. Please re-enter.";
		}
		else if(type.equals("email")) {
			regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			errorMsg = "Invalid email format, please re-enter";
		}
		
		do {
			test = 1;
			System.out.print(label);
			input = s.nextLine();
			if (!input.matches(regex)) {
				System.out.println(errorMsg);
				test = 0;
			}
		} while (test == 0);
		return input;
	}
}
