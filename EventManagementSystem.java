package ems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class EventManagementSystem {
    
    private static final String DB_URL = "jdbc:mysql://localhost/event_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    
    private Connection conn;
    
    public EventManagementSystem() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public void addEvent() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter event name:");
            String name = scanner.nextLine();
            
            System.out.println("Enter event date (YYYY-MM-DD):");
            String dateStr = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateStr);
            
            System.out.println("Enter event time (HH:MM:SS):");
            String timeStr = scanner.nextLine();
            LocalTime time = LocalTime.parse(timeStr);
            
            System.out.println("Enter event description:");
            String description = scanner.nextLine();
            
            System.out.println("Enter venue ID:");
            int venueId = scanner.nextInt();
            
            if (!isVenueExists(venueId)) {
                System.out.println("Venue with ID " + venueId + " does not exist.");
                return;
            }
            
            String sql = "INSERT INTO Events (name, date, time, description, venue_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDate(2, java.sql.Date.valueOf(date));
                pstmt.setTime(3, java.sql.Time.valueOf(time));
                pstmt.setString(4, description);
                pstmt.setInt(5, venueId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Event added successfully!");
                } else {
                    System.out.println("Failed to add event.");
                }
            }
            
        } catch (SQLException | DateTimeParseException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void updateEvent() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter event ID to update:");
            int eventId = scanner.nextInt();
            
            System.out.println("Enter new event name:");
            String name = scanner.nextLine(); // Consume the newline character
            name = scanner.nextLine(); // Read the actual input
            
            System.out.println("Enter new event date (YYYY-MM-DD):");
            String dateStr = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateStr);
            
            System.out.println("Enter new event time (HH:MM:SS):");
            String timeStr = scanner.nextLine();
            LocalTime time = LocalTime.parse(timeStr);
            
            System.out.println("Enter new event description:");
            String description = scanner.nextLine();
            
            System.out.println("Enter new venue ID:");
            int venueId = scanner.nextInt();
            
            if (!isVenueExists(venueId)) {
                System.out.println("Venue with ID " + venueId + " does not exist.");
                return;
            }
            
            String sql = "UPDATE Events SET name = ?, date = ?, time = ?, description = ?, venue_id = ? WHERE event_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDate(2, java.sql.Date.valueOf(date));
                pstmt.setTime(3, java.sql.Time.valueOf(time));
                pstmt.setString(4, description);
                pstmt.setInt(5, venueId);
                pstmt.setInt(6, eventId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Event updated successfully!");
                } else {
                    System.out.println("Failed to update event.");
                }
            }
            
        } catch (SQLException | DateTimeParseException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void deleteEvent() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter event ID to delete:");
            int eventId = scanner.nextInt();
            
            String sql = "DELETE FROM Events WHERE event_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, eventId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Event deleted successfully!");
                } else {
                    System.out.println("Failed to delete event. Event with ID " + eventId + " does not exist.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addParticipant() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter participant name:");
            String name = scanner.nextLine();
            
            System.out.println("Enter participant email:");
            String email = scanner.nextLine();
            
            String sql = "INSERT INTO Participants (name, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Participant added successfully!");
                } else {
                    System.out.println("Failed to add participant.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void updateParticipant() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter participant ID to update:");
            int participantId = scanner.nextInt();
            
            System.out.println("Enter new participant name:");
            String name = scanner.nextLine(); // Consume the newline character
            name = scanner.nextLine(); // Read the actual input
            
            System.out.println("Enter new participant email:");
            String email = scanner.nextLine();
            
            String sql = "UPDATE Participants SET name = ?, email = ? WHERE participant_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, participantId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Participant updated successfully!");
                } else {
                    System.out.println("Failed to update participant.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void deleteParticipant() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter participant ID to delete:");
            int participantId = scanner.nextInt();
            
            String sql = "DELETE FROM Participants WHERE participant_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, participantId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Participant deleted successfully!");
                } else {
                    System.out.println("Failed to delete participant. Participant with ID " + participantId + " does not exist.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addVenue() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter venue name:");
            String name = scanner.nextLine();
            
            System.out.println("Enter venue address:");
            String address = scanner.nextLine();
            
            String sql = "INSERT INTO Venues (name, address) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, address);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Venue added successfully!");
                } else {
                    System.out.println("Failed to add venue.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void updateVenue() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter venue ID to update:");
            int venueId = scanner.nextInt();
            
            System.out.println("Enter new venue name:");
            String name = scanner.nextLine(); // Consume the newline character
            name = scanner.nextLine(); // Read the actual input
            
            System.out.println("Enter new venue address:");
            String address = scanner.nextLine();
            
            String sql = "UPDATE Venues SET name = ?, address = ? WHERE venue_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, address);
                pstmt.setInt(3, venueId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Venue updated successfully!");
                } else {
                    System.out.println("Failed to update venue.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void deleteVenue() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter venue ID to delete:");
            int venueId = scanner.nextInt();
            
            String sql = "DELETE FROM Venues WHERE venue_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, venueId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Venue deleted successfully!");
                } else {
                    System.out.println("Failed to delete venue. Venue with ID " + venueId + " does not exist.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public boolean isVenueExists(int venueId) {
        try {
            String sql = "SELECT COUNT(*) FROM Venues WHERE venue_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, venueId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking venue existence: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        EventManagementSystem ems = new EventManagementSystem();
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Event Management System");
                System.out.println("1. Add Event");
                System.out.println("2. Update Event");
                System.out.println("3. Delete Event");
                System.out.println("4. Add Participant");
                System.out.println("5. Update Participant");
                System.out.println("6. Delete Participant");
                System.out.println("7. Add Venue");
                System.out.println("8. Update Venue");
                System.out.println("9. Delete Venue");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        ems.addEvent();
                        break;
                    case 2:
                        ems.updateEvent();
                        break;
                    case 3:
                        ems.deleteEvent();
                        break;
                    case 4:
                        ems.addParticipant();
                        break;
                    case 5:
                        ems.updateParticipant();
                        break;
                    case 6:
                        ems.deleteParticipant();
                        break;
                    case 7:
                        ems.addVenue();
                        break;
                    case 8:
                        ems.updateVenue();
                        break;
                    case 9:
                        ems.deleteVenue();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }
}
