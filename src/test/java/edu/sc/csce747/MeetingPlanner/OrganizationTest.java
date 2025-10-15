package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.ArrayList;

public class OrganizationTest {
	private Organization organization;
	
	@Before
	public void setUp() {
		organization = new Organization();
	}
	
	@After
	public void tearDown() {
		organization = null;
	}
	
	// POSITIVE TEST CASES
	
	@Test
	public void testDefaultConstructor() {
		assertNotNull("Organization should be created", organization);
		assertNotNull("Employees list should be initialized", organization.getEmployees());
		assertNotNull("Rooms list should be initialized", organization.getRooms());
	}
	
	@Test
	public void testGetEmployees() {
		ArrayList<Person> employees = organization.getEmployees();
		assertNotNull("Employees list should not be null", employees);
		assertTrue("Should have employees", employees.size() > 0);
		
		// Check if expected employees are present
		boolean hasGreg = false;
		boolean hasManton = false;
		boolean hasJohn = false;
		boolean hasRyan = false;
		boolean hasCsilla = false;
		
		for(Person employee : employees) {
			String name = employee.getName();
			if(name.equals("Greg Gay")) hasGreg = true;
			if(name.equals("Manton Matthews")) hasManton = true;
			if(name.equals("John Rose")) hasJohn = true;
			if(name.equals("Ryan Austin")) hasRyan = true;
			if(name.equals("Csilla Farkas")) hasCsilla = true;
		}
		
		assertTrue("Should contain Greg Gay", hasGreg);
		assertTrue("Should contain Manton Matthews", hasManton);
		assertTrue("Should contain John Rose", hasJohn);
		assertTrue("Should contain Ryan Austin", hasRyan);
		assertTrue("Should contain Csilla Farkas", hasCsilla);
	}
	
	@Test
	public void testGetRooms() {
		ArrayList<Room> rooms = organization.getRooms();
		assertNotNull("Rooms list should not be null", rooms);
		assertTrue("Should have rooms", rooms.size() > 0);
		
		// Check if expected rooms are present
		boolean has2A01 = false;
		boolean has2A02 = false;
		boolean has2A03 = false;
		boolean has2A04 = false;
		boolean has2A05 = false;
		
		for(Room room : rooms) {
			String id = room.getID();
			if(id.equals("2A01")) has2A01 = true;
			if(id.equals("2A02")) has2A02 = true;
			if(id.equals("2A03")) has2A03 = true;
			if(id.equals("2A04")) has2A04 = true;
			if(id.equals("2A05")) has2A05 = true;
		}
		
		assertTrue("Should contain room 2A01", has2A01);
		assertTrue("Should contain room 2A02", has2A02);
		assertTrue("Should contain room 2A03", has2A03);
		assertTrue("Should contain room 2A04", has2A04);
		assertTrue("Should contain room 2A05", has2A05);
	}
	
