package com.controller.meeting;

import java.io.File;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingInvitation;
import com.model.meeting.MeetingParticipant;
import com.model.meeting.MeetingTimeSlot;
import com.utilities.JSONParser;
import com.utilities.Validator;

/**
 * @author jessiesetiady
 *
 */
public class MeetingController {
	private Meeting meetingDraft;
	private Validator validator = new Validator();
	
	private JSONParser<Meeting> jParserMeeting = new JSONParser<Meeting>(Meeting.class);
	private JSONParser<Integer> jParserInteger = new JSONParser<Integer>(Integer.class);
	private JSONParser<MeetingInvitation> jParserInvitation = new JSONParser<MeetingInvitation>(MeetingInvitation.class);
	private JSONParser<Integer> jParserMeetingID = new JSONParser<Integer>(Integer.class);
	
	private String fileMeetingData = "resources/meetingdata.json";
	private String fileMeetingId = "resources/meeting_id.json";
	private String fileMeetingInvitation = "resources/invitations.json";
	private String fileLastMeetingID = "resources/lastid";
	
	public void createMeetingDraft(String title, String agenda, String location,
			int duration, String proposedDateRange, String negotiationDeadline, String meetingInitiator, String createdDate) {
		
		meetingDraft = new Meeting(getNextMeetingID(), title, agenda, location,
				duration, proposedDateRange, negotiationDeadline, meetingInitiator, createdDate);
		
	}
	
	public int getNextMeetingID() {
		int id = 0;
		
		id = jParserMeetingID.loadObj(fileLastMeetingID);
		id++;
		
		jParserMeetingID.writeObj(id, fileLastMeetingID);
		
		return id;
	}
	
	public void addMeetingParticipant(String email, boolean isImportant) {
		MeetingParticipant mp = new MeetingParticipant(meetingDraft.getId(), email, isImportant);
		List<MeetingParticipant> arrMP = meetingDraft.getMeetingParticipant();
		if(!findMeetingParticipantByEmail(arrMP, mp.getEmail())) {
			arrMP.add(mp);
		}
		meetingDraft.setMeetingParticipant(arrMP);
	}
	
	public List<MeetingParticipant> getParticipantList() {
		return meetingDraft.getMeetingParticipant();
	}
	
	public Meeting getMeetingDraft() {
		return this.meetingDraft;
	}
	
