package com.model.meeting;

import java.util.Date;
import java.util.List;
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
	private String proposedDateRange;
	private String negotiationDeadline;
	private List<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
	private List<MeetingParticipant> meetingParticipant = new ArrayList<MeetingParticipant>();
	private int meetingStatus;
	private String meetingInitiator;
	private String createdDate;
	private String scheduledDate;
	private String proposedStartDate;
	private String proposedEndDate;
	
	//meetingstatus
	public int NEGOTIATING = 0;
	public int CANCELED = -9;
	public int SCHEDULED = 1;
	public int RUNNING = 2;
	public int FINISH = 3;
	
	public Meeting(int id, String title, String agenda, String location,
			int duration, String proposedDateRange, String negotiationDeadline, String meetingInitiator, String createdDate) {
		
		this.id = id; //id generated in Meeting Controller
		this.title = title;
		this.agenda = agenda;
		this.location = location;
		this.duration = duration;
		this.proposedDateRange = proposedDateRange;
		this.negotiationDeadline = negotiationDeadline;
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

	public List<MeetingTimeSlot> getMeetingTimeSlots() {
		return meetingTimeSlots;
	}

	public String getProposedDateRange() {
		return proposedDateRange;
	}

	public void setProposedDateRange(String proposedDateRange) {
		this.proposedDateRange = proposedDateRange;
	}

	public String getNegotiationDeadline() {
		return negotiationDeadline;
	}

	public void setNegotiationDeadline(String negotiationDeadline) {
		this.negotiationDeadline = negotiationDeadline;
	}

	public void setMeetingTimeSlots(List<MeetingTimeSlot> meetingTimeSlots2) {
		this.meetingTimeSlots = meetingTimeSlots2;
	}

	public List<MeetingParticipant> getMeetingParticipant() {
		return meetingParticipant;
	}

	public void setMeetingParticipant(List<MeetingParticipant> meetingParticipant) {
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
	
	public String getProposedStartDate() {
		return this.proposedDateRange.split(" - ")[0];
	}
	
	public String getProposedEndDate() {
		return this.proposedDateRange.split(" - ")[1];
	}
	
	public void setProposedStartDate(String proposedStartDate) {
		this.proposedStartDate = proposedStartDate;
	}
	
	public void setProposedEndDate(String proposedEndDate) {
		this.proposedEndDate = proposedEndDate;
	}
	
}
