package com.controller.invitation;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.model.invitation.Invitation;
import com.model.invitation.Invitation.InvitationStatus;

/**
 * @author Siti_R
 *
 */

public class InvitationController 
{
	public static void acceptInvitation(int idMeeting, String p)
	{
		
	}
	
	public static void rejectInvitation(int idMeeting, String p)
	{
		
	}
	
	public static void cencelInvitation(int idMeeting, String p)
	{
		
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
    //                mappedInvitations.put(invitation.getInvitationStatus(), new ArrayList<>());
                }

                if(invitation.getTo().equals(email)) {
                    mappedInvitations.get(invitation.getInvitationStatus()).add(invitation);
                }
            }
        }
        
        return mappedInvitations;
    }
        
	private static List<Invitation> loadInvitations() {
		// TODO Auto-generated method stub
		return null;
	}
}
