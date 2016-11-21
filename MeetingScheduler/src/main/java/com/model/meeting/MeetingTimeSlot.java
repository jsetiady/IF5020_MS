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
		//numOrdinaryParticipant = numOrdinaryParticipant; //expected yang harus hadir
		//numImportantParticipant = numImportantParticipant; //expected yang harus hadir
		
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
	
	
}