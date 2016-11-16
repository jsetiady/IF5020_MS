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
		int duration;
		boolean invalidInput;
		
		System.out.println("CREATE MEETING");
		System.out.println("-----------------------------");
		
		System.out.print("# Title\t\t\t: ");
		title = s.nextLine();
		
		System.out.print("# Agenda\t\t: ");
		agenda = s.nextLine();
		
		System.out.print("# Location\t\t: ");
		location = s.nextLine();
		
		do {
		    System.out.print("# Duration (hours) (1-8) \t:");
		    while (!s.hasNextInt()) {
		        System.out.println("Please enter a Positive Integer value");
		        s.next(); // this is important!
		    }
		    duration = s.nextInt();
		} while (duration <= 0);
		System.out.println("Thank you! Got " + duration);
		
		/*
		do {
			invalidInput = false;
			try {
				System.out.print("# Duration (hours)\t: ");
				duration = s.nextInt();
				s.nextLine();
			} catch(Exception e) {
				System.out.println("Invalid input. Please enter an Integer value");
				invalidInput = true;
			}
		} while(invalidInput);
		*/
		
		System.out.println("# Proposed date range");
		System.out.print("    Start date\t\t: ");
		strStartDate = s.nextLine();
		
		System.out.print("    End date\t\t: ");
		strEndDate = s.nextLine();
		
		System.out.println("# Max response time");
		System.out.print("    Date (dd/mm/yy)\t: ");
		strMaxResponseDate = s.nextLine();
		
		System.out.print("    Time (hh:mm)\t: ");
		strMaxResponseTime = s.nextLine();
		
	}
}
