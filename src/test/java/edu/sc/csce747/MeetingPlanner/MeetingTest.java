package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.ArrayList;

public class MeetingTest {
	private Meeting meeting;
	private Person person1, person2;
	private Room room;
	
	@Before
	public void setUp() {
		meeting = new Meeting();
		person1 = new Person("John Doe");
		person2 = new Person("Jane Smith");
		room = new Room("2A01");
	}
	
	@After
	public void tearDown() {
		meeting = null;
		person1 = null;
		person2 = null;
		room = null;
	}
	
	// POSITIVE TEST CASES
	
	@Test
	public void testDefaultConstructor() {
		Meeting newMeeting = new Meeting();
		assertNotNull("Meeting should be created", newMeeting);
		assertEquals("Default month should be 0", 0, newMeeting.getMonth());
		assertEquals("Default day should be 0", 0, newMeeting.getDay());
		assertEquals("Default start time should be 0", 0, newMeeting.getStartTime());
		assertEquals("Default end time should be 0", 0, newMeeting.getEndTime());
	}
	
	@Test
	public void testConstructor_monthDay() {
		Meeting newMeeting = new Meeting(3, 15);
		assertEquals("Month should be set", 3, newMeeting.getMonth());
		assertEquals("Day should be set", 15, newMeeting.getDay());
		assertEquals("Start time should be 0", 0, newMeeting.getStartTime());
		assertEquals("End time should be 23", 23, newMeeting.getEndTime());
	}
	
	@Test
	public void testConstructor_monthDayDescription() {
		Meeting newMeeting = new Meeting(6, 20, "Team Meeting");
		assertEquals("Month should be set", 6, newMeeting.getMonth());
		assertEquals("Day should be set", 20, newMeeting.getDay());
		assertEquals("Description should be set", "Team Meeting", newMeeting.getDescription());
		assertEquals("Start time should be 0", 0, newMeeting.getStartTime());
		assertEquals("End time should be 23", 23, newMeeting.getEndTime());
	}
	
	@Test
	public void testConstructor_monthDayStartEnd() {
		Meeting newMeeting = new Meeting(8, 25, 9, 11);
		assertEquals("Month should be set", 8, newMeeting.getMonth());
		assertEquals("Day should be set", 25, newMeeting.getDay());
		assertEquals("Start time should be set", 9, newMeeting.getStartTime());
		assertEquals("End time should be set", 11, newMeeting.getEndTime());
	}
	
	@Test
	public void testConstructor_full() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(person1);
		attendees.add(person2);
		
