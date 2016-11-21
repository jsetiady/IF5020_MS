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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.meeting.Meeting;
import com.model.meeting.MeetingParticipant;
import com.model.meeting.MeetingTimeSlot;

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
			// Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void addMeetingParticipant(String email, boolean isImportant) {
		MeetingParticipant mp = new MeetingParticipant(email, isImportant);
		List<MeetingParticipant> arrMP = meetingDraft.getMeetingParticipant();
		arrMP.add(mp);
		meetingDraft.setMeetingParticipant(arrMP);
	}
	
	public List<MeetingParticipant> getParticipantList() {
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
		
		System.out.println(numOfTimeSlot + " time slot for this meeting is generated");
		
		start = meetingStartDate;
		for(int i=0; i<days; i++) {
			int j = 0;
			while(j<numOfTimeSlotPerDay) {
				meetingTimeSlots.add(new MeetingTimeSlot(start, startTime[j], endTime[j+duration-1]));
				//System.out.println(start + " " + startTime[j] + " " + endTime[j+duration-1]);
				j++;
			}
			start = dateArithmetic(start, 1);
		}
		return meetingTimeSlots;
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

	
	public void generateMeetingInvitation() {
		//TODO @jeje
	}
	
	public void saveMeetingCreation() {
		List<MeetingTimeSlot> meetingTimeSlots = new ArrayList<MeetingTimeSlot>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			//generate time slot
			meetingTimeSlots = generateMeetingTimeSlot(meetingDraft.getDuration(), meetingDraft.getProposedStartDate(), meetingDraft.getProposedEndDate());
			
			//add to meeting instance
			meetingDraft.setMeetingTimeSlots(meetingTimeSlots);
			
			//save meetingDraft to json
			System.out.println("Meeting id: M" + meetingDraft.getId());
			mapper.writeValue(new File(meetingPath + "M" +  meetingDraft.getId() + ".json"), meetingDraft);
			
			//add new meeting id to json meeting_id
			List<Integer> meetingIds = mapper.readValue(new File("resources/meeting_id.json"), new TypeReference<List<Integer>>(){});
			meetingIds.add(meetingDraft.getId());
			mapper.writeValue(new File("resources/meeting_id.json"),  meetingIds);
			
			//generate invitation
			
			
		} catch (JsonParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
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
			// Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
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
	
	public Meeting getMeetingByID(String meetingID) {
		Meeting m = null;
		ObjectMapper mapper = new ObjectMapper();
		//load file json
		try {
			m = mapper.readValue(new File("resources/meeting/M"+Integer.parseInt(meetingID.substring(1))+".json"), Meeting.class);
		} catch (JsonParseException e) {
			System.out.println("You don't have privilege to view this meeting");
			//e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("You don't have privilege to view this meeting");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("You don't have privilege to view this meeting");
			//e.printStackTrace();
		}
		return m;
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
				if(m.getMeetingStatus()==m.SCHEDULED) {
					participantList = m.getMeetingParticipant();
					
					for(int j=0;j<participantList.size();j++) {
						if(participantList.get(j).getEmail().equals(participant)) {
							//add Meeting to createdMeetingList
							scheduledMeetingList.add(m);
						}
					}
					
				}
				
			}
			
		} catch (JsonParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		return scheduledMeetingList;
	}
	
}
