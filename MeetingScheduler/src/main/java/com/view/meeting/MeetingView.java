package com.view.meeting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.controller.meeting.MeetingController;

/**
 * @author jessiesetiady
 *
 */
public class MeetingView {
	
	MeetingController mc = new MeetingController();
	
	
	public void createMeetingView() {
		//variables
		Scanner s = new Scanner(System.in);
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

		String title, agenda, location, strStartDate = null, 
			 strEndDate = null, strMaxResponseDate, strMaxResponseTime;
		
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
		
		//TODO @jeje Change lower and upper date
		try {
			strStartDate = getAndValidateInput(s, "    Start date (dd/mm/yyyy)\t: ", "date", format.parse("17/11/2016"));
			strEndDate = getAndValidateInput(s, "    End date (dd/mm/yyyy)\t: ", "date", format.parse("17/11/2016"));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("# Max response time");
		strMaxResponseDate = getAndValidateInput(s, "    Date (dd/mm/yyyy)\t\t: ", "date");
		strMaxResponseTime = getAndValidateInput(s, "    Time (hh:mm)\t\t: ", "time");
		
		clearConsole();
		
		//call add participant
		
		//TODO @jeje create instance meeting with above properties
		mc.createMeetingDraft(title, agenda, location, duration, strStartDate, strEndDate, strMaxResponseDate);
		
		System.out.println("CREATE MEETING - SUMMARY");
		System.out.println("--------------------------------");
		System.out.println("# Title\t\t\t\t: " + title);
		System.out.println("# Agenda\t\t\t: " + agenda);
		System.out.println("# Location\t\t\t: " + location);
		System.out.println("# Duration\t\t\t: " + duration);
		System.out.println("Proposed date range");
		System.out.println("    Start date (dd/mm/yyyy)\t: " + strStartDate);
		System.out.println("    End date (dd/mm/yyyy)\t: " + strEndDate);
		System.out.println("# Max response time");
		System.out.println("    Date (dd/mm/yyyy)\t\t: " + strMaxResponseDate);
		System.out.println("    Time (hh:mm)\t\t: " + strMaxResponseTime);

		
	}
	
	public void participantListView() {
		System.out.println("Participant List");
		System.out.println("--------------------------------");
		MeetingController mc = new MeetingController();
		mc.getParticipantList();
	}
	
	public void addMeetingParticipantView() {
		System.out.println("Participant List");
		System.out.println("--------------------------------");
	}
	
	public void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	}
	
	public Date getSpecificDate(int x) {
		//DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, x);
		return cal.getTime();
	}
	
	public String getAndValidateInput(Scanner s, String label, String type) {
		int test;
		String input, regex = ".", errorMsg = ".";
		
		if(type.equals("date")) { //format: dd/mm/yyyy
			regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
			errorMsg = "Invalid date format. Please re-enter.";
		}
		else if(type.equals("duration")) { //positive integer between 1-8
			regex = "^[1-8]+$";
			errorMsg = "Please enter a value between 1-8";
		}
		else if(type.equals("time")) { //format: hh:mm
			regex = "[0-9]{2}:[0-9]{2}";
			errorMsg = "Invalid time format. Please re-enter.";
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
	
	public String getAndValidateInput(Scanner s, String label, String type, Date lowerDate) {
		int test;
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

		String input;
			do {
				test = 1;
				input = getAndValidateInput(s, label, type);
				try {
					if(lowerDate.after(format.parse(input))) {
						test = 0;
						System.out.println("The date you entered is not allowed");
					}/* else if(upperDate.before(format.parse(input))) {
						test = 0;
						System.out.println("The date you entered is not allowed");
					}*/
				} catch (ParseException e) {
					System.out.println("Error when parsing date");
					e.printStackTrace();
				}
			} while(test == 0);
		return input;
	}

}
