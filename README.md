# MeetingPlanner

A Java-based meeting planning system that allows scheduling meetings, managing attendees, and room assignments.

## Project Structure

```
MeetingPlanner/
├── src/
│   ├── main/java/edu/sc/csce747/MeetingPlanner/
│   │   ├── Calendar.java          # Calendar management
│   │   ├── Meeting.java           # Meeting entity
│   │   ├── Person.java            # Person/attendee management
│   │   ├── Room.java              # Room management
│   │   ├── Organization.java      # Organization setup
│   │   ├── PlannerInterface.java  # Main interface
│   │   └── TimeConflictException.java # Custom exception
│   └── test/java/edu/sc/csce747/MeetingPlanner/
│       ├── CalendarTest.java      # Calendar unit tests
│       ├── MeetingTest.java       # Meeting unit tests
│       ├── PersonTest.java        # Person unit tests
│       ├── RoomTest.java          # Room unit tests
│       └── OrganizationTest.java  # Organization unit tests
├── build.xml                      # Ant build configuration
├── README.md                      # This file
└── doc/                          # Generated Javadoc documentation
```

## Features

- **Calendar Management**: Schedule and manage meetings with conflict detection
- **Meeting Scheduling**: Create meetings with attendees, rooms, and time slots
- **Person Management**: Track individual schedules and availability
- **Room Management**: Manage room availability and assignments
- **Organization Setup**: Pre-configured employees and rooms
- **Time Conflict Detection**: Prevents double-booking of people and rooms

## Building the Project

This project uses Apache Ant as the build tool. Make sure you have Java 8+ and Ant installed.

### Prerequisites

- Java 8 or higher
- Apache Ant 1.9.0 or higher

### Build Commands

```bash
# Clean build directory
ant clean

# Compile source code
ant compile

# Run unit tests
ant test

# Generate Javadoc documentation
ant javadoc

# Run all tests and generate reports
ant test-report

# Build everything (compile, test, javadoc)
ant build

# Create JAR file
ant jar

# Run the application
ant run

# Show help
ant help
```

### Quick Start

1. Clone or download the project
2. Navigate to the project directory
3. Run `ant build` to compile, test, and generate documentation
4. Run `ant run` to start the application

## Testing

The project includes comprehensive unit tests using JUnit 4. Tests cover:

- **Positive test cases**: Valid operations and expected behavior
- **Negative test cases**: Error conditions and edge cases
- **Boundary testing**: Edge values and limits
- **Exception testing**: Proper exception handling

### Test Coverage

- **Calendar**: 25+ test cases covering scheduling, conflict detection, and validation
- **Meeting**: 20+ test cases covering creation, modification, and edge cases
- **Person**: 15+ test cases covering schedule management and conflict detection
- **Room**: 15+ test cases covering room availability and scheduling
- **Organization**: 15+ test cases covering employee and room management

### Running Tests

```bash
# Run all tests
ant test

# Run tests with detailed output
ant test-detailed

# Generate test report
ant test-report
```

## Known Issues and Bugs Found

During testing, several bugs were identified:

1. **Calendar.java line 85**: Month validation is incorrect - should be `mMonth > 12` not `mMonth >= 12`
2. **Calendar.java line 42**: November 31st is incorrectly marked as non-existent (should be November 30th)
3. **Meeting.java line 103**: toString() method will throw NullPointerException if room or attendees are null
4. **Meeting.java line 87**: addAttendee() will throw NullPointerException if attendees list is null

## API Documentation

Javadoc documentation is automatically generated and available in the `doc/` directory after running `ant javadoc`.

## License

This project is part of a university assignment for CSCE 747.

## Author

MeetingPlanner Project - CSCE 747 Assignment
