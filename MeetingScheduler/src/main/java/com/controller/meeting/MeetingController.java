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

/**
 * @author jessiesetiady
 *
 */
public class MeetingController {
	private Meeting m, meetingDraft;
	private String meetingPath = "resources/meeting/";
	
	public void createMeetingDraft(String title, String agenda, String location,
			int duration, String proposedStartDate, String proposedEndDate,
			String maxReponseDate, String maxResponseTime, String meetingInitiator) {
		
		meetingDraft = new Meeting(getNextMeetingID(), title, agenda, location,
				duration, proposedStartDate, proposedEndDate,
				maxReponseDate, maxResponseTime, meetingInitiator);
		
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
			ArrayList<Integer> meetingIds = mapper.readValue(new File("resources/meeting_id.json"), new TypeReference<List<Integer>>(){});
			meetingIds.add(meetingDraft.getId());
			mapper.writeValue(new File("meeting_id.json"),  new TypeReference<List<Integer>>(){});
			
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
	
}
