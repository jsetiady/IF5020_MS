package com.view.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//import java.util.Scanner;
import com.controller.invitation.InvitationController;
import com.model.invitation.Invitation;
import com.model.user.User;
import com.controller.meeting.MeetingController;

/**
 * @author Siti_Rozani
 *
 */

public class ParticipantView 
{
	
	User users = new User();
	InvitationController pc = new InvitationController();
	
	//method menu peserta
	/*public static void menuParticipant()
	{
		sc = new Scanner(System.in);
		int pilih = 0;
		
		System.out.println("=================================");
		System.out.println("Hello, "+ user.getFirstName());
		System.out.println("=================================");
		System.out.println("| \t\t\t\t|");
		System.out.println("| 1. List Invitation\t\t|");
		System.out.println("| 2. Exit\t\t\t|");
		System.out.println("|\t\t\t\t|");
		System.out.println("=================================");
		System.out.print(" Enter your choice :");
		
		try {
			pilih = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input");
		}
		System.out.println();
		
		switch(pilih) 
		{
			case 1:
				listInvitation();
				break;
			case 2:
				break;
			default: 
				System.out.println("Sorry, Menu not found");
				System.out.println();
				menuParticipant();
		}
		
	}*/
	
	// method untuk inisiator
	/*public static void listMeeting()
	{
		Map<Meeting.MeetingStatus, List<Meeting>> mappedMeetings = MeetingController.getMeetingsOrderByStatus(this.user.getEmail());
		
		System.out.println("List Meeting ");
		System.out.println("===================================");
		System.out.println();
        System.out.println("Negotiating:");
        if (mappedMeetings.get(Meeting.MeetingStatus.NEGOTIATING) != null && !mappedMeetings.get(Meeting.MeetingStatus.NEGOTIATING).isEmpty()) {
            for (Meeting meeting : mappedMeetings.get(Meeting.MeetingStatus.NEGOTIATING)) {
                System.out.println("(id=" + meeting.getId() + ") " + meeting.getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        
        System.out.println();
        
        System.out.println("Confirmed:");
        if (mappedMeetings.get(Meeting.MeetingStatus.CONFIRMED) != null && !mappedMeetings.get(Meeting.MeetingStatus.CONFIRMED).isEmpty()) {
            for (Meeting meeting : mappedMeetings.get(Meeting.MeetingStatus.CONFIRMED)) {
                System.out.println("(id=" + meeting.getId() + ") " + meeting.getTitle());
            }
        } else {
            System.out.println("Empty");
        }

        System.out.println();
        
        System.out.println("Canceled:");
        if (mappedMeetings.get(Meeting.MeetingStatus.CANCELED) != null && !mappedMeetings.get(Meeting.MeetingStatus.CANCELED).isEmpty()) {
            for (Meeting meeting : mappedMeetings.get(Meeting.MeetingStatus.CANCELED)) {
                System.out.println("(id=" + meeting.getId() + ") " + meeting.getTitle());
            }
        } else {
            System.out.println("Empty");
        }

        System.out.println();
        
        System.out.println("Running:");
        if (mappedMeetings.get(Meeting.MeetingStatus.RUNNING) != null && !mappedMeetings.get(Meeting.MeetingStatus.RUNNING).isEmpty()) {
            for (Meeting meeting : mappedMeetings.get(Meeting.MeetingStatus.RUNNING)) {
                System.out.println("(id=" + meeting.getId() + ") " + meeting.getTitle());
            }
        } else {
            System.out.println("Empty");
        }

        System.out.println();
        
        System.out.println("Finished:");
        if (mappedMeetings.get(Meeting.MeetingStatus.FINISHED) != null && !mappedMeetings.get(Meeting.MeetingStatus.FINISHED).isEmpty()) {
            for (Meeting meeting : mappedMeetings.get(Meeting.MeetingStatus.FINISHED)) {
                System.out.println("(id=" + meeting.getId() + ") " + meeting.getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        
    }*/
	
