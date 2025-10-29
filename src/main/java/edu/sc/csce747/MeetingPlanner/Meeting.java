package edu.sc.csce747.MeetingPlanner;

import java.util.ArrayList;

public class Meeting {
	private int month;
	private int day;
	private int start;
	private int end;
	private ArrayList<Person> attendees; 
	private Room room;
	private String description;
	
	/**
	 * Default constructor
	 */
	public Meeting(){
	}
	
	/**
	 * Constructor that can be used to block off a whole day -
	 * such as for a vacation
	 * @param month - The month of the meeting (1-12).
	 * @param day - The day of the meeting (1-31).
	 */
	public Meeting(int month, int day){
		this.month=month;
		this.day=day;
		this.start=0;
		this.end=23;
	}
	
	/**
	 * Constructor that can be used to block off a whole day -
	 * such as for a vacation
	 * @param month - The month of the meeting (1-12).
	 * @param day - The day of the meeting (1-31).
	 * @param description - A description of the meeting.
	 */
	public Meeting(int month, int day, String description){
		this.month=month;
		this.day=day;
		this.start=0;
		this.end=23;
		this.description= description;
	}
	
	/**
	 * More detailed constructor
	 * @param month - The month of the meeting (1-12).
	 * @param day - The day of the meeting (1-31).
	 * @param start - The time the meeting starts (0-23).
	 * @param end - The time the meeting ends (0-23).
	 */
	public Meeting(int month, int day, int start, int end){
		this.month=month;
		this.day=day;
		this.start=start;
		this.end=end;
	}
	
	/**
	 * More detailed constructor
	 * @param month - The month of the meeting (1-12).
	 * @param day - The day of the meeting (1-31).
	 * @param start - The time the meeting starts (0-23).
	 * @param end - The time the meeting ends (0-23).
	 * @param attendees - The people attending the meeting.
	 * @param room - The room that the meeting is taking place in.
	 * @param description - A description of the meeting.
	 */
	public Meeting(int month, int day, int start, int end, ArrayList<Person> attendees, Room room, String description){
		this.month=month;
		this.day=day;
		this.start=start;
		this.end=end;
		this.attendees = attendees;
		this.room = room;
		this.description = description;
	}

	/**
	 * Add an attendee to the meeting.
	 * @param attendee - The person to add.
	 */
	public void addAttendee(Person attendee) {
		if (this.attendees == null) {
			this.attendees = new ArrayList<Person>();
		}
		this.attendees.add(attendee);
	}
	
	/**
	 * Removes an attendee from the meeting.
	 * @param attendee - The person to remove.
	 */
	public void removeAttendee(Person attendee) {
		if (this.attendees == null) {
			return;
		}
		this.attendees.remove(attendee);
	}
	
	/**
	 * Returns information about the meeting as a formatted string.
	 * @return String - Information about the meeting.
	 */
	public String toString(){
		String safeDescription = (description == null) ? "" : description;
		String roomId = (room == null) ? "NoRoom" : room.getID();
		StringBuilder infoBuilder = new StringBuilder();
		infoBuilder.append(month).append("/").append(day)
			.append(", ").append(start).append(" - ").append(end)
			.append(",").append(roomId).append(": ").append(safeDescription)
			.append("\nAttending: ");
		
		if (attendees == null || attendees.isEmpty()) {
			infoBuilder.append("none");
		} else {
			for (int i = 0; i < attendees.size(); i++) {
				Person attendee = attendees.get(i);
				String name = (attendee == null) ? "" : attendee.getName();
				infoBuilder.append(name);
				if (i < attendees.size() - 1) {
					infoBuilder.append(",");
				}
			}
		}
		
		return infoBuilder.toString();
	}
	
	/**
	 * Getters and Setters
	 */
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getStartTime() {
		return start;
	}

	public void setStartTime(int start) {
		this.start = start;
	}

	public int getEndTime() {
		return end;
	}

	public void setEndTime(int end) {
		this.end = end;
	}

	public ArrayList<Person> getAttendees() {
		return attendees;
	}

	public void setAttendees(ArrayList<Person> attendees) {
		this.attendees = attendees;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
