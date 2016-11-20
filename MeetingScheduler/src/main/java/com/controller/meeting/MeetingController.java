package com.controller.meeting;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingParticipant;

import examples.Staff;

/**
 * @author jessiesetiady
 *
 */
public class MeetingController {
	private Meeting m, meetingDraft;
	private String meetingPath = "resources/meeting/";
	
	public void createMeetingDraft(String title, String agenda, String location,
			int duration, String proposedStartDate, String proposedEndDate,
			String maxReponseDate, String maxResponseTime, String meetingInitiator, String createdDate) {
		
		meetingDraft = new Meeting(getNextMeetingID(), title, agenda, location,
				duration, proposedStartDate, proposedEndDate,
				maxReponseDate, maxResponseTime, meetingInitiator, createdDate);
		
	}
	
	public int getNextMeetingID() {
		int id = 0;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			id =  mapper.readValue(new File("resources/lastid"), Integer.class);
			id++;
			mapper.writeValue(new File("resources/lastid"), id);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void addMeetingParticipant(String email, boolean isImportant) {
		MeetingParticipant mp = new MeetingParticipant(email, isImportant);
		ArrayList<MeetingParticipant> arrMP = meetingDraft.getMeetingParticipant();
		arrMP.add(mp);
		meetingDraft.setMeetingParticipant(arrMP);
	}
	
	public ArrayList<MeetingParticipant> getParticipantList() {
		return meetingDraft.getMeetingParticipant();
	}
	
	public Meeting getMeetingDraft() {
		return meetingDraft;
	}
	
	public boolean findMeetingParticipantByEmail(List<MeetingParticipant> arrMP, String email) {
		for(int i=0;i<arrMP.size();i++) {
			if(arrMP.get(i).getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	public void saveMeetingCreation() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			//save meetingDraft to json
			System.out.println("Meeting id: " + meetingDraft.getId());
			mapper.writeValue(new File(meetingPath + "M" +  meetingDraft.getId() + ".json"), meetingDraft);
			
			//add new meeting id to json meeting_id
			List<Integer> meetingIds = mapper.readValue(new File("resources/meeting_id.json"), new TypeReference<List<Integer>>(){});
			meetingIds.add(meetingDraft.getId());
			mapper.writeValue(new File("resources/meeting_id.json"),  meetingIds);
			
			//generate time slot
			
			
			//generate invitation
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public List<Meeting> getListOfCreatedMeeting(String initiator) {
		ObjectMapper mapper = new ObjectMapper();
		Meeting m = new Meeting();
		List<Meeting> createdMeetingList = new ArrayList<Meeting>();
		
		try {
			//load meeting_id
			List<Integer> listMeetingID = mapper.readValue(new File("resources/meeting_id.json"), new TypeReference<List<Integer>>(){});
			
			//for each meeting id
			for(int i=0;i<listMeetingID.size();i++) {
				//load file json
				m = mapper.readValue(new File("resources/meeting/M"+listMeetingID.get(i)+".json"), Meeting.class);
				if(m.getMeetingInitiator().equals(initiator)) {
					//add Meeting to createdMeetingList
					createdMeetingList.add(m);
				}
			}
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public List<Meeting> getListOfScheduledMeetingByEmail(String participant) {
		ObjectMapper mapper = new ObjectMapper();
		Meeting m = new Meeting();
		List<Meeting> scheduledMeetingList = new ArrayList<Meeting>();
		List<MeetingParticipant> participantList = new ArrayList<MeetingParticipant>();
		
		try {
			//load meeting_id
			List<Integer> listMeetingID = mapper.readValue(new File("resources/meeting_id.json"), new TypeReference<List<Integer>>(){});
			
			//for each meeting id
			for(int i=0;i<listMeetingID.size();i++) {
				//load file json
				m = mapper.readValue(new File("resources/meeting/M"+listMeetingID.get(i)+".json"), Meeting.class);
				
				if(m.getMeetingStatus()==m.SCHEDULED)
				participantList = m.getMeetingParticipant();
				
				for(int j=0;i<participantList.size();j++) {
					if(participantList.get(j).getEmail().equals(participant)) {
						System.out.println(participantList.get(j).getEmail());
						//add Meeting to createdMeetingList
						scheduledMeetingList.add(m);
					}
				}
				
			}
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return scheduledMeetingList;
	}
	
}
