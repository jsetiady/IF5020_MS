package com.model.meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jessiesetiady
 *
 */
public class MeetingParticipant {
	
	//constant
	public static int PENDING = -1;
	public static int ACCEPT = 1;
	public static int REJECT = 0;
	
	//variable
	private int meetingID;
	private String email = "";
	private boolean isImportant = false;
	private int response = PENDING;
	private String responseDate = "";
	private String invitationDate;
	private String invitationMsg;
	
	public MeetingParticipant(int meetingID, String email, boolean isImportant) {
		this.email = email;
		this.isImportant = isImportant;
		this.responseDate = "--/--/----";
		this.meetingID = meetingID;
		response = PENDING;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		this.invitationDate = dateFormat.format(date);
		this.invitationMsg = "";
	}
	
	public MeetingParticipant() {}
	
	public MeetingParticipant(int meetingID, String email) {
		this(meetingID, email, false);
	}
	
	public void setMeetingID(int meetingID) {
		this.meetingID = meetingID;
	}

	public int getMeetingID() {
		return meetingID;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(String invitationDate) {
		this.invitationDate = invitationDate;
	}

	public String getInvitationMsg() {
		return invitationMsg;
	}

	public void setInvitationMsg(String invitationMsg) {
		this.invitationMsg = invitationMsg;
	}
	
	
	
	
	
}
