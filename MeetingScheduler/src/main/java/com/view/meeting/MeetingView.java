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
import com.model.meeting.MeetingTimeSlot;
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
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

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
		addStrParticipantList("M"+mc.getMeetingDraft().getId(), impParticipantList, true);
		addStrParticipantList("M"+mc.getMeetingDraft().getId(), ordParticipantList, false);
		System.out.println("\n  Press enter to continue");
		s.nextLine();
		
		createMeetingSummaryView(s);
		addMeetingParticipantView();
	}
	
	public void addStrParticipantList(String meetingID, String listParticipant, boolean isImportant) {
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
				System.out.println("No\tResponse\tResponse Date\t\tEmail\t\t");
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
		System.out.println("# Scheduled date\t\t: " + m.getScheduledDate());
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
	
	public void viewMeetingInfo(Meeting m) {
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
		//List<MeetingInvitation> invitationList= mc.getMeetingInvitationByEmail(email);
		List<MeetingParticipant> invitationList= mc.getInvitationListByEmail(email);
		if(invitationList.size()==0) {
			 System.out.println("You have not invited in any meeting yet");
			 s.nextLine();
		} else {
			System.out.println("No\tInvitation Date\t\tMeeting ID\tTitle\t\tProposed Date Range\tMeeting Status\tInitiator\tYour Status\t\tYour Response\tNegotiation Deadline");
			for(int i=0;i<invitationList.size();i++) {
				Meeting m = mc.getMeetingByID("M" + invitationList.get(i).getMeetingID());	
				
				System.out.print(i+1 + "\t");
				System.out.print(invitationList.get(i).getInvitationDate() + "\t");
				System.out.print("M" + invitationList.get(i).getMeetingID() + "\t\t");
				System.out.print(prettyPrint(m.getTitle(),15) + "\t");
				System.out.print(m.getProposedDateRange() + "\t");
				System.out.print(getStrMeetingStatus(m.getMeetingStatus()) + "\t");
				System.out.print(m.getMeetingInitiator() + "\t");
				System.out.print(getStrImportant(invitationList.get(i).isImportant()) + "\t");
				System.out.print(getStrResponseStatus(invitationList.get(i).getResponse()) + "\t\t");
				System.out.println(m.getNegotiationDeadline());
			}
			System.out.println();
		}
	}
	
	public String prettyPrint(String text, int length) {
		int x;
		if(text.length()<length) {
			x = length - text.length();
			for(int i=0;i<x;i++) {
				text = text + " ";
			}
		} else {
			text = text.substring(0, length);
		}
		return text;
	}
	
	public void rejectInvitation(String meetingID, String email) {
		Meeting m;
		String answer;
		MeetingParticipant mp;
		List<MeetingTimeSlot> mts;
		m = mc.getMeetingByID(meetingID);
		if(mc.isParticipant(m, email)) {
			mp = mc.getParticipantInfo(m, email);
			System.out.println("You are " + getStrImportant(mp.isImportant()) + " for this meeting.");
			answer = validator.getAndValidateInput(s, "Are you sure wants to reject the invitation \nfor meeting id : " + meetingID + " (Y/N) ? ", "YN");
			
			if(answer.equals("Y")) {
				if(mc.rejectInvitation(meetingID, email)) {
					System.out.println("Your response: `REJECT` for meeting id: `"+ meetingID +"` has been recorded\n");
				} else {
					System.out.println("You are not eligible to response to this meeting, or\nThe meeting invitation is no longer able to be rejected\n");
				}
			} else {
				System.out.println();
			}
		}
	}
	
	public String timeslotInvitationAvailability(boolean status) {
		if(status) {
			return "Available";
		} else {
			return "Not Available";
		}
	}
	
	public void acceptInvitation(String meetingID, String email) {
		Meeting m;
		String answer;
		MeetingParticipant mp;
		List<MeetingTimeSlot> mts;
		int number = 0;
		
		//check the eligibility 
		m = mc.getMeetingByID(meetingID);
		
		if(mc.isParticipant(m, email)) {
			//confirmation for accept
			mp = mc.getParticipantInfo(m, email);
			System.out.println("You are " + getStrImportant(mp.isImportant()) + " for this meeting.");
			answer = validator.getAndValidateInput(s,"Are you sure wants to accept meeting ID: " + meetingID + " (Y/N) ? ", "YN");
			
			
			System.out.println("\nYour response is ACCEPT. Please add availability time.");
			//get timeslot
			do {
				mts = m.getMeetingTimeSlots();
				System.out.println("\nNo\tDate\t\tStart\tEnd\tDuration\tYour Availability Status");
				//show timeslot
				for(int i=0;i<mts.size();i++) {
					System.out.print(i+1 + "\t");
					System.out.print(mts.get(i).getDate() + "\t");
					System.out.print(mts.get(i).getStartTime() + "\t");
					System.out.print(mts.get(i).getEndTime() + "\t");
					System.out.print(m.getDuration() + " hours  \t");
					System.out.println(timeslotInvitationAvailability(mc.checkAvailabilityTimeSlotOfParticipant(mts.get(i), email)));
				}
				
				//menu add / remove timeslot
				System.out.println("Menu");
				System.out.println("1. Add available slot");
				System.out.println("2. Remove slot availability");
				System.out.println("3. Save response");
				System.out.print("Your choice: ");
				answer = s.nextLine();
				
				switch(answer) {
					case "1": 
						System.out.print("Enter time slot number (1-" + mts.size() + ") : ");
						try {
							number = s.nextInt();
						} catch(Exception e) {
							System.out.print("Invalid input, please try again.");
						}
						s.nextLine();
						
						if(number<1 && number>mts.size()) {
							System.out.print("Invalid time slot number.");
							s.nextLine();
						} else {
							if(mp.isImportant()) { //if important
								mts.get(number-1).setNumImportantParticipant(mts.get(number-1).getNumImportantParticipant()+1);
								
								ArrayList<String> im = mts.get(number-1).getImportantParticipants();
								im.add(email);
								mts.get(number-1).setImportantParticipants(im);
								im = mts.get(number-1).getImportantParticipants();
							} else {
								mts.get(number-1).setNumOrdinaryParticipant(mts.get(number-1).getNumOrdinaryParticipant()+1);
								
								ArrayList<String> im = mts.get(number-1).getOrdinaryParticipants();
								im.add(email);
								mts.get(number-1).setOrdinaryParticipants(im);
								im = mts.get(number-1).getOrdinaryParticipants();
							}
						}
						break;
					case "2":
						System.out.print("Enter time slot number (1-" + mts.size() + ") : ");
						try {
							number = s.nextInt();
						} catch(Exception e) {
							System.out.print("Invalid input, please try again.");
						}
						s.nextLine();
						
						if(number<1 && number>mts.size()) {
							System.out.print("Invalid time slot number.");
							s.nextLine();
						} else {
							if(mp.isImportant()) { //if important
								mts.get(number-1).setNumImportantParticipant(mts.get(number-1).getNumImportantParticipant()+1);
								
								ArrayList<String> im = mts.get(number-1).getImportantParticipants();
								im.remove(email);
								mts.get(number-1).setImportantParticipants(im);
								im = mts.get(number-1).getImportantParticipants();
							} else {
								mts.get(number-1).setNumOrdinaryParticipant(mts.get(number-1).getNumOrdinaryParticipant()+1);
								
								ArrayList<String> im = mts.get(number-1).getOrdinaryParticipants();
								im.remove(email);
								mts.get(number-1).setOrdinaryParticipants(im);
								im = mts.get(number-1).getOrdinaryParticipants();
							}
						}
						break;
					case "3": 
						//set response
						List<MeetingParticipant> arrMP = m.getMeetingParticipant();
						for(int i=0;i<arrMP.size();i++) {
							if(arrMP.get(i).getEmail().equals(email)) {
								arrMP.get(i).setResponse(arrMP.get(i).ACCEPT);
								arrMP.get(i).setResponseDate(validator.getCurrentTime());
							}
						}
						m.setMeetingParticipant(arrMP);
						
						//set timeslot
						m.setMeetingTimeSlots(mts);
						
						//update meeting in arrMeeting
						List<Meeting> arrMeeting = mc.getAllMeeting();
						for(int i=0;i<arrMeeting.size();i++) {
							if(arrMeeting.get(i).getId()==Integer.parseInt(meetingID.substring(1))) {
								arrMeeting.set(i, m);
							}
						}
						
						//save to json
						mc.updateMeetingData(arrMeeting);
						
						System.out.println("Your response has been recorded.");
						System.out.println("You may change your timeslot availability before the negotiation deadline.");
						System.out.println("");
						break;
					default: 
						System.out.println("Please enter menu option between 1-3");
						s.nextLine();
						break;
				}
			} while(!(answer.equals("3")));
		}
		
	}
	
	public void cancelMeeting(String meetingID, String email) {
		//check eligibility
		Meeting m = mc.getMeetingByID(meetingID);
		String answer = "";
		if(m.getMeetingInitiator().equals(email)) {
			answer = validator.getAndValidateInput(s, "Are you sure wants to cancel meeting id: "+ meetingID + " (Y/N) ? ", "YN");
			switch(answer) {
				case "Y" : 
					mc.cancelMeeting(m);
					break;
				case "N" : 
					System.out.println("Cancelling the cancel-meeting");
					break;
			}
		} else {
			System.out.println("You are not eligible to view this meeting");
		}
	}
	
	public void runScheduler(String meetingID, String email) {
		Meeting m = mc.getMeetingByID(meetingID);
		if(m==null) { //check meeting id exist
			System.out.println("You are not eligible to run-scheduler on this meeting");
		} else {
			if(!m.getMeetingInitiator().equals(email)) { //check initiator
				System.out.println("You are not eligible to view this meeting");
			} else {
				if( m.getMeetingStatus() == m.CANCELED || //check meeting status
					m.getMeetingStatus() == m.FINISH ||
					m.getMeetingStatus() == m.RUNNING) {
					System.out.println("You are not able to run-scheduler on canceled / running / finish meeting");
				} else {
					//check is all participant already responded
					List<MeetingParticipant> mp = m.getMeetingParticipant();
					int k = 0;
					for(int i=0;i<mp.size();i++) {
						if(mp.get(i).getResponse()!=mp.get(i).PENDING) {
							k++;
						}
					}
					if(k==mp.size()) { //all participant already respond
						//calculate & get schedule
						
					} else { //some participant have not give respond
						if(validator.strToDate(validator.getCurrentTime()).before(validator.strToDate(m.getNegotiationDeadline())) ){
							//check if pass negotiation deadline
							System.out.println("You are not able to run-scheduler before the negotiation \ndeadline / before all participant responded");
						} else {
							
							//calculate & get schedule
							
						}
					}
					
				}
				
				
			}
		}
		
	}
	
	public void detailInvitation(String meetingID, String email) {
		//check eligibility
		List<Meeting> arrMeeting = mc.getAllMeeting();
		boolean found = false;
		for(int i=0;i<arrMeeting.size();i++) {
			if(arrMeeting.get(i).getId()==Integer.parseInt(meetingID.substring(1))) {
				if(mc.isParticipant(arrMeeting.get(i), email)) {
					found = true;
					viewMeetingInfo(arrMeeting.get(i));
					
					//participant response
					MeetingParticipant mp = mc.getParticipantInfo(arrMeeting.get(i), email);
					System.out.println("----\nYour response");
					System.out.println("# Participant status\t: " + getStrImportant(mp.isImportant()));
					System.out.println("# Response\t\t: " + getStrResponseStatus(mp.getResponse()));
					System.out.println("# Response date\t\t: " + mp.getResponseDate());
					System.out.println("# Time slot detail");
					List<MeetingTimeSlot> mts = arrMeeting.get(i).getMeetingTimeSlots();
					System.out.println("\nNo\tDate\t\tStart\tEnd\tYour Availability Status");
					//show timeslot
					for(int j=0;j<mts.size();j++) {
						System.out.print(j+1 + "\t");
						System.out.print(mts.get(j).getDate() + "\t");
						System.out.print(mts.get(j).getStartTime() + "\t");
						System.out.print(mts.get(j).getEndTime() + "\t");
						System.out.println(timeslotInvitationAvailability(mc.checkAvailabilityTimeSlotOfParticipant(mts.get(j), email)));
					}
				}
			}
		}
		
		if(!found) {
			System.out.println("You are not eligible to view this meeting / invitation");
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