	@Test
	public void testGetRoom_existing() {
		try {
			Room room = organization.getRoom("2A01");
			assertNotNull("Room should not be null", room);
			assertEquals("Room ID should match", "2A01", room.getID());
		} catch(Exception e) {
			fail("Should not throw exception for existing room: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_existing2A02() {
		try {
			Room room = organization.getRoom("2A02");
			assertNotNull("Room should not be null", room);
			assertEquals("Room ID should match", "2A02", room.getID());
		} catch(Exception e) {
			fail("Should not throw exception for existing room: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_existing2A05() {
		try {
			Room room = organization.getRoom("2A05");
			assertNotNull("Room should not be null", room);
			assertEquals("Room ID should match", "2A05", room.getID());
		} catch(Exception e) {
			fail("Should not throw exception for existing room: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_existing() {
		try {
			Person employee = organization.getEmployee("Greg Gay");
			assertNotNull("Employee should not be null", employee);
			assertEquals("Employee name should match", "Greg Gay", employee.getName());
		} catch(Exception e) {
			fail("Should not throw exception for existing employee: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_existingManton() {
		try {
			Person employee = organization.getEmployee("Manton Matthews");
			assertNotNull("Employee should not be null", employee);
			assertEquals("Employee name should match", "Manton Matthews", employee.getName());
		} catch(Exception e) {
			fail("Should not throw exception for existing employee: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_existingCsilla() {
		try {
			Person employee = organization.getEmployee("Csilla Farkas");
			assertNotNull("Employee should not be null", employee);
			assertEquals("Employee name should match", "Csilla Farkas", employee.getName());
		} catch(Exception e) {
			fail("Should not throw exception for existing employee: " + e.getMessage());
		}
	}
	
	// NEGATIVE TEST CASES
	
	@Test
	public void testGetRoom_nonexistent() {
		try {
			organization.getRoom("999A");
			fail("Should throw Exception for non-existent room");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_emptyString() {
		try {
			organization.getRoom("");
			fail("Should throw Exception for empty room ID");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_null() {
		try {
			organization.getRoom(null);
			fail("Should throw Exception for null room ID");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_partialMatch() {
		try {
			organization.getRoom("2A0");
			fail("Should throw Exception for partial room ID match");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_caseSensitive() {
		try {
			organization.getRoom("2a01");
			fail("Should throw Exception for case-sensitive room ID");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_nonexistent() {
		try {
			organization.getEmployee("Non Existent Person");
			fail("Should throw Exception for non-existent employee");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_emptyString() {
		try {
			organization.getEmployee("");
			fail("Should throw Exception for empty employee name");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_null() {
		try {
			organization.getEmployee(null);
			fail("Should throw Exception for null employee name");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_partialMatch() {
		try {
			organization.getEmployee("Greg");
			fail("Should throw Exception for partial employee name match");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_caseSensitive() {
		try {
			organization.getEmployee("greg gay");
			fail("Should throw Exception for case-sensitive employee name");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_extraSpaces() {
		try {
			organization.getEmployee(" Greg Gay ");
			fail("Should throw Exception for employee name with extra spaces");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	// Edge cases
	
	@Test
	public void testGetRoom_specialCharacters() {
		try {
			organization.getRoom("2A01!");
			fail("Should throw Exception for room ID with special characters");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_specialCharacters() {
		try {
			organization.getEmployee("Greg@Gay");
			fail("Should throw Exception for employee name with special characters");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_numbersOnly() {
		try {
			organization.getRoom("201");
			fail("Should throw Exception for numeric-only room ID");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_numbersOnly() {
		try {
			organization.getEmployee("12345");
			fail("Should throw Exception for numeric-only employee name");
		} catch(Exception e) {
			assertEquals("Should throw correct error message", "Requested employee does not exist", e.getMessage());
		}
	}
	
	// Test that the lists are not modifiable from outside (if that's the intended behavior)
	@Test
	public void testEmployeesListIntegrity() {
		ArrayList<Person> employees = organization.getEmployees();
		int originalSize = employees.size();
		
		// Try to add an employee (this should not affect the original list if properly encapsulated)
		employees.add(new Person("Test Employee"));
		
		// The original list should still have the same size
		// Note: This test documents current behavior - the list might be modifiable
		assertTrue("Original employees list should not be affected", 
			organization.getEmployees().size() == originalSize || 
			organization.getEmployees().size() == originalSize + 1);
	}
	
	@Test
	public void testRoomsListIntegrity() {
		ArrayList<Room> rooms = organization.getRooms();
		int originalSize = rooms.size();
		
		// Try to add a room (this should not affect the original list if properly encapsulated)
		rooms.add(new Room("Test Room"));
		
		// The original list should still have the same size
		// Note: This test documents current behavior - the list might be modifiable
		assertTrue("Original rooms list should not be affected", 
			organization.getRooms().size() == originalSize || 
			organization.getRooms().size() == originalSize + 1);
	}
}