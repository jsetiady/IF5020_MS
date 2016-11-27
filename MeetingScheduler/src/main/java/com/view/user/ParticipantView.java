package com.view.user;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.controller.invitation.InvitationController;
import com.model.invitation.Invitation;
import com.model.user.User;
import com.controller.meeting.MeetingController;

/**
 * @author Siti_R
 *
 */

public class ParticipantView 
{
	
	static User users = new User();
	InvitationController pc = new InvitationController();
	private static Scanner sc;
	
	public static void menuParticipant()
	{
		sc = new Scanner(System.in);
		int pilih = 0;
		
		System.out.println("=================================");
		System.out.println("Hello, "/*+ user.getFirstName()*/);
		System.out.println("=================================");
		System.out.println("| \t\t\t\t|");
		System.out.println("| 1. List Invitation\t\t|");
		System.out.println("| 2. Logout\t\t\t|");
		System.out.println("|\t\t\t\t|");
		System.out.println("=================================");
		System.out.print(" Enter your choice :");
		
		try {
			pilih = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input");
		}
		System.out.println();
		
		switch(pilih) {
			case 1:
				listInvitation();
				break;
			case 2:
				break;
			default: 
				System.out.println("Sorry, Menu not found");
		}
		
	}
	
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
	
	public static void listInvitation()
	{
		Map<Invitation.InvitationStatus, List<Invitation>> mappedInvitations = InvitationController.getInvitationsOrderByStatus(users.getEmail());
        
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

	}
	
	public static void main(String[]args)
	{
		menuParticipant();
	}
}
