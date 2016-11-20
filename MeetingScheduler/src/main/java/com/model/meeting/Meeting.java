package com.model.meeting;

import java.util.Date;
import java.util.ArrayList;

/**
 * @author jessiesetiady
 * 
 */

public class Meeting {
	private int id;
	private String title;
	private String agenda;
	private String location;
	private int duration;
	private String proposedStartDate;
	private String proposedEndDate;
	private String maxResponseDate;
	private String maxResponseTime;
	private ArrayList<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
	private ArrayList<MeetingParticipant> meetingParticipant = new ArrayList<MeetingParticipant>();
	private int meetingStatus;
	private String meetingInitiator;
	private String createdDate;
	private String scheduledDate;
	
	//meetingstatus
	public int NEGOTIATING = 0;
	public int CANCELED = -9;
	public int SCHEDULED = 1;
	public int RUNNING = 2;
	public int FINISH = 3;
	
	//constructor
	public Meeting(int id, String title, String agenda, String location,
			int duration, String proposedStartDate, String proposedEndDate,
			String maxResponseDate, String maxResponseTime, String meetingInitiator, String createdDate) {
		
		this.id = id; //id generated in Meeting Controller
		this.title = title;
		this.agenda = agenda;
		this.location = location;
		this.duration = duration;
		this.proposedStartDate = proposedStartDate;
		this.proposedEndDate = proposedEndDate;
		this.maxResponseDate = maxResponseDate;
		this.maxResponseTime = maxResponseTime;
		this.meetingInitiator = meetingInitiator;
		this.meetingStatus = 0;
		this.createdDate = createdDate;
		this.scheduledDate = "-         ";
	}
	
	public Meeting() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getProposedStartDate() {
		return proposedStartDate;
	}

	public void setProposedStartDate(String proposedStartDate) {
		this.proposedStartDate = proposedStartDate;
	}

	public String getProposedEndDate() {
		return proposedEndDate;
	}

	public void setProposedEndDate(String proposedEndDate) {
		this.proposedEndDate = proposedEndDate;
	}

	public String getMaxResponseDate() {
		return maxResponseDate;
	}

	public void setMaxResponseDate(String maxResponseDate) {
		this.maxResponseDate = maxResponseDate;
	}
	
	public String getMaxResponseTime() {
		return maxResponseTime;
	}

	public void setMaxResponseTime(String maxResponseTime) {
		this.maxResponseTime = maxResponseTime;
	}

	public ArrayList<MeetingTimeSlot> getMeetingTimeSlots() {
		return meetingTimeSlots;
	}

	public void setMeetingTimeSlots(ArrayList<MeetingTimeSlot> meetingTimeSlots) {
		this.meetingTimeSlots = meetingTimeSlots;
	}

	public ArrayList<MeetingParticipant> getMeetingParticipant() {
		return meetingParticipant;
	}

	public void setMeetingParticipant(ArrayList<MeetingParticipant> meetingParticipant) {
		this.meetingParticipant = meetingParticipant;
	}

	public int getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(int meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getMeetingInitiator() {
		return meetingInitiator;
	}

	public void setMeetingInitiator(String meetingInitiator) {
		this.meetingInitiator = meetingInitiator;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	
	
	
	// begin setter & getter
	
	
	
	/*
	public void runScheduler() {
		Meeting m;
		
		//generate slots
		//jumlah slot pada satu hari ada 10 (10 jam)
		//slot = 10/durasi 
		
		int total_days = Days.daysBetween(m.getStartDate(), m.getEndDate()).getDays(); // get total days between date range
		
		date = m.getStartDate();
		while(date.before(m.getEndDate())) {
			for(int i=0;i<slots.size();i++) { //slots.size() = 10
				
			}
			Date next_date = new Date(date.getTime() + (1000 * 60 * 60 * 24));
			date = next_date;
		}
		
	}
	*/
	
}
