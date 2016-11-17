package com.controller.meeting;

import java.util.ArrayList;
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
		ArrayList<MeetingParticipant> arrMP = m.getMeetingParticipant();
		arrMP.add(mp);
		meetingDraft.setMeetingParticipant(arrMP);
	}
	
	public ArrayList<MeetingParticipant> getParticipantList() {
		return meetingDraft.getMeetingParticipant();
	}
	
	public Meeting getMeetingDraft() {
		return meetingDraft;
	}
	
}
