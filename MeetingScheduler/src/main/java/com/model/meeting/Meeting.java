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
	private String negotiationDeadline;
	private ArrayList<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
	private ArrayList<MeetingParticipant> meetingParticipant = new ArrayList<MeetingParticipant>();
	private int meetingStatus;
	
	//constructor
	public Meeting(String title, String agenda, String location, int duration, String proposedStartDate, String proposedEndDate, String negotiationDeadline) {
		this.title = title;
		this.agenda = agenda;
		this.location = location;
		this.duration = duration;
		this.proposedStartDate = proposedStartDate;
		this.proposedEndDate = proposedEndDate;
		this.negotiationDeadline = negotiationDeadline;
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
