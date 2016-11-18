package com.controller.meeting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingParticipant;
import com.view.meeting.MeetingView;

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
			String maxReponseDate, String maxResponseTime, String meetingInitiator) {
		
		meetingDraft = new Meeting(getNextMeetingID(), title, agenda, location,
				duration, proposedStartDate, proposedEndDate,
				maxReponseDate, maxResponseTime, meetingInitiator);
		
	}
	
	public int getNextMeetingID() {
		int length = 4;
		int id = 0;
		int idInt;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			id =  mapper.readValue(new File("resources/id"), Integer.class);
			id++;
			mapper.writeValue(new File("resources/id"), id);
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
	
	public void saveMeetingCreation() {
		ObjectMapper mapper = new ObjectMapper();
		//List<Meeting> meetingList = new ArrayList<Meeting>();

		try {
			System.out.println("Meeting id: " + meetingDraft.getId());
			mapper.writeValue(new File(meetingPath + "M" +  meetingDraft.getId() + ".json"), meetingDraft);
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
	
}
