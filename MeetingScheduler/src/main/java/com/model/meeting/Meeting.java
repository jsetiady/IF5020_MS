package com.model.meeting;

import java.util.Date;
import java.util.ArrayList;

/**
 * @author jessiesetiady
 * 
 */

public class Meeting {
	private int id; //supaya lebih mudah dicari
	private String title;
	private String agenda;
	private String location;
	private String duration;
	private Date proposedStartDate;
	private Date proposedEndDate;
	private Date negotiationDeadline;
	private ArrayList<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
	private ArrayList<MeetingParticipant> meetingParticipant = new ArrayList<MeetingParticipant>();
	private int meetingStatus;
	
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
