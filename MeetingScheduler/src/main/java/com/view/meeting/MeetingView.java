package com.view.meeting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.controller.meeting.MeetingController;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingParticipant;

/**
 * @author jessiesetiady
 *
 */
public class MeetingView {
	
	MeetingController mc = new MeetingController();
	
	
	public void createMeetingView(String meetingInitiatorID) {
		//variables
		Scanner s = new Scanner(System.in);
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

		String title, agenda, location, strStartDate = null, 
			 strEndDate = null, strMaxResponseDate, strMaxResponseTime;
		
		int duration, input, choice;
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

		mc.createMeetingDraft(title, agenda, location, duration, strStartDate, strEndDate, strMaxResponseDate, strMaxResponseTime, meetingInitiatorID);
	
		do {
			System.out.println("\nConfirmation");
			System.out.println("1. Next (add participant)");
			System.out.println("2. Cancel Meeting Creation");
			System.out.print("Enter your choice: ");
			choice = s.nextInt();
			s.nextLine();
			
			switch(choice) {
				case 1:
					createMeetingAddParticipantView();
					break;
				case 2: 
					System.out.println("\nMeeting creation has been canceled\nPress enter to continue");
					s.nextLine();
					break;
				default:
					System.out.println("\nInvalid input\nPlease choose between 1 and 2\nPress enter to continue");
					s.nextLine();
			}
		} while(choice!=1 && choice!=2);
		
	}
	
	
	public void createMeetingSummaryView() {
		Meeting m = mc.getMeetingDraft();
		System.out.println("CREATE MEETING - SUMMARY");
		System.out.println("--------------------------------");
		System.out.println("# Title\t\t\t\t: " + m.getTitle());
		System.out.println("# Agenda\t\t\t: " + m.getAgenda());
		System.out.println("# Location\t\t\t: " + m.getLocation());
		System.out.println("# Duration\t\t\t: " + m.getDuration());
		System.out.println("# Proposed date range");
		System.out.println("    Start date (dd/mm/yyyy)\t: " + m.getProposedStartDate());
		System.out.println("    End date (dd/mm/yyyy)\t: " + m.getProposedEndDate());
		System.out.println("# Max response time");
		System.out.println("    Date (dd/mm/yyyy)\t\t: " + m.getMaxResponseDate());
		System.out.println("    Time (hh:mm)\t\t: " + m.getMaxResponseDate());
	}
	
	public void participantListView() {
		Scanner s = new Scanner(System.in);
		System.out.println("\n\nParticipant List");
		System.out.println("--------------------------------");
		
		//MeetingController mc = new MeetingController();
		//mc.getParticipantList();
		s.nextLine();
		
	}
	
	public void createMeetingAddParticipantView() {
		Scanner s = new Scanner(System.in);
		String email, strImportant;
		
		System.out.println("\n\nAdd Participant");
		System.out.println("--------------------------------");
		email = getAndValidateInput(s, "Email\t\t\t\t: ", "text");
		strImportant = getAndValidateInput(s, "Important participant (Y/N)\t: ", "YN");
		
		//add to mc
		mc.addMeetingParticipant(email, strImportantToBoolean(strImportant));
		
		System.out.println("\n\nParticipant successfully added. Press enter to continue\n\n");
		
		s.nextLine();
	}
	
	public boolean strImportantToBoolean(String isImportant) {
		return isImportant.equals("Y") ? true : false;
	}
	
	public String boolImportantToString(boolean isImportant) {
		return isImportant ? "Y" : "N";
	} 
	
	public void addMeetingParticipantView() {
		int choice;
		ArrayList<MeetingParticipant> arrMP;
		do {
			System.out.println("CREATE MEETING - Add Participant");
			System.out.println("--------------------------------");
			System.out.println("Participant List");
			arrMP = mc.getParticipantList();
			if(arrMP.size()==0) {
				System.out.println("\nThis meeting has no participant. Add one now\n");
			} else {
				for(int i=0;i<arrMP.size();i++) {
					System.out.println((i+1) + " " + arrMP.get(i).getEmail() + " " + arrMP.get(i).isImportant());
				}
			}
			
			System.out.println("\n\n");
			System.out.println("1. Add meeting participant");
			System.out.println("2. Edit meeting participant");
			System.out.println("3. Show meeting information");
			System.out.println("4. Save meeting");
			System.out.println("5. Cancel meeting creation");
			System.out.println("Enter your choice");
			
			choice = 3;
		} while(choice != 3);
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
		else if(type.equals("YN")) {
			regex = "[NYny]{1}";
			errorMsg = "Please enter either Y or N character";
		}
		else if(type.equals("text")) {
			regex = "^(?!\\s*$).+";
			errorMsg = "This field cannot be empty";
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
