package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.ArrayList;

public class PersonTest {
	private Person person;
	private Meeting meeting1, meeting2;
	private Room room;
	
	@Before
	public void setUp() {
		person = new Person("Test User");
		room = new Room("2A01");
		meeting1 = new Meeting(3, 15, 9, 11);
		meeting2 = new Meeting(3, 15, 14, 16);
	}
	
	@After
	public void tearDown() {
		person = null;
		meeting1 = null;
		meeting2 = null;
		room = null;
	}
	
	// POSITIVE TEST CASES
	
	@Test
	public void testDefaultConstructor() {
		Person newPerson = new Person();
		assertNotNull("Person should be created", newPerson);
		assertEquals("Default name should be empty", "", newPerson.getName());
	}
	
	@Test
	public void testConstructor_withName() {
		Person newPerson = new Person("John Doe");
		assertEquals("Name should be set", "John Doe", newPerson.getName());
	}
	
	@Test
	public void testAddMeeting_valid() {
		try {
			person.addMeeting(meeting1);
			assertTrue("Person should be busy during meeting time", 
				person.isBusy(3, 15, 9, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_multipleMeetings() {
		try {
			person.addMeeting(meeting1);
			person.addMeeting(meeting2);
			assertTrue("Person should be busy during first meeting", 
				person.isBusy(3, 15, 9, 11));
			assertTrue("Person should be busy during second meeting", 
				person.isBusy(3, 15, 14, 16));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_noConflict() {
		try {
			person.addMeeting(meeting1);
			assertFalse("Person should not be busy at different time", 
				person.isBusy(3, 15, 14, 16));
			assertFalse("Person should not be busy on different day", 
				person.isBusy(3, 16, 9, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_month() {
		try {
			person.addMeeting(meeting1);
			person.addMeeting(meeting2);
			
			String agenda = person.printAgenda(3);
			assertNotNull("Agenda should not be null", agenda);
			assertTrue("Agenda should contain month info", agenda.contains("Agenda for 3:"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_day() {
		try {
			person.addMeeting(meeting1);
			
			String agenda = person.printAgenda(3, 15);
			assertNotNull("Agenda should not be null", agenda);
			assertTrue("Agenda should contain day info", agenda.contains("Agenda for 3/15:"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetMeeting() {
		try {
			person.addMeeting(meeting1);
			
			Meeting retrieved = person.getMeeting(3, 15, 0);
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
			person.addMeeting(meeting1);
			assertTrue("Person should be busy before removal", 
				person.isBusy(3, 15, 9, 11));
			
			person.removeMeeting(3, 15, 0);
			assertFalse("Person should not be busy after removal", 
				person.isBusy(3, 15, 9, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// NEGATIVE TEST CASES
	
	@Test
	public void testAddMeeting_timeConflict() {
		try {
			Meeting conflictingMeeting = new Meeting(3, 15, 10, 12);
			person.addMeeting(meeting1);
			person.addMeeting(conflictingMeeting);
			fail("Should throw TimeConflictException for overlapping meetings");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for attendee"));
			assertTrue("Should contain person name", e.getMessage().contains("Test User"));
		}
	}
	
	@Test
	public void testAddMeeting_exactOverlap() {
		try {
			Meeting exactOverlap = new Meeting(3, 15, 9, 11);
			person.addMeeting(meeting1);
			person.addMeeting(exactOverlap);
			fail("Should throw TimeConflictException for exact overlap");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for attendee"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidDate() {
		try {
			Meeting invalidMeeting = new Meeting(13, 15, 9, 11);
			person.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for invalid month");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for attendee"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidTime() {
		try {
			Meeting invalidMeeting = new Meeting(3, 15, 25, 11);
			person.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for invalid time");
		} catch(TimeConflictException e) {
			assertTrue("Should contain conflict message", e.getMessage().contains("Conflict for attendee"));
		}
	}
	
	@Test
	public void testIsBusy_invalidParameters() {
		try {
			person.isBusy(13, 15, 9, 11);
			fail("Should throw TimeConflictException for invalid month");
		} catch(TimeConflictException e) {
			assertEquals("Should throw correct error message", "Month does not exist.", e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidDay() {
		try {
			person.isBusy(3, 35, 9, 11);
			fail("Should throw TimeConflictException for invalid day");
		} catch(TimeConflictException e) {
			assertEquals("Should throw correct error message", "Day does not exist.", e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidTime() {
		try {
			person.isBusy(3, 15, 25, 11);
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
			person.addMeeting(earlyMeeting);
			person.addMeeting(lateMeeting);
			
			assertTrue("Should be busy at early time", person.isBusy(4, 10, 0, 1));
			assertTrue("Should be busy at late time", person.isBusy(4, 10, 22, 23));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_consecutiveMeetings() {
		try {
			Meeting meeting1 = new Meeting(5, 20, 9, 11);
			Meeting meeting2 = new Meeting(5, 20, 11, 13);
			person.addMeeting(meeting1);
			person.addMeeting(meeting2);
			
			assertTrue("Should be busy during first meeting", person.isBusy(5, 20, 9, 11));
			assertTrue("Should be busy during second meeting", person.isBusy(5, 20, 11, 13));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_sameTimeDifferentDays() {
		try {
			Meeting meeting1 = new Meeting(6, 10, 10, 12);
			Meeting meeting2 = new Meeting(6, 11, 10, 12);
			person.addMeeting(meeting1);
			person.addMeeting(meeting2);
			
			assertTrue("Should be busy on first day", person.isBusy(6, 10, 10, 12));
			assertTrue("Should be busy on second day", person.isBusy(6, 11, 10, 12));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetMeeting_invalidIndex() {
		try {
			person.addMeeting(meeting1);
			// This should throw IndexOutOfBoundsException
			person.getMeeting(3, 15, 1);
			fail("Should throw IndexOutOfBoundsException for invalid index");
		} catch(TimeConflictException e) {
			fail("Should not throw TimeConflictException: " + e.getMessage());
		} catch(IndexOutOfBoundsException e) {
			assertTrue("Should throw IndexOutOfBoundsException", true);
		}
	}
	
	@Test
	public void testRemoveMeeting_invalidIndex() {
		try {
			person.addMeeting(meeting1);
			// This should throw IndexOutOfBoundsException
			person.removeMeeting(3, 15, 1);
			fail("Should throw IndexOutOfBoundsException for invalid index");
		} catch(TimeConflictException e) {
			fail("Should not throw TimeConflictException: " + e.getMessage());
		} catch(IndexOutOfBoundsException e) {
			assertTrue("Should throw IndexOutOfBoundsException", true);
		}
	}
	
	@Test
	public void testAddMeeting_nullMeeting() {
		try {
			person.addMeeting(null);
			fail("Should throw NullPointerException for null meeting");
		} catch(NullPointerException e) {
			assertTrue("Should throw NullPointerException", true);
		} catch(TimeConflictException e) {
			// This might also be thrown depending on implementation
			assertTrue("Should throw TimeConflictException or NullPointerException", true);
		}
	}
}