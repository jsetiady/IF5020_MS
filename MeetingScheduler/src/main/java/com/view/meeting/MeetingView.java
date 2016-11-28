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
import com.controller.user.UserController;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingInvitation;
import com.model.meeting.MeetingParticipant;
import com.utilities.Validator;

/**
 * @author jessiesetiady
 *
 */
public class MeetingView {
	
	MeetingController mc = new MeetingController();
	UserController uc = new UserController();
	private Scanner s = new Scanner(System.in);
	private Validator validator = new Validator();
	
	
	public void createMeetingView(String meetingInitiatorID) {
		//variables
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

		String title, agenda, location, negotiationDeadline = null, createdDate, proposedDateRange;
		String ordParticipantList = "", impParticipantList = "";
		int duration, choice, test;
		
		System.out.println("CREATE MEETING");
		System.out.println("--------------------------------");
		
		title = validator.getAndValidateInput(s, "# Title\t\t\t\t: ", "text");
		
		agenda = validator.getAndValidateInput(s, "# Agenda\t\t\t: ", "text");
		
		location = validator.getAndValidateInput(s, "# Location\t\t\t: ", "text");
		
		duration = Integer.parseInt(validator.getAndValidateInput(s, "# Duration (hours) (1-9)\t: ", "duration"));
		
		System.out.println("\n  Note: Please enter the proposed date range in format: dd/mm/yyyy - dd/mm/yyyy");
		proposedDateRange = validator.getAndValidateInput(s, "# Proposed date range\t\t: ", "daterange");
		negotiationDeadline = validator.getAndValidateInput(s, "# Negotiation deadline\t\t: ", "date", proposedDateRange);
		System.out.println("\n  Note: Please enter participant list in format: <email>, <email>");
		
		do {
			test = 1;
			impParticipantList = validator.getAndValidateInput(s, "# Important participant\t\t: ", "participant");
			ordParticipantList = validator.getAndValidateInput(s, "# Ordinary participant\t\t: ", "participant");
			
			if(ordParticipantList.equals("") && impParticipantList.equals("")) {
				test = 0;
				System.out.println("\n  Err: You have to enter at least one participant. Add one now.");
			}
		} while(test == 0);
		
		createdDate = new SimpleDateFormat("dd/mm/yyyy").format(Calendar.getInstance().getTime());
		
		//instantiate meeting
		mc.createMeetingDraft(title, agenda, location, duration, proposedDateRange, negotiationDeadline, meetingInitiatorID, createdDate);
		//meeting participant
		System.out.println("\n# Confirmation:");
		addStrParticipantList(impParticipantList, true);
		addStrParticipantList(ordParticipantList, false);
		System.out.println("\n  Press enter to continue");
		s.nextLine();
		
		createMeetingSummaryView(s);
		addMeetingParticipantView();
	}
	
	public void addStrParticipantList(String listParticipant, boolean isImportant) {
		String[] participants;
		
		participants = listParticipant.replaceAll("\\s","").split(",");
		int k = 0;
		for(int i=0;i<participants.length;i++) {
			if(!participants[i].equals("")) {
				if(validator.isValidEmail(participants[i])) {
					if(uc.getUserByEmail(participants[i])!=null) {
						mc.addMeetingParticipant(participants[i], isImportant);
						k++;
					} else {
						System.out.println("  Err: user with email : " + participants[i] + " is not exist");
					}
				} else {
					System.out.println("  Err: " + participants[i] + " is not a valid email format");
				}
			}
		}
		
		if(isImportant) {
			System.out.println("  " + k + " important participant added\n");
		} else {
			System.out.println("  " + k + " ordinary participant added\n");
		}
	}
	
	
	public void createMeetingSummaryView(Scanner s) {
		Meeting m = mc.getMeetingDraft();
		System.out.println("\nCREATE MEETING - SUMMARY");
		System.out.println("--------------------------------");
		System.out.println("# Title\t\t\t\t: " + m.getTitle());
		System.out.println("# Agenda\t\t\t: " + m.getAgenda());
		System.out.println("# Location\t\t\t: " + m.getLocation());
		System.out.println("# Duration\t\t\t: " + m.getDuration());
		System.out.println("# Proposed date range: ");
		System.out.println("    Start date (dd/mm/yyyy)\t: " + m.getProposedStartDate());
		System.out.println("    End date (dd/mm/yyyy)\t: " + m.getProposedEndDate());
		System.out.println("# Negotiation Deadline\t\t: "  + m.getNegotiationDeadline() );
		
	}
	
	public void createMeetingAddParticipantView() {
		List<MeetingParticipant> arrMP = new ArrayList<MeetingParticipant>();
		String email, strImportant;
		arrMP = mc.getParticipantList();
		
		System.out.println("\n\nAdd Participant");
		System.out.println("--------------------------------");
		email = validator.getAndValidateInput(s, "Email\t\t\t\t: ", "text");
		if(mc.findMeetingParticipantByEmail(arrMP, email)) {
			System.out.println(email + " already exist in Participant list");
			s.nextLine();
		} else {
			strImportant = validator.getAndValidateInput(s, "Important participant (Y/N)\t: ", "YN");
			
			//add to mc
			mc.addMeetingParticipant(email, strImportantToBoolean(strImportant));
			
			System.out.println("\n\nParticipant successfully added.\nPress enter to continue\n\n");
			
			s.nextLine();
		}
	}
	
