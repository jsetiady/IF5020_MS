package com.model.meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jessiesetiady
 *
 */
public class MeetingInvitation {
	private int meetingID;
	private String invitationDate;
	private MeetingParticipant mp;
	private String message;
	private boolean responseStatus;
	
	public MeetingInvitation(int meetingID, MeetingParticipant mp, String message) {
		this.meetingID = meetingID;
		this.mp = mp;
		this.message = message;
		this.responseStatus = false;
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Date date = new Date();
		this.invitationDate = dateFormat.format(date);

	}
	
	public String getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(String invitationDate) {
		this.invitationDate = invitationDate;
	}

	public boolean isResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(boolean responseStatus) {
		this.responseStatus = responseStatus;
	}

	public MeetingInvitation() {
		
	}

	public int getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(int meetingID) {
		this.meetingID = meetingID;
	}

	public MeetingParticipant getMp() {
		return mp;
	}

	public void setMp(MeetingParticipant mp) {
		this.mp = mp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

