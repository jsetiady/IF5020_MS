package com.model.meeting;

import java.util.ArrayList;

class MeetingTimeSlot {
	private String startTime;
	private String endTime;
	private ArrayList<String> ordinaryParticipants;
	private ArrayList<String> importantParticipants;
	private int numOrdinaryParticipant;
	private int numImportantParticipant;
	
	public MeetingTimeSlot(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		numOrdinaryParticipant = 0;
		numImportantParticipant = 0;
		
		ordinaryParticipants = new ArrayList<String>();
		importantParticipants = new ArrayList<String>();
	}
	
	public MeetingTimeSlot() {}
	
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public 
	
}