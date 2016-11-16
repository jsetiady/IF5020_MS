package com.view.meeting;

import java.util.Scanner;

/**
 * @author jessiesetiady
 *
 */
public class MeetingView {
	public void displayCreateMeetingView() {
		//variables
		Scanner s = new Scanner(System.in);
		String title, agenda, location, strStartDate, 
			 strEndDate, strMaxResponseDate, strMaxResponseTime;
		
		int duration, input;
		boolean invalidInput;
		
		System.out.println("CREATE MEETING");
		System.out.println("--------------------------------");
		
		System.out.print("# Title\t\t\t\t: ");
		title = s.nextLine();
		
		System.out.print("# Agenda\t\t\t: ");
		agenda = s.nextLine();
		
		System.out.print("# Location\t\t\t: ");
		location = s.nextLine();
		
		duration = Integer.parseInt(getAndValidateInput(s, "# Duration (hours) (1-8)\t: ", "duration"));
		System.out.println("dur: " + duration);
		System.out.println("# Proposed date range");
		strStartDate = getAndValidateInput(s, "    Start date\t\t\t: ", "date");
		strEndDate = getAndValidateInput(s, "    End date\t\t\t: ", "date");
		
		System.out.println("# Max response time");
		strMaxResponseDate = getAndValidateInput(s, "    Date\t\t\t: ", "date");
		
		System.out.print("    Time (hh:mm)\t\t: ");
		strMaxResponseTime = s.nextLine();
		
	}
	
	public String getAndValidateInput(Scanner s, String label, String type) {
		int test;
		String input, regex = ".", errorMsg = ".";
		
		if(type.equals("date")) {
			regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
			errorMsg = "Invalid date format. Please re-enter.";
		} else if(type.equals("duration")) { //positive integer between 1-8
			regex = "^[1-8]+$";
			errorMsg = "Please enter a value between 1-8";
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
