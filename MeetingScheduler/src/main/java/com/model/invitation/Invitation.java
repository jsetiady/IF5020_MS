package com.model.invitation;

/**
 * @author Siti_Rozani
 *
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
//import com.controller.meeting.MeetingController;
//import com.model.meeting.Meeting;

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
	/*	Meeting met1 = MeetingController.getMeetingByID(o1.getMeetingId());
        Meeting met2 = MeetingController.getMeetingByID(o2.getMeetingId());*/

        return 1;
	}

	public void view() {
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder build;
        String a;
        build = new StringBuilder();
        a = "";
        
        System.out.println("Meeting id: " + meetingId);
        System.out.println("To: " + to);
        System.out.print("Availability: ");

        for (Date date : availability) {
            build.append(a).append(date);
            a = ",";
        }
        
        System.out.println(build);
        System.out.println("Status: " + status);
		
	}
}