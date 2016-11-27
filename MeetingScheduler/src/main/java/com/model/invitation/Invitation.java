package com.model.invitation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Siti_R
 */

public class Invitation implements Comparator<Invitation> 
{
	// Attributes
    private String meetingId;
    private String to;
    private List<Date> availability;
    private InvitationStatus status;

    public enum InvitationStatus 
    {
        WAITING, ACCEPTED, REJECTED, CANCELED, CONFIRMED, FINISHED
    }
    
    // Constructor
    public Invitation() 
    {
        this.to = "";
        this.availability = null;
        this.status = InvitationStatus.WAITING;
    }

    public Invitation(String meetingId, String to) 
    {
        this.meetingId = meetingId;
        this.to = to;
        this.availability = new ArrayList<>();
        this.status = InvitationStatus.WAITING;
    }

    // Getters
    public String getMeetingId() 
    {
        return meetingId;
    }

    public String getTo() 
    {
        return to;
    }

    public List<Date> getAvailability() 
    {
        return availability;
    }

    public InvitationStatus getInvitationStatus() 
    {
        return status;
    }

    // Setters
    public void setMeetingId(String meetingId) 
    {
        this.meetingId = meetingId;
    }

    public void setTo(String to) 
    {
        this.to = to;
    }

    public void setAvailability(List<Date> availability) 
    {
        this.availability = availability;
    }

    public void setInvitationStatus(InvitationStatus status) 
    {
        this.status = status;
    }

	@Override
	public int compare(Invitation o1, Invitation o2) {
		
		return 0;
	}
}