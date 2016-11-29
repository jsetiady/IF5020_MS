package com.controller.meeting;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.model.meeting.Meeting;
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
	
	public void editMeeting(Meeting m) {
		m.setMeetingStatus(m.CANCELED);
		List<Meeting> arrMeeting = getAllMeeting();
		for(int i=0;i<arrMeeting.size();i++) {
			if(arrMeeting.get(i).getId()==m.getId()) {
				arrMeeting.set(i, m);
				jParserMeeting.write(arrMeeting, fileMeetingData);
			}
		}
	}
	
	public void addMeetingParticipant(String email, boolean isImportant) {
		MeetingParticipant mp = new MeetingParticipant(meetingDraft.getId(), email, isImportant);
		List<MeetingParticipant> arrMP = meetingDraft.getMeetingParticipant();
		if(!findMeetingParticipantByEmail(arrMP, mp.getEmail())) {
			arrMP.add(mp);
		}
		meetingDraft.setMeetingParticipant(arrMP);
	}
	
	public int getTotalParticipant(List<MeetingParticipant> mp, boolean isImportant) {
		int num = 0;
		for(int i=0;i<mp.size();i++) {
			if(mp.get(i).isImportant()==isImportant) {
				num++;
			}
		}
		return 0;
	}
	
	public void runScheduler(String meetingID) {
		Meeting m = getMeetingByID(meetingID);
		List<MeetingTimeSlot> mts = null;
		int[] timeslotScore;
		mts = m.getMeetingTimeSlots();
		timeslotScore = new int[mts.size()];
		for(int j=0;j<mts.size();j++) {
			timeslotScore[j] = 0;
			if(mts.get(j).getNumImportantParticipant() < getTotalParticipant(m.getMeetingParticipant(), true)) {
				timeslotScore[j] = 0;
			} else {
				timeslotScore[j] = mts.get(j).getNumImportantParticipant() + mts.get(j).getNumOrdinaryParticipant();
			}
		}
		
		//sorting & get rank #1
		int maxIndex = -9;
		int max = -9;
		for(int i=0;i<mts.size();i++) {
			if(max<timeslotScore[i]) {
				maxIndex = i;
				max = timeslotScore[i];
			}
		}
		
		System.out.println("Timeslot score: " + max);
		if(max != 0) {
			System.out.println("Scheduled date: " + mts.get(maxIndex).getDate() + " " + mts.get(maxIndex).getStartTime() + "-" + mts.get(maxIndex).getEndTime());
			
			//TODO RUN SCHEDULER JEEEEH!
			m.setScheduledDate(mts.get(maxIndex).getDate() + " " + mts.get(maxIndex).getStartTime() + "-" + mts.get(maxIndex).getEndTime());
			m.setMeetingStatus(m.SCHEDULED);
			
			List<Meeting> arrMeeting = getAllMeeting();
			for(int i=0;i<arrMeeting.size();i++) {
				if(arrMeeting.get(i).getId()==m.getId()) {
					arrMeeting.set(i, m);
					jParserMeeting.write(arrMeeting, fileMeetingData);
				}
			}
			
		}
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
		
		//add to meeting instance
		meetingDraft.setMeetingTimeSlots(meetingTimeSlots);
		
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
	
	
	public List<Meeting> getAllMeeting() {
		List<Meeting> mlist = new ArrayList<Meeting>();
		mlist = jParserMeeting.load(fileMeetingData);
		return mlist;
	}
	
	public void updateMeetingData(List<Meeting> arrMeeting) {
		jParserMeeting.write(arrMeeting, fileMeetingData);
	}
	
	public boolean isParticipant(Meeting m, String email) {
		if(getParticipantInfo(m,email)==null) {
			return false;
		} else {
			return true;
		}
	}
	
	public MeetingParticipant getParticipantInfo(Meeting m, String email) {
		List<MeetingParticipant> mp = m.getMeetingParticipant();
		for(int i=0;i<mp.size();i++) {
			if(mp.get(i).getEmail().equals(email)) {
				return mp.get(i);
			}
		}
		return null;
	}
	
	public void cancelMeeting(Meeting m) {
		m.setMeetingStatus(m.CANCELED);
		List<Meeting> arrMeeting = getAllMeeting();
		for(int i=0;i<arrMeeting.size();i++) {
			if(arrMeeting.get(i).getId()==m.getId()) {
				if(arrMeeting.get(i).getMeetingStatus()==arrMeeting.get(i).FINISH ||
					arrMeeting.get(i).getMeetingStatus()==arrMeeting.get(i).RUNNING) {
						System.out.println("The meeting is no longer able to be canceled (status: Running / Finish");
					} else {
						arrMeeting.set(i, m);
						jParserMeeting.write(arrMeeting, fileMeetingData);
						
						System.out.println("The meeting successfully canceled");
					}
			}
		}
	}
	
	public boolean checkAvailabilityTimeSlotOfParticipant(MeetingTimeSlot mts, String email) {
		//TODO
		List<String> mpImp = new ArrayList<String>();
		List<String> mpOrd = new ArrayList<String>();
		mpImp = mts.getImportantParticipants();
		mpOrd = mts.getOrdinaryParticipants();
		
		if(mpImp!=null) {
			for(int i=0;i<mpImp.size();i++) {
				if(mpImp.get(i).equals(email)) {
					return true;
				}
			}
		}
		
		if(mpOrd!=null) {
			for(int i=0;i<mpOrd.size();i++) {
				if(mpOrd.get(i).equals(email)) {
					return true;
				}
			}
		}
		
		return false;		
	}
	
	
	public boolean rejectInvitation(String meetingID, String email) {
		// things that should be updated
		// meeting participant --> response = reject
		//timeslot di reset
		
		List<Meeting> mlist = getAllMeeting();
		List<MeetingParticipant> mp = new ArrayList<MeetingParticipant>();
		Meeting m;
		for(int i=0;i<mlist.size();i++) {
			m = mlist.get(i);
			if(m.getId() == Integer.parseInt(meetingID.substring(1))) {
				mp = m.getMeetingParticipant();
				for(int j=0;j<mp.size();j++) {
					if(mp.get(j).getEmail().equals(email)) {
						mp.get(j).setResponseDate(validator.getCurrentTime());
						mp.get(j).setResponse(mp.get(j).REJECT);
					}
				}
				mlist.set(i, m);
			}
		}
		
		//write to JSON
		jParserMeeting.write(mlist, fileMeetingData);
		
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

	
}
