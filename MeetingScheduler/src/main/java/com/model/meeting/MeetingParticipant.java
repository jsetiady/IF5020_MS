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
	static int REJECT = 1;
	
	//variable
	private String email = "";
	private boolean isImportant = false;
	private int response;
	private String responseDate = "";
	
	public MeetingParticipant(String email, boolean isImportant) {
		this.email = email;
		this.isImportant = isImportant;
		response = PENDING;
	}
	
	public MeetingParticipant(String email) {
		this(email, false);
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public boolean isImportant() {
		return this.isImportant;
	}
	
	public int getResponse() {
		return this.response;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setIsImportant(boolean isImportant) {
		this.isImportant = isImportant;
	} 
	
}