		Meeting newMeeting = new Meeting(10, 15, 14, 16, attendees, room, "Project Review");
		assertEquals("Month should be set", 10, newMeeting.getMonth());
		assertEquals("Day should be set", 15, newMeeting.getDay());
		assertEquals("Start time should be set", 14, newMeeting.getStartTime());
		assertEquals("End time should be set", 16, newMeeting.getEndTime());
		assertEquals("Description should be set", "Project Review", newMeeting.getDescription());
		assertEquals("Room should be set", room, newMeeting.getRoom());
		assertEquals("Attendees should be set", attendees, newMeeting.getAttendees());
	}
	
	@Test
	public void testSettersAndGetters() {
		meeting.setMonth(5);
		meeting.setDay(12);
		meeting.setStartTime(10);
		meeting.setEndTime(12);
		meeting.setDescription("Test Meeting");
		meeting.setRoom(room);
		
		assertEquals("Month should be set", 5, meeting.getMonth());
		assertEquals("Day should be set", 12, meeting.getDay());
		assertEquals("Start time should be set", 10, meeting.getStartTime());
		assertEquals("End time should be set", 12, meeting.getEndTime());
		assertEquals("Description should be set", "Test Meeting", meeting.getDescription());
		assertEquals("Room should be set", room, meeting.getRoom());
	}
	
	@Test
	public void testAddAttendee() {
		meeting.setAttendees(new ArrayList<Person>());
		meeting.addAttendee(person1);
		meeting.addAttendee(person2);
		
		ArrayList<Person> attendees = meeting.getAttendees();
		assertNotNull("Attendees list should not be null", attendees);
		assertEquals("Should have 2 attendees", 2, attendees.size());
		assertTrue("Should contain person1", attendees.contains(person1));
		assertTrue("Should contain person2", attendees.contains(person2));
	}
	
	@Test
	public void testRemoveAttendee() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(person1);
		attendees.add(person2);
		meeting.setAttendees(attendees);
		
		meeting.removeAttendee(person1);
		
		ArrayList<Person> remainingAttendees = meeting.getAttendees();
		assertEquals("Should have 1 attendee after removal", 1, remainingAttendees.size());
		assertFalse("Should not contain person1", remainingAttendees.contains(person1));
		assertTrue("Should still contain person2", remainingAttendees.contains(person2));
	}
	
	@Test
	public void testToString_withAllFields() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(person1);
		attendees.add(person2);
		
		meeting.setMonth(3);
		meeting.setDay(15);
		meeting.setStartTime(9);
		meeting.setEndTime(11);
		meeting.setDescription("Team Standup");
		meeting.setRoom(room);
		meeting.setAttendees(attendees);
		
		String result = meeting.toString();
		assertNotNull("toString should not return null", result);
		assertTrue("Should contain date info", result.contains("3/15"));
		assertTrue("Should contain time info", result.contains("9 - 11"));
		assertTrue("Should contain room info", result.contains("2A01"));
		assertTrue("Should contain description", result.contains("Team Standup"));
		assertTrue("Should contain attendee names", result.contains("John Doe"));
		assertTrue("Should contain attendee names", result.contains("Jane Smith"));
	}
	
	// NEGATIVE TEST CASES
	
	@Test
	public void testAddAttendee_nullAttendeesList() {
		// Should not throw; implementation initializes attendees lazily
		meeting.addAttendee(person1);
		assertNotNull("Attendees list should be initialized", meeting.getAttendees());
		assertEquals("Should have 1 attendee", 1, meeting.getAttendees().size());
	}
	
	@Test
	public void testRemoveAttendee_nullAttendeesList() {
		// Should be a no-op and not throw
		meeting.removeAttendee(person1);
		assertNull("Attendees list remains null after no-op remove", meeting.getAttendees());
	}
	
	@Test
	public void testToString_nullRoom() {
		// Should not throw; prints placeholder for room
		meeting.setMonth(3);
		meeting.setDay(15);
		meeting.setStartTime(9);
		meeting.setEndTime(11);
		meeting.setDescription("Test Meeting");
		// room is null by default
		String result = meeting.toString();
		assertNotNull(result);
		assertTrue("Should include placeholder when room is null", result.contains("NoRoom"));
	}
	
	@Test
	public void testToString_nullAttendees() {
		// Should not throw; prints 'none' for attendees
		meeting.setMonth(3);
		meeting.setDay(15);
		meeting.setStartTime(9);
		meeting.setEndTime(11);
		meeting.setDescription("Test Meeting");
		meeting.setRoom(room);
		// attendees is null by default
		String result = meeting.toString();
		assertNotNull(result);
		assertTrue("Should indicate none when attendees is null", result.contains("Attending: none"));
	}
	
	@Test
	public void testToString_emptyAttendees() {
		meeting.setMonth(3);
		meeting.setDay(15);
		meeting.setStartTime(9);
		meeting.setEndTime(11);
		meeting.setDescription("Test Meeting");
		meeting.setRoom(room);
		meeting.setAttendees(new ArrayList<Person>());
		
		String result = meeting.toString();
		assertNotNull("toString should not return null", result);
		assertTrue("Should contain basic meeting info", result.contains("3/15"));
		assertTrue("Should contain time info", result.contains("9 - 11"));
		assertTrue("Should contain room info", result.contains("2A01"));
		assertTrue("Should contain description", result.contains("Test Meeting"));
	}
	
	@Test
	public void testRemoveAttendee_notInList() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(person1);
		meeting.setAttendees(attendees);
		
		// Try to remove person2 who is not in the list
		meeting.removeAttendee(person2);
		
		ArrayList<Person> remainingAttendees = meeting.getAttendees();
		assertEquals("Should still have 1 attendee", 1, remainingAttendees.size());
		assertTrue("Should still contain person1", remainingAttendees.contains(person1));
	}
	
	@Test
	public void testAddAttendee_duplicate() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(person1);
		meeting.setAttendees(attendees);
		
		// Add the same person again
		meeting.addAttendee(person1);
		
		ArrayList<Person> allAttendees = meeting.getAttendees();
		assertEquals("Should have 2 attendees (including duplicate)", 2, allAttendees.size());
		// Note: ArrayList allows duplicates, so this is expected behavior
	}
	
	// Edge cases for time validation
	
	@Test
	public void testSetStartTime_boundaryValues() {
		meeting.setStartTime(0);
		assertEquals("Start time 0 should be valid", 0, meeting.getStartTime());
		
		meeting.setStartTime(23);
		assertEquals("Start time 23 should be valid", 23, meeting.getStartTime());
	}
	
	@Test
	public void testSetEndTime_boundaryValues() {
		meeting.setEndTime(0);
		assertEquals("End time 0 should be valid", 0, meeting.getEndTime());
		
		meeting.setEndTime(23);
		assertEquals("End time 23 should be valid", 23, meeting.getEndTime());
	}
	
	@Test
	public void testSetStartTime_negative() {
		meeting.setStartTime(-1);
		assertEquals("Start time -1 should be set (no validation in setter)", -1, meeting.getStartTime());
	}
	
	@Test
	public void testSetEndTime_negative() {
		meeting.setEndTime(-1);
		assertEquals("End time -1 should be set (no validation in setter)", -1, meeting.getEndTime());
	}
	
	@Test
	public void testSetStartTime_tooHigh() {
		meeting.setStartTime(24);
		assertEquals("Start time 24 should be set (no validation in setter)", 24, meeting.getStartTime());
	}
	
	@Test
	public void testSetEndTime_tooHigh() {
		meeting.setEndTime(24);
		assertEquals("End time 24 should be set (no validation in setter)", 24, meeting.getEndTime());
	}
	
	// Edge cases for date validation
	
	@Test
	public void testSetMonth_boundaryValues() {
		meeting.setMonth(1);
		assertEquals("Month 1 should be valid", 1, meeting.getMonth());
		
		meeting.setMonth(12);
		assertEquals("Month 12 should be valid", 12, meeting.getMonth());
	}
	
	@Test
	public void testSetDay_boundaryValues() {
		meeting.setDay(1);
		assertEquals("Day 1 should be valid", 1, meeting.getDay());
		
		meeting.setDay(31);
		assertEquals("Day 31 should be valid", 31, meeting.getDay());
	}
	
	@Test
	public void testSetMonth_invalidValues() {
		meeting.setMonth(0);
		assertEquals("Month 0 should be set (no validation in setter)", 0, meeting.getMonth());
		
		meeting.setMonth(13);
		assertEquals("Month 13 should be set (no validation in setter)", 13, meeting.getMonth());
		
		meeting.setMonth(-1);
		assertEquals("Month -1 should be set (no validation in setter)", -1, meeting.getMonth());
	}
	
	@Test
	public void testSetDay_invalidValues() {
		meeting.setDay(0);
		assertEquals("Day 0 should be set (no validation in setter)", 0, meeting.getDay());
		
		meeting.setDay(32);
		assertEquals("Day 32 should be set (no validation in setter)", 32, meeting.getDay());
		
		meeting.setDay(-1);
		assertEquals("Day -1 should be set (no validation in setter)", -1, meeting.getDay());
	}
}
