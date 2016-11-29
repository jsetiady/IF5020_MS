package com.model.meeting;

import java.util.ArrayList;

public class MeetingTimeSlot {
	private String startTime;
	private String endTime;
	private String date;
	private ArrayList<String> ordinaryParticipants;
	private ArrayList<String> importantParticipants;
	private int numOrdinaryParticipant;
	private int numImportantParticipant;
	
	public MeetingTimeSlot(String date, String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
		numOrdinaryParticipant = 0;
		numImportantParticipant = 0;
		
		ordinaryParticipants = new ArrayList<String>();
		importantParticipants = new ArrayList<String>();
	}
	
	public MeetingTimeSlot() {}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}

	public ArrayList<String> getOrdinaryParticipants() {
		return ordinaryParticipants;
	}

	public void setOrdinaryParticipants(ArrayList<String> ordinaryParticipants) {
		this.ordinaryParticipants = ordinaryParticipants;
	}

	public ArrayList<String> getImportantParticipants() {
		return importantParticipants;
	}

	public void setImportantParticipants(ArrayList<String> importantParticipants) {
		this.importantParticipants = importantParticipants;
	}

	public int getNumOrdinaryParticipant() {
		return numOrdinaryParticipant;
	}

	public void setNumOrdinaryParticipant(int numOrdinaryParticipant) {
		this.numOrdinaryParticipant = numOrdinaryParticipant;
	}

	public int getNumImportantParticipant() {
		return numImportantParticipant;
	}

	public void setNumImportantParticipant(int numImportantParticipant) {
		this.numImportantParticipant = numImportantParticipant;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}