package com.controller.meeting;

import java.util.Date;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingParticipant;

/**
 * @author jessiesetiady
 *
 */
public class MeetingController {
	private Meeting m, meetingDraft;
	
	public void createMeetingDraft(String title, String agenda, String location,
			int duration, String proposedStartDate, String proposedEndDate,
			String maxReponseDate, String maxResponseTime, String meetingInitiator) {
		
		meetingDraft = new Meeting(getNextMeetingID(), title, agenda, location,
				duration, proposedStartDate, proposedEndDate,
				maxReponseDate, maxResponseTime, meetingInitiator);
		
	}
	
	public int getNextMeetingID() {
		return 1;
	}
	
	public void addMeetingParticipant(String email, boolean isImportant) {
		MeetingParticipant mp = new MeetingParticipant(email, isImportant);
		
	}
	
	public Meeting getMeetingDraft() {
		return this.meetingDraft;
	}
	
}
