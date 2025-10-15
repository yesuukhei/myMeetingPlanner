package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.ArrayList;

public class RoomTest {
	private Room room;
	private Meeting meeting1, meeting2;
	
	@Before
	public void setUp() {
		room = new Room("2A01");
		meeting1 = new Meeting(3, 15, 9, 11);
		meeting2 = new Meeting(3, 15, 14, 16);
	}
	
	@After
	public void tearDown() {
		room = null;
		meeting1 = null;
		meeting2 = null;
	}
	
	// POSITIVE TEST CASES
	
	@Test
	public void testDefaultConstructor() {
		Room newRoom = new Room();
		assertNotNull("Room should be created", newRoom);
		assertEquals("Default ID should be empty", "", newRoom.getID());
	}
	
	@Test
	public void testConstructor_withID() {
		Room newRoom = new Room("2A02");
		assertEquals("ID should be set", "2A02", newRoom.getID());
	}
	
	@Test
	public void testAddMeeting_valid() {
		try {
			room.addMeeting(meeting1);
			assertTrue("Room should be busy during meeting time", 
				room.isBusy(3, 15, 9, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_multipleMeetings() {
		try {
			room.addMeeting(meeting1);
			room.addMeeting(meeting2);
			assertTrue("Room should be busy during first meeting", 
				room.isBusy(3, 15, 9, 11));
			assertTrue("Room should be busy during second meeting", 
				room.isBusy(3, 15, 14, 16));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_noConflict() {
		try {
			room.addMeeting(meeting1);
			assertFalse("Room should not be busy at different time", 
				room.isBusy(3, 15, 14, 16));
			assertFalse("Room should not be busy on different day", 
				room.isBusy(3, 16, 9, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_month() {
		try {
			room.addMeeting(meeting1);
			room.addMeeting(meeting2);
			
			String agenda = room.printAgenda(3);
			assertNotNull("Agenda should not be null", agenda);
			assertTrue("Agenda should contain month info", agenda.contains("Agenda for 3:"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_day() {
		try {
			room.addMeeting(meeting1);
			
			String agenda = room.printAgenda(3, 15);
			assertNotNull("Agenda should not be null", agenda);
			assertTrue("Agenda should contain day info", agenda.contains("Agenda for 3/15:"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetMeeting() {
		try {
			room.addMeeting(meeting1);
			
			Meeting retrieved = room.getMeeting(3, 15, 0);
			assertNotNull("Retrieved meeting should not be null", retrieved);
			assertEquals("Meeting month should match", 3, retrieved.getMonth());
			assertEquals("Meeting day should match", 15, retrieved.getDay());
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testRemoveMeeting() {
		try {
			room.addMeeting(meeting1);
			assertTrue("Room should be busy before removal", 
				room.isBusy(3, 15, 9, 11));
			
			room.removeMeeting(3, 15, 0);
			assertFalse("Room should not be busy after removal", 
				room.isBusy(3, 15, 9, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// NEGATIVE TEST CASES
	
	@Test
	public void testAddMeeting_timeConflict() {
		try {
			Meeting conflictingMeeting = new Meeting(3, 15, 10, 12);
			room.addMeeting(meeting1);
			room.addMeeting(conflictingMeeting);
			fail("Should throw TimeConflictException for overlapping meetings");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for room"));
			assertTrue("Should contain room ID", e.getMessage().contains("2A01"));
		}
	}
	
	@Test
	public void testAddMeeting_exactOverlap() {
		try {
			Meeting exactOverlap = new Meeting(3, 15, 9, 11);
			room.addMeeting(meeting1);
			room.addMeeting(exactOverlap);
			fail("Should throw TimeConflictException for exact overlap");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for room"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidDate() {
		try {
			Meeting invalidMeeting = new Meeting(13, 15, 9, 11);
			room.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for invalid month");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for room"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidTime() {
		try {
			Meeting invalidMeeting = new Meeting(3, 15, 25, 11);
			room.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for invalid time");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for room"));
		}
	}
	
	@Test
	public void testIsBusy_invalidParameters() {
		try {
			room.isBusy(13, 15, 9, 11);
			fail("Should throw TimeConflictException for invalid month");
		} catch(TimeConflictException e) {
			assertEquals("Should throw correct error message", "Month does not exist.", e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidDay() {
		try {
			room.isBusy(3, 35, 9, 11);
			fail("Should throw TimeConflictException for invalid day");
		} catch(TimeConflictException e) {
			assertEquals("Should throw correct error message", "Day does not exist.", e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidTime() {
		try {
			room.isBusy(3, 15, 25, 11);
			fail("Should throw TimeConflictException for invalid time");
		} catch(TimeConflictException e) {
			assertEquals("Should throw correct error message", "Illegal hour.", e.getMessage());
		}
	}
	
	// Edge cases
	
	@Test
	public void testAddMeeting_boundaryTimes() {
		try {
			Meeting earlyMeeting = new Meeting(4, 10, 0, 1);
			Meeting lateMeeting = new Meeting(4, 10, 22, 23);
			room.addMeeting(earlyMeeting);
			room.addMeeting(lateMeeting);
			
			assertTrue("Should be busy at early time", room.isBusy(4, 10, 0, 1));
			assertTrue("Should be busy at late time", room.isBusy(4, 10, 22, 23));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_consecutiveMeetings() {
		try {
			Meeting meeting1 = new Meeting(5, 20, 9, 11);
			Meeting meeting2 = new Meeting(5, 20, 11, 13);
			room.addMeeting(meeting1);
			room.addMeeting(meeting2);
			
			assertTrue("Should be busy during first meeting", room.isBusy(5, 20, 9, 11));
			assertTrue("Should be busy during second meeting", room.isBusy(5, 20, 11, 13));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_sameTimeDifferentDays() {
		try {
			Meeting meeting1 = new Meeting(6, 10, 10, 12);
			Meeting meeting2 = new Meeting(6, 11, 10, 12);
			room.addMeeting(meeting1);
			room.addMeeting(meeting2);
			
			assertTrue("Should be busy on first day", room.isBusy(6, 10, 10, 12));
			assertTrue("Should be busy on second day", room.isBusy(6, 11, 10, 12));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	
	
	@Test
	public void testAddMeeting_nullMeeting() {
		try {
			room.addMeeting(null);
			fail("Should throw NullPointerException for null meeting");
		} catch(NullPointerException e) {
			assertTrue("Should throw NullPointerException", true);
		} catch(TimeConflictException e) {
			// This might also be thrown depending on implementation
			assertTrue("Should throw TimeConflictException or NullPointerException", true);
		}
	}
	
	@Test
	public void testRoomID_setter() {
		// Test if we can change room ID (if setter exists)
		room = new Room("2A01");
		assertEquals("Initial ID should be set", "2A01", room.getID());
		
		// Note: There's no setter for ID in the current implementation
		// This test documents the current behavior
	}
}