	public void addMeetingParticipantView() {
		int choice;
		List<MeetingParticipant> arrMP;
		do {
			System.out.println("\n--------------------------------");
			System.out.println("Participant List");
			arrMP = mc.getParticipantList();
			printParticipantList(arrMP, false);
			
			System.out.println("\n");
			System.out.println("Menu");
			System.out.println("1. Add more meeting participant");
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
		List<MeetingParticipant> arrMP;
		String num, confirm, selected;
		int choice;
		
		System.out.println("\nEdit Participant");
		System.out.println("--------------------------------");
		System.out.println("*Participant list");
		arrMP = mc.getParticipantList();
		printParticipantList(arrMP, false);
		
		System.out.println("\nEnter participant number that you want to edit (1-" + arrMP.size() + ")");		
		num = validator.getAndValidateInput(s, "Participant num: ", "number");
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
					confirm = validator.getAndValidateInput(s, "Are you sure wants to remove "
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
	
	public void printParticipantList(List<MeetingParticipant> arrMP, boolean withResponse) {
		int choice, num;
		if(arrMP.size()==0) {
			System.out.println("\nThis meeting has no participant. Add one now\n");
		} else {
			if(withResponse) {
				//TODO @jeje get meetingParticipant instance
				System.out.println("No\tResponse\tResponse Date\tEmail\t\t");
				for(int i=0;i<arrMP.size();i++) {
					System.out.print((i+1) + "\t");
					System.out.print(getStrResponseStatus(arrMP.get(i).getResponse()) + "\t\t");
					System.out.print(arrMP.get(i).getResponseDate()+"\t");
					System.out.print(arrMP.get(i).getEmail() + "\t");
					System.out.println(boolImportantToString(arrMP.get(i).isImportant()) + "\t");
				}
				System.out.println("Menu:");
				System.out.println("1. View response detail");
				System.out.println("2. Show response by timeslot");
				System.out.println("3. Back");
				System.out.print("Enter your choice: "); choice = s.nextInt();
				
				switch(choice) {
				case 1 : 
					System.out.print("Enter participant number: ");
					num = s.nextInt();
					
					//TODO: @jeje error handling
					
					System.out.println("Participant email\t: " + arrMP.get(num-1).getEmail());
					System.out.println("Response\t\t: " + getStrResponseStatus(arrMP.get(num-1).getResponse()));
					System.out.println("Response date\t\t: " + arrMP.get(num-1).getResponseDate());
					System.out.println("Available slot\t\t: ");
					
					//TODO show arr meeting timeslot
					
					s.nextLine();
					break;
				case 2 : break;
				case 3 : break;
				default: System.out.println("Input invalid\n");s.nextLine();break;
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
	
	
	
	public String getAndValidateInput(Scanner s, String label, String type, Date lowerDate) {
		int test;
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

		String input;
			do {
				test = 1;
				input = validator.getAndValidateInput(s, label, type);
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
		int choice = 0, num;
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
				try {
					choice = s.nextInt();
					
				}
				catch(Exception e) {
					System.out.println("Input is not recognized");
				}
				
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
		if(m==null) {
			System.out.println("You don't have privilege to view this meeting");
		} else {
			viewCreatedMeetingDetails(m);
		}
	}
	
	public void viewCreatedMeetingDetails(Meeting m) {
		System.out.println("MEETING DETAILS");
		System.out.println("--------------------------------");
		System.out.println("# Meeting ID\t\t\t: M" + m.getId());
		System.out.println("# Initiator\t\t\t: " + m.getMeetingInitiator());
		System.out.println("# Status\t\t\t: " + getStrMeetingStatus(m.getMeetingStatus()));
		System.out.println("---\n# Title\t\t\t\t: " + m.getTitle());
		System.out.println("# Agenda\t\t\t: " + m.getAgenda());
		System.out.println("# Location\t\t\t: " + m.getLocation());
		System.out.println("# Duration\t\t\t: " + m.getDuration());
		System.out.println("# Proposed date range");
		System.out.println("    Start date (dd/mm/yyyy)\t: " + m.getProposedStartDate());
		System.out.println("    End date (dd/mm/yyyy)\t: " + m.getProposedEndDate());
		System.out.println("# Negotiation Deadline\t\t: " + m.getNegotiationDeadline());
		System.out.println("---\nParticipant List:");
		printParticipantList(m.getMeetingParticipant(), true);
		System.out.println();
	}
	
	public void viewMeetingScheduleByEmail(String email) {
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
	
	public void viewMeetingInvitation(String email) {
		List<MeetingInvitation> invitationList= mc.getMeetingInvitationByEmail(email);
		if(invitationList.size()==0) {
			 System.out.println("You have not invited in any meeting yet");
			 s.nextLine();
		} else {
			System.out.println("No\tInvitation Date\t\tMeeting ID\tMeeting Status\tInitiator\tYour Status\t\tYour Response");
			for(int i=0;i<invitationList.size();i++) {
				
				Meeting m = mc.getMeetingByID("M" + invitationList.get(i).getMeetingID());
				
				//TODO load meeting
				
				System.out.print(i+1 + "\t");
				System.out.print(invitationList.get(i).getInvitationDate() + "\t");
				System.out.print(invitationList.get(i).getMeetingID() + "\t\t");
				System.out.print(invitationList.get(i).getMeetingID() + "\t");
				System.out.print("jeje@gmail.com" + "\t");
				System.out.print(getStrImportant(invitationList.get(i).getMp().isImportant()) + "\t");
				System.out.println(getStrResponseStatus(invitationList.get(i).getMp().getResponse()) + "\t");
			}
		}
	}
	
	public String getStrImportant(boolean important) {
		if(important) {
			return "Important participant";
		} else {
			return "Ordinary participant";
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
	
	public String getStrResponseStatus(int status) {
		switch(status) {
			case -1 : return "Pending";
			case 0 : return "Reject ";
			case 1 : return "Accept ";
		}
		return "";
	}

}