	public void listInvitation()
	{
		/*		sc = new Scanner(System.in);
		String pilih,pil, mid;
		pilih = null;
		mid = null;*/
		
		Map<Invitation.InvitationStatus, List<Invitation>> mappedInvitations = InvitationController.getInvitationsOrderByStatus(users.getEmail());
        
		System.out.println("List Invitation ");
		System.out.println("===================================");
		System.out.println();
		System.out.println("Waiting:");
        if (mappedInvitations.get(Invitation.InvitationStatus.WAITING) != null && !mappedInvitations.get(Invitation.InvitationStatus.WAITING).isEmpty()) {
            if (mappedInvitations.get(Invitation.InvitationStatus.WAITING).size() > 1) {
                Collections.sort(mappedInvitations.get(Invitation.InvitationStatus.WAITING), new Invitation());
            }

            for (Invitation invitation : mappedInvitations.get(Invitation.InvitationStatus.WAITING)) {
                System.out.println("(id=" + invitation.getMeetingId() + ") Title: " + MeetingController.getMeetingByID(invitation.getMeetingId()).getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        System.out.println();
        System.out.println("Accepted:");
        if (mappedInvitations.get(Invitation.InvitationStatus.ACCEPTED) != null && !mappedInvitations.get(Invitation.InvitationStatus.ACCEPTED).isEmpty()) {
            if (mappedInvitations.get(Invitation.InvitationStatus.ACCEPTED).size() > 1) {
                Collections.sort(mappedInvitations.get(Invitation.InvitationStatus.ACCEPTED), new Invitation());
            }

            for (Invitation invitation : mappedInvitations.get(Invitation.InvitationStatus.ACCEPTED)) {
                System.out.println("(id=" + invitation.getMeetingId() + ") " + MeetingController.getMeetingByID(invitation.getMeetingId()).getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        System.out.println();
        System.out.println("Rejected:");
        if (mappedInvitations.get(Invitation.InvitationStatus.REJECTED) != null && !mappedInvitations.get(Invitation.InvitationStatus.REJECTED).isEmpty()) {
            if (mappedInvitations.get(Invitation.InvitationStatus.REJECTED).size() > 1) {
                Collections.sort(mappedInvitations.get(Invitation.InvitationStatus.REJECTED), new Invitation());
            }

            for (Invitation invitation : mappedInvitations.get(Invitation.InvitationStatus.REJECTED)) {
                System.out.println("(id=" + invitation.getMeetingId() + ") " + MeetingController.getMeetingByID(invitation.getMeetingId()).getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        System.out.println();
        System.out.println("Canceled:");
        if (mappedInvitations.get(Invitation.InvitationStatus.CANCELED) != null && !mappedInvitations.get(Invitation.InvitationStatus.CANCELED).isEmpty()) {
            if (mappedInvitations.get(Invitation.InvitationStatus.CANCELED).size() > 1) {
                Collections.sort(mappedInvitations.get(Invitation.InvitationStatus.CANCELED), new Invitation());
            }

            for (Invitation invitation : mappedInvitations.get(Invitation.InvitationStatus.CANCELED)) {
                System.out.println("(id=" + invitation.getMeetingId() + ") " + MeetingController.getMeetingByID(invitation.getMeetingId()).getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        System.out.println();
        System.out.println("Confirmed:");
        if (mappedInvitations.get(Invitation.InvitationStatus.CONFIRMED) != null && !mappedInvitations.get(Invitation.InvitationStatus.CONFIRMED).isEmpty()) {
            if (mappedInvitations.get(Invitation.InvitationStatus.CONFIRMED).size() > 1) {
                Collections.sort(mappedInvitations.get(Invitation.InvitationStatus.CONFIRMED), new Invitation());
            }

            for (Invitation invitation : mappedInvitations.get(Invitation.InvitationStatus.CONFIRMED)) {
                System.out.println("(id=" + invitation.getMeetingId() + ") " + MeetingController.getMeetingByID(invitation.getMeetingId()).getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        System.out.println();
        System.out.println("Finished:");
        if (mappedInvitations.get(Invitation.InvitationStatus.FINISHED) != null && !mappedInvitations.get(Invitation.InvitationStatus.FINISHED).isEmpty()) {
            if (mappedInvitations.get(Invitation.InvitationStatus.FINISHED).size() > 1) {
                Collections.sort(mappedInvitations.get(Invitation.InvitationStatus.FINISHED), new Invitation());
            }

            for (Invitation invitation : mappedInvitations.get(Invitation.InvitationStatus.FINISHED)) {
                System.out.println("(id=" + invitation.getMeetingId() + ") " + MeetingController.getMeetingByID(invitation.getMeetingId()).getTitle());
            }
        } else {
            System.out.println("Empty");
        }
        
        System.out.println();
        /*System.out.println("MENU :");
        System.out.println("1. detail-invitation <meeting_id>");
        System.out.println("2. back");
		System.out.print(" Enter your choice :");
		
		pilih = sc.nextLine();
		String choice[] = pilih.split(" ");
        pil = choice[0];
        if (choice.length > 1) {
            mid = choice[1];
        }
		
		switch(pil) {
			case "back":
				menuParticipant();
				break;
			case "detail-invitation":
                detailInvitation(mid);
				break;
			default: 
				System.out.println("Sorry, Menu not found");
		}*/
	}
	
	public void detailInvitation(String meeting_id) 
	 {
		 //int pilih=0;
		// Invitation invitation = InvitationController.findInvitation(meeting_id, this.users.getEmail());
		 System.out.println();
		 System.out.println("Detail Invitation");
	     System.out.println("===================================");
		 System.out.println();
	    /*    if (invitation != null) {
	            invitation.view();
	        } else {
	            System.out.println("Invitation not found");
	        }
*/
	      System.out.println("");
	      System.out.println("Detail Meeting "+ meeting_id);
	      System.out.println("-----------------------------------");
	     // MeetingController.getMeetingByID(Integer.toString(meeting_id));
	        
	      System.out.println();
	      /*System.out.println("MENU :");
	      System.out.println("1. Help");
	      System.out.println("2. Exit");
	      System.out.print(" Enter your choice :");
		  try 
		  {
			  pilih = sc.nextInt();
		  } catch (Exception e) 
		  {
			 System.out.println("Invalid input");
		  }
		  	 System.out.println();
			
		  switch(pilih) 
		  {
		  	case 1:
				//menuParticipant();
		  		showHelp();
				break;
			case 2:
				break;
			default: 
				System.out.println("Sorry, Menu not found");
		  }*/
	 }
	
	public void acceptInvitation(String meeting_id) 
	{
        Invitation invitation = InvitationController.findInvitation(meeting_id, users.getEmail());
        
        // Mendapatkan tanggal hari ini
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.clear(Calendar.AM_PM);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
       
        
        try {
			if(!today.getTime().after(formatter.parse(MeetingController.getMeetingByID(meeting_id).getProposedStartDate()))) {
			    switch (invitation.getInvitationStatus()) {
			        case WAITING:
			        case REJECTED:
			            SimpleDateFormat sdfd = new SimpleDateFormat("yyyy/MM/dd");
			            sdfd.setLenient(false);
			            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH");
			            sdf.setLenient(false);

			            List<String> availabilities;
			            List<Date> date;
			            boolean isAvailabilityValid;
			            String temp;
			            Scanner in = new Scanner(System.in);

			            do {
			                isAvailabilityValid = true;
			                
			                // Menanyakan ketersediaan waktu participant
			                System.out.print("Availability, separate with comma (yyyy/MM/dd HH): ");
			                temp = in.nextLine();
			                
			                availabilities = Arrays.asList(temp.split(","));
			                date = new ArrayList<>();

			                for(String availability : availabilities) {
			                    try {
			                        if(!sdfd.parse(availability).before(formatter.parse(MeetingController.getMeetingByID(meeting_id).getProposedStartDate())) && !sdfd.parse(availability).after(formatter.parse(MeetingController.getMeetingByID(meeting_id).getProposedEndDate()))) {
			                            date.add(sdf.parse(availability));
			                        } else {
			                            isAvailabilityValid = false;
			                            System.out.println("You gave an invalid date");
			                        }
			                    } catch (ParseException ex) {
			                        isAvailabilityValid = false;
			                        System.out.println("Please input the date in valid format");
			                    }
			                }
			            } while (!isAvailabilityValid);

			            // Menambahkan availibilitas participant
			            InvitationController.editAvailability(meeting_id, users.getEmail(), date);
			            InvitationController.acceptInvitation(meeting_id, users.getEmail());
			            System.out.println("Invitation accepted");
			            break;
			        case ACCEPTED:
			        case CONFIRMED:
			            System.out.println("You have already accepted the invitation.");
			            break;
			        case CANCELED:
			            System.out.println("The invitation is canceled.");
			            break;
			        case FINISHED:
			            System.out.println("The meeting is finished already.");
			            break;
			        default:
			            break;
			    }
			} else {
			    System.out.println("You've missed the deadline. The deadline is on: " + MeetingController.getMeetingByID(meeting_id).getMaxResponseDate());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void rejectInvitation(String meeting_id) {
        Invitation invitation = InvitationController.findInvitation(meeting_id, users.getEmail());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        
        // Mendapatkan tanggal hari ini
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.clear(Calendar.AM_PM);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        
        try {
			if(!today.getTime().after(formatter.parse(MeetingController.getMeetingByID(meeting_id).getProposedStartDate()))) {
			    switch (invitation.getInvitationStatus()) {
			        case WAITING:
			        case ACCEPTED:
			        case CONFIRMED:
			            // Mengubah status menjadi rejected
			            InvitationController.rejectInvitation(meeting_id, users.getEmail());
			            break;
			        case REJECTED:
			            System.out.println("You already have rejected the invitation.");
			            break;
			        case CANCELED:
			            System.out.println("The invitation is canceled.");
			            break;
			        case FINISHED:
			            System.out.println("The meeting is finished already.");
			            break;
			        default:
			            break;
			    }
			} else {
			    System.out.println("You've missed the deadline. The deadline is on: " + MeetingController.getMeetingByID(meeting_id).getMaxResponseDate());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public static void main(String[]args)
	 {
	//	menuParticipant();
	 }
}
