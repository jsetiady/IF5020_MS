package com.controller.invitation;

/**
 * @author Siti_Rozani
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.invitation.Invitation;
import com.model.invitation.Invitation.InvitationStatus;

/**
 * @author Siti_R
 *
 */

public class InvitationController 
{
	private static String invitationPath = "resources/invitations.json";
	public static void acceptInvitation(String meeting_id, String p)
	{
		List<Invitation> invitations = loadInvitations();
        Invitation invitation = null;
        boolean found = false;
        int i = 0;
        
        if(invitations != null) {
            while(!found && i < invitations.size()) {
                if(invitations.get(i).getMeetingId() == meeting_id && invitations.get(i).getTo().equals(p)) {
                    found = true;
                    invitation = invitations.get(i);
                }
                
                ++i;
            }
        }
        
        invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
        saveInvitations(invitations);
	}
	
	public static void rejectInvitation(String meeting_id, String p)
	{
		List<Invitation> invitations = loadInvitations();
        Invitation invitation = null;
        boolean found = false;
        int i = 0;
        
        if(invitations != null) {
            while(!found && i < invitations.size()) {
                if(invitations.get(i).getMeetingId() == meeting_id && invitations.get(i).getTo().equals(p)) {
                    found = true;
                    invitation = invitations.get(i);
                }
                
                ++i;
            }
        }
        
        invitation.setInvitationStatus(InvitationStatus.REJECTED);
        saveInvitations(invitations);
	}
	
	public static void cencelInvitation(String meeting_id, String p)
	{
		List<Invitation> invitations = loadInvitations();
        Invitation invitation = null;
        boolean found = false;
        int i = 0;
        
        if(invitations != null) {
            while(!found && i < invitations.size()) {
                if(invitations.get(i).getMeetingId() == meeting_id && invitations.get(i).getTo().equals(p)) {
                    found = true;
                    invitation = invitations.get(i);
                }
                
                ++i;
            }
        }
        
        invitation.setInvitationStatus(InvitationStatus.CANCELED);
        saveInvitations(invitations);
	}
	
	public static void viewInvitation(String user)
	{
		
	}

	public static Map<InvitationStatus, List<Invitation>> getInvitationsOrderByStatus(String email) {
        List<Invitation> invitations =  loadInvitations();
        Map<InvitationStatus, List<Invitation>> mappedInvitations;
        mappedInvitations = new HashMap<>();
        
        if (invitations != null) {
            for (Invitation invitation : invitations) {
                if (mappedInvitations.get(invitation.getInvitationStatus()) == null) 
                {
                   mappedInvitations.put(invitation.getInvitationStatus(), null);
                }

                if(invitation.getTo().equals(email)) {
                    mappedInvitations.get(invitation.getInvitationStatus()).add(invitation);
                }
            }
        }
        
        return mappedInvitations;
    }
	
	public static void editAvailability(String meeting_id, String email, List<Date> date) {
		List<Invitation> invitations = loadInvitations();
        Invitation invitation = null;
        boolean found = false;
        int i = 0;
        
        if(invitations != null) {
            while(!found && i < invitations.size()) {
                if(invitations.get(i).getMeetingId() == meeting_id  && invitations.get(i).getTo().equals(email)) {
                    found = true;
                    invitation = invitations.get(i);
                }
                
                ++i;
            }
        }
        
        invitation.setAvailability(date);
        saveInvitations(invitations);
		
	}
	
	private static void saveNewInvitation(Invitation invitation) {
        List<Invitation> invitations = loadListFromJson(invitationPath, Invitation[].class);
        if (invitations == null) {
            invitations = new ArrayList<>();
        }
        invitations.add(invitation);
        saveListToJson(invitationPath, invitations);
    }

    private static void saveInvitations(List<Invitation> invitations) {
        saveListToJson(invitationPath, invitations);
    }
        
	private static List<Invitation> loadInvitations() {
        return loadListFromJson(invitationPath, Invitation[].class);
    }
	
	public static <T> ArrayList<T> loadListFromJson(String fileName, Class<?> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            T[] array = (T[]) mapper.readValue(new String(Files.readAllBytes(Paths.get(fileName))), clazz);
            return new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            
        }
        
        return null;
    }
	
	public static <T> void saveListToJson(String fileName, List<T> list) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
                writer.write(mapper.writeValueAsString(list.toArray()));
            }
        } catch (IOException e) {
            
        }
    }

	public static Invitation findInvitation(String meeting_id, String email) 
	{
		List<Invitation> invitations = loadInvitations();
		Invitation invitation = null;
		boolean found = false;
		int i = 0;
		
		if (invitations != null) {
	            while(!found && i < invitations.size()) {
	                if(invitations.get(i).getMeetingId() == meeting_id && invitations.get(i).getTo().equals(email)) {
	                    found = true;
	                    invitation = invitations.get(i);
	                }
	                ++i;
	            }
		}
	        
		return invitation;
	}
}