	public boolean findMeetingParticipantByEmail(List<MeetingParticipant> arrMP, String email) {
		for(int i=0;i<arrMP.size();i++) {
			if(arrMP.get(i).getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	
	public String dateArithmetic(String strDate, int i) {
		String dt = strDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			System.out.println("Error when parsing date");
			e.printStackTrace();
		}
		c.add(Calendar.DATE, i);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		return dt;
	}
	
	 public int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24)) + 1;
	}
	
	public void saveMeetingCreation() {
		List<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
		//generate time slot
		meetingTimeSlots = generateMeetingTimeSlot(meetingDraft.getDuration(), meetingDraft.getProposedStartDate(), meetingDraft.getProposedEndDate());
		
		//generate invitation
		//List<MeetingInvitation> mis = generateMeetingInvitation(meetingDraft.getId(), meetingDraft.getMeetingParticipant(), meetingDraft.getMeetingInitiator());
		//saveMeetingInvitation(mis);
		
		//add to meeting instance
		meetingDraft.setMeetingTimeSlots(meetingTimeSlots);
		//meetingDraft.setMeetingInvitation(mis);
		
		//save meetingDraft to json
		System.out.println("Meeting id: M" + meetingDraft.getId());
		
		//save meeting draft file
		jParserMeeting.append(fileMeetingData, meetingDraft);
		
		//add new meeting id to json meeting_id
		jParserInteger.append(fileMeetingId, (Integer) meetingDraft.getId());
	}
	
	
	public List<Meeting> getListOfCreatedMeeting(String initiator) {
		List<Meeting> createdMeetingList = new ArrayList<Meeting>();
		List<Meeting> meetingList = new ArrayList<Meeting>();
		
		meetingList = jParserMeeting.load(fileMeetingData);
		for(int i=0;i<meetingList.size();i++) {
			if(meetingList.get(i).getMeetingInitiator().equals(initiator)) {
				createdMeetingList.add(meetingList.get(i));
			}
		}
			
		return createdMeetingList;
	}
	
	public String getStrMeetingStatus(int meetingStatus) {
		String str = "";
		switch(meetingStatus) {
			case '0': str = "NEGOTIATING"; break;
			case '9': str = "CANCELED"; break;
			case '1': str = "CONFIRMED"; break;
			case '2': str = "RUNNING"; break;
			case '3': str = "FINISHED"; break;
		}
		return str;
	}
	
	public Meeting getMeetingByID(String meetingID) {
		List<Meeting> meeting = new ArrayList<Meeting>();
		
		if(meetingID.substring(1)!=null) {
			if(validator.isValidNumber(meetingID.substring(1))) {
				meeting = jParserMeeting.load(fileMeetingData);
				for(int i=0;i<meeting.size();i++) {
					if(meeting.get(i).getId() == Integer.parseInt(meetingID.substring(1))) {
						return meeting.get(i);
					}
				}
			}
		}
		
		return null;
	}
	
	
	public List<MeetingParticipant> getInvitationListByEmail(String email) {
		List<Meeting> m = new ArrayList<Meeting>();
		List<MeetingParticipant> mp = new ArrayList<MeetingParticipant>();
		List<MeetingParticipant> invitation = new ArrayList<MeetingParticipant>();
		m = jParserMeeting.load(fileMeetingData);
		for(int i=0;i<m.size();i++) {
			mp = m.get(i).getMeetingParticipant();
			for(int j=0;j<mp.size();j++) {
				if(mp.get(j).getEmail().equals(email)) {
					invitation.add(mp.get(j));
				}
			}
		}
		return invitation;
	}
	
	
	public boolean rejectInvitation(String meetingID, String email) {
		//yang harus di update klo reject
		// meeting participant --> response = reject
		
		
		
		return true;
		
	}
	
	public List<Meeting> getListOfScheduledMeetingByEmail(String participant) {
		List<Meeting> meetingList = new ArrayList<Meeting>();
		List<Meeting> scheduledMeetingList = new ArrayList<Meeting>();
		List<MeetingParticipant> participantList = new ArrayList<MeetingParticipant>();
		
		meetingList = jParserMeeting.load(fileMeetingData);
		
		for(int i=0;i<meetingList.size();i++) {
			if(meetingList.get(i).getMeetingStatus()==meetingList.get(i).SCHEDULED) {
				participantList = meetingList.get(i).getMeetingParticipant();
				
				for(int j=0;j<participantList.size();j++) {
					if(participantList.get(j).getEmail().equals(participant)) {
						//add Meeting to scheduledMeetingList
						scheduledMeetingList.add(meetingList.get(i));
					}
				}
			}
		}
		return scheduledMeetingList;
	}
	
	
	
	//invitation
	
	public List<MeetingTimeSlot> generateMeetingTimeSlot(int duration, String meetingStartDate, String meetingEndDate) {
		String[] startTime = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};
		String[] endTime = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
		int numOfTimeSlotPerDay, numOfTimeSlot;
		String start;
		List<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
		numOfTimeSlotPerDay = 9-duration+1;
		
		
		//String to date
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	    Date startDate = null, endDate = null;
	    try {
	        startDate = df.parse(meetingStartDate);
	        endDate = df.parse(meetingEndDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		
		int days = daysBetween(startDate, endDate);
		
		numOfTimeSlot = days * numOfTimeSlotPerDay;
		
		System.out.println("\n Result: ");
		System.out.println(numOfTimeSlot + " time slot for this meeting has been generated");
		
		start = meetingStartDate;
		for(int i=0; i<days; i++) {
			int j = 0;
			while(j<numOfTimeSlotPerDay) {
				meetingTimeSlots.add(new MeetingTimeSlot(start, startTime[j], endTime[j+duration-1]));
				j++;
			}
			start = dateArithmetic(start, 1);
		}
		return meetingTimeSlots;
	}

	public List<MeetingInvitation> generateMeetingInvitation(int meetingID, List<MeetingParticipant> mps, String initiator) {
		List<MeetingInvitation> meetingInvitations = new ArrayList<MeetingInvitation>();
		for(int i=0;i<mps.size();i++) {
			// 1 participant, 1 invitation
			meetingInvitations.add(new MeetingInvitation(meetingID, mps.get(i), initiator + " was added you as participant on meeting " + meetingID));
		}
		
		return meetingInvitations;
	}
	
	
	
	public void saveMeetingInvitation(List<MeetingInvitation> mis) {
		List<MeetingInvitation> meetingInvitation = new ArrayList<MeetingInvitation>();
		meetingInvitation = jParserInvitation.load(fileMeetingInvitation);
		for(int i=0;i<mis.size();i++) {
			meetingInvitation.add(mis.get(i));
		}
		jParserInvitation.write(meetingInvitation, fileMeetingInvitation);
		
		System.out.println("Meeting invitation has been sent to participants");
	}
	
	public List<MeetingInvitation> getMeetingInvitationByEmail(String email) {
		List<MeetingInvitation> meetingInvitations = new ArrayList<MeetingInvitation>();
		List<MeetingInvitation> meetingInvitationsFiltered = new ArrayList<MeetingInvitation>();
		
		//load meeting invitation
		meetingInvitations = jParserInvitation.load(fileMeetingInvitation);
		for(int i=0;i<meetingInvitations.size();i++) {
			if(meetingInvitations.get(i).getMp().getEmail().equals(email)) {
				meetingInvitationsFiltered.add(meetingInvitations.get(i));
			}
		}
				
		return meetingInvitationsFiltered;
	}
	
}
