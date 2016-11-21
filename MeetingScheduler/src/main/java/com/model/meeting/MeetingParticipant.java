package com.model.meeting;

import java.util.Date;

/**
 * @author jessiesetiady
 *
 */
public class MeetingParticipant {
	
	//constant
	static int PENDING = -1;
	static int ACCEPT = 1;
	static int REJECT = 0;
	
	//variable
	private String email = "";
	private boolean isImportant = false;
	private int response = PENDING;
	private String responseDate = "";
	
	public MeetingParticipant(String email, boolean isImportant) {
		this.email = email;
		this.isImportant = isImportant;
		this.responseDate = "--/--/----";
		response = PENDING;
	}
	
	public MeetingParticipant() {}
	
	public MeetingParticipant(String email) {
		this(email, false);
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
	
	
	
	
	
}
