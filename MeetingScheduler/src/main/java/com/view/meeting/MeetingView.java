package com.view.meeting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
	private Scanner s;
	
	
	public void createMeetingView(String meetingInitiatorID) {
		//variables
		Scanner s = new Scanner(System.in);
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

		String title, agenda, location, strStartDate = null, 
			 strEndDate = null, strMaxResponseDate, strMaxResponseTime, createdDate;
		
		int duration, input, choice;
		boolean invalidInput;
		
		System.out.println("CREATE MEETING");
		System.out.println("--------------------------------");
		
		title = getAndValidateInput(s, "# Title\t\t\t\t: ", "text");
		
		agenda = getAndValidateInput(s, "# Agenda\t\t\t: ", "text");
		
		location = getAndValidateInput(s, "# Location\t\t\t: ", "text");
		
		duration = Integer.parseInt(getAndValidateInput(s, "# Duration (hours) (1-9)\t: ", "duration"));
		
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
		
		createdDate = new SimpleDateFormat("dd/mm/yyyy").format(Calendar.getInstance().getTime());

		mc.createMeetingDraft(title, agenda, location, duration, strStartDate, strEndDate, strMaxResponseDate, strMaxResponseTime, meetingInitiatorID, createdDate);
	
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
					addMeetingParticipantView();
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
	
	
	public void createMeetingSummaryView(Scanner s) {
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
		
		System.out.println("Press enter to continue\n\n");
		s.nextLine();
	}
	
	public void createMeetingAddParticipantView() {
		Scanner s = new Scanner(System.in);
		ArrayList<MeetingParticipant> arrMP = new ArrayList<MeetingParticipant>();
		String email, strImportant;
		arrMP = mc.getParticipantList();
		
		System.out.println("\n\nAdd Participant");
		System.out.println("--------------------------------");
		email = getAndValidateInput(s, "Email\t\t\t\t: ", "text");
		if(mc.findMeetingParticipantByEmail(arrMP, email)) {
			System.out.println(email + " already exist in Participant list");
			s.nextLine();
		} else {
			strImportant = getAndValidateInput(s, "Important participant (Y/N)\t: ", "YN");
			
			//add to mc
			mc.addMeetingParticipant(email, strImportantToBoolean(strImportant));
			
			System.out.println("\n\nParticipant successfully added.\nPress enter to continue\n\n");
			
			s.nextLine();
		}
	}
	
	public void addMeetingParticipantView() {
		int choice;
		Scanner s = new Scanner(System.in);
		ArrayList<MeetingParticipant> arrMP;
		do {
			System.out.println("CREATE MEETING - Add Participant");
			System.out.println("--------------------------------");
			System.out.println("Participant List");
			arrMP = mc.getParticipantList();
			printParticipantList(arrMP, false);
			
			System.out.println("\n");
			System.out.println("Menu");
			System.out.println("1. Add meeting participant");
			System.out.println("2. Edit meeting participant");
			System.out.println("3. Show meeting information");
			System.out.println("4. Save meeting");
			System.out.println("5. Cancel meeting creation");
			System.out.print("Enter your choice: ");
			choice = s.nextInt();
			s.nextLine();
			
			switch(choice) {
				case 1: createMeetingAddParticipantView(); break;
				case 2: createMeetingEditParticipantView(s); break;
				case 3: createMeetingSummaryView(s); break;
				case 4: saveMeetingView(s); break;
				case 5: cancelMeetingCreation(s); break;
				default: break;
			}
			
		} while(choice!= 4 && choice!=5);
	}
	
	public void createMeetingEditParticipantView(Scanner s) {
		String email, strImportant;
		ArrayList<MeetingParticipant> arrMP;
		String num, confirm, selected;
		int choice;
		
		System.out.println("\nEdit Participant");
		System.out.println("--------------------------------");
		System.out.println("*Participant list");
		arrMP = mc.getParticipantList();
		printParticipantList(arrMP, false);
		
		System.out.println("\nEnter participant number that you want to edit (1-" + arrMP.size() + ")");		
		num = getAndValidateInput(s, "Participant num: ", "number");
		if(Integer.parseInt(num)<0 || Integer.parseInt(num)>arrMP.size()) {
			System.out.println("The entered participant number is not valid\nPress Enter to continue\n");
			s.nextLine();
		} else {
			selected = arrMP.get(Integer.parseInt(num)-1).getEmail();
			System.out.println("Selected: " + selected);
			System.out.println("1. Remove participant");
			System.out.println("2. Set as Important Participant");
			System.out.println("3. Set as Ordinary Participant");
			System.out.println("4. Cancel edit");
			System.out.print("Enter your choice: ");
			choice = s.nextInt();
			s.nextLine();
			
			switch(choice) {
				case 1: 
					confirm = getAndValidateInput(s, "Are you sure wants to remove "
							+ selected +
							" from participant list (Y/N) ? ", "YN");
					if(confirm.equals("Y")) {
						arrMP.remove(Integer.parseInt(num)-1);
						mc.getMeetingDraft().setMeetingParticipant(arrMP);
						System.out.println(selected + " successfully removed");
					} else {
						System.out.println("Canceled");
					}
					s.nextLine();
					break;
				case 2:
					arrMP.get(Integer.parseInt(num)-1).setImportant(true);
					mc.getMeetingDraft().setMeetingParticipant(arrMP);
					System.out.println(selected 
							+ " marked as Important Participant\n");
					s.nextLine();
					break;
				case 3:
					arrMP.get(Integer.parseInt(num)-1).setImportant(false);
					mc.getMeetingDraft().setMeetingParticipant(arrMP);
					System.out.println(selected
							+ " marked as Ordinary Participant\n");
					s.nextLine();
					break;
				case 4:
					System.out.println("Canceled");
					break;
				default: 
					System.out.println("Input invalid.\nPress enter to continue");
					s.nextLine();
					break;
			}
		}
	}
	
	public void printParticipantList(ArrayList<MeetingParticipant> arrMP, boolean withResponse) {
		if(arrMP.size()==0) {
			System.out.println("\nThis meeting has no participant. Add one now\n");
		} else {
			if(withResponse) {
				System.out.println("No\tResponse\tResponse Date\tEmail\t\t");
				for(int i=0;i<arrMP.size();i++) {
					System.out.print((i+1) + "\t");
					System.out.print("PENDING\t\t");
					System.out.print("--/--/----\t");
					System.out.print(arrMP.get(i).getEmail() + "\t");
					System.out.println(boolImportantToString(arrMP.get(i).isImportant()) + "\t");
				}
			} else {
				for(int i=0;i<arrMP.size();i++) {
					System.out.println((i+1) + ". " + arrMP.get(i).getEmail() + " " + boolImportantToString(arrMP.get(i).isImportant()));
				}
			}
		}
	}	
	
	public void cancelMeetingCreation(Scanner s) {		
		System.out.println("\nMeeting creation has been canceled\nPress enter to continue\n");
		s.nextLine();
	}
	
	public void saveMeetingView(Scanner s) {
		mc.saveMeetingCreation();
		System.out.println("\nSuccessfully save the meeting\nPress enter to continue\n");
		s.nextLine();
	}
	
	public Date getSpecificDate(int x) {
		//DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, x);
		return cal.getTime();
	}
	
	public boolean strImportantToBoolean(String isImportant) {
		return isImportant.equals("Y") ? true : false;
	}
	
	public String boolImportantToString(boolean isImportant) {
		return isImportant ? "(important)" : "";
	} 
	
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
	
	public void displayCreatedMeeting(String email) {
		s = new Scanner(System.in);
		int choice, num;
		List<Meeting> createdMeetingList = mc.getListOfCreatedMeeting(email);
		if(createdMeetingList.isEmpty()) {
			System.out.println("You have not created any meeting yet.");
			System.out.println("Press enter to continue");
			s.nextLine();
		} else {
			do {
				System.out.println("No\tMeeting ID\tMeeting Status\tCreated Date\tSchedule\tTitle");
				for(int i=0;i<createdMeetingList.size();i++) {
					System.out.print(i+1 + "\t");
					System.out.print("M" + createdMeetingList.get(i).getId() + "\t\t");
					System.out.print(getStrMeetingStatus(createdMeetingList.get(i).getMeetingStatus()) + "\t");
					System.out.print(createdMeetingList.get(i).getCreatedDate() + "\t");
					System.out.print(createdMeetingList.get(i).getScheduledDate() + "\t");
					System.out.print(createdMeetingList.get(i).getTitle());
					System.out.println();
				}
				System.out.println("--------------------------------");
				System.out.println("You have created " + createdMeetingList.size() + " meeting\n");
				
				
				System.out.println("Menu");
				System.out.println("1. View Meeting Details");
				System.out.println("2. Back");
				System.out.print("Enter your choice: ");
				choice = s.nextInt();
				s.nextLine();
				
				switch(choice)  {
					case 1:
						System.out.print("Enter meeting number (1-"+createdMeetingList.size()+") : ");
						num = s.nextInt();
						s.nextLine();
						
						if(num<1 || num>createdMeetingList.size()) {
							System.out.println("Invalid input");
							System.out.println("Press enter to continue");
							s.nextLine();
						} else {
							System.out.println();
							viewCreatedMeetingDetails(createdMeetingList.get(num-1));
							System.out.println("Press enter to continue");
							s.nextLine();
							
						}
						break;
					case 2: break;
					default:
						System.out.println("Invalid input, please try again\n");
						s.nextLine();
						break;
				}
			} while(choice!=2);
		}
		
	}
	
	public void viewMeetingByID(String meetingID) {
		Meeting m = mc.getMeetingByID(meetingID);
		viewCreatedMeetingDetails(m);
	}
	
	public void viewCreatedMeetingDetails(Meeting m) {
		System.out.println("MEETING DETAILS");
		System.out.println("--------------------------------");
		System.out.println("# Meeting ID\t\t\t: " + m.getId());
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
		System.out.println("---\nParticipant List:");
		printParticipantList(m.getMeetingParticipant(), true);
		System.out.println();
	}
	
	public void viewMeetingScheduleByEmail(String email) {
		s = new Scanner(System.in);
		int choice = 0, num;
		List<Meeting> scheduledMeetingList = mc.getListOfScheduledMeetingByEmail(email);
		if(scheduledMeetingList.isEmpty()) {
			System.out.println("You have not listed in any scheduled meeting.");
			System.out.println("Press enter to continue");
			s.nextLine();
		} else {
			do {
				System.out.println("Schedule for " + email);
				System.out.println("No\tMeeting ID\tMeeting Status\tCreated Date\tSchedule\tTitle");
				for(int i=0;i<scheduledMeetingList.size();i++) {
					System.out.print(i+1 + "\t");
					System.out.print("M" + scheduledMeetingList.get(i).getId() + "\t\t");
					System.out.print(getStrMeetingStatus(scheduledMeetingList.get(i).getMeetingStatus()) + "\t");
					System.out.print(scheduledMeetingList.get(i).getCreatedDate() + "\t");
					System.out.print(scheduledMeetingList.get(i).getScheduledDate() + "\t");
					System.out.print(scheduledMeetingList.get(i).getTitle());
					System.out.println();
				}
				System.out.println("--------------------------------");
				System.out.println("You are listed as participant in " + scheduledMeetingList.size() + " meeting\n");
				
				System.out.println("Menu");
				System.out.println("1. View Meeting Details");
				System.out.println("2. Back");
				System.out.print("Enter your choice: ");
				choice = s.nextInt();
				
				switch(choice)  {
					case 1:
						System.out.print("Enter meeting number (1-"+scheduledMeetingList.size()+") : ");
						num = s.nextInt();
						System.out.println();
						viewCreatedMeetingDetails(scheduledMeetingList.get(num-1));
						System.out.println("Press enter to continue\n\n");
						s.nextLine();
						break;
					case 2: break;
					default:
						System.out.println("Invalid input, please try again\n");
						s.nextLine();
						s.nextLine();
						break;
				}
			} while(choice!=2);
		}
	}
	
	public String getStrMeetingStatus(int status) {
		switch(status) {
			case 0 : return "Negotiating";
			case -9 : return "Canceled   ";
			case 1 : return "Scheduled   ";
			case 2 : return "Running    ";
			case 3 : return "Finish     ";
		}
		return "";
	}

}
