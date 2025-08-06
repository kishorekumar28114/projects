package Eventmanage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EventDAO {
	 public void createEvent(Event event) {
	        String sql = "INSERT INTO Events (event_name, event_type_id, start_date, end_date, venue, ticket_price, total_seats, available_seats, event_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection conn = DBConnect.getConnection();
	           PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, event.getEventName());
	            stmt.setInt(2, event.getEventTypeid());
	            stmt.setString(3, event.getStartDate());
	            stmt.setString(4, event.getEndDate());
	            stmt.setString(5, event.getVenue());
	            stmt.setDouble(6, event.getTicketPrice());
	            stmt.setInt(7, event.getTotalSeats());
	            stmt.setInt(8, event.getAvailableSeats());
	            stmt.setString(9, event.getEventDescription());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Event> getAllEvents() {
	        List<Event> events = new ArrayList<>();
	        String sql = "SELECT * FROM Events";
	        try (Connection conn = DBConnect.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
	            while (rs.next()) {
	                Event event = new Event(
	                        rs.getString("event_name"),
	                        rs.getInt("event_type_id"),
	                        rs.getString("start_date"),
	                        rs.getString("end_date"),
	                        rs.getString("venue"),
	                        rs.getDouble("ticket_price"),
	                        rs.getInt("total_seats"),
	                        rs.getInt("available_seats"),
	                        rs.getString("event_description")
	                );
	                event.setEventId(rs.getInt("event_id"));
	                events.add(event);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return events;
	    }
	    public void updateSeats(int eventId, int seatAdjustment) {
	        String sql = "UPDATE Events SET available_seats = available_seats + ? WHERE event_id = ?";
	        try (Connection conn = DBConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, seatAdjustment);
	            stmt.setInt(2, eventId);
	            stmt.executeUpdate();
	            System.out.println("Event seat count updated.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void getEventTypes() {
	        String sql = "SELECT event_type_id, event_type_name FROM EventTypes";
	        try (Connection conn = DBConnect.getConnection();
	        	Statement stmt = conn.createStatement(); 
	        	ResultSet rs = stmt.executeQuery(sql)) {
	            while (rs.next()) {
	                System.out.println(rs.getInt("event_type_id")+ rs.getString("event_type_name"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public List<Attendee> getAttendeesByEventId(int eventId) {
	        List<Attendee> attendees = new ArrayList<>();
	        String sql = "{CALL GetAttendees(?)}"; 

	        try (Connection conn = DBConnect.getConnection();
	        	CallableStatement stmt = conn.prepareCall(sql)) {
	            stmt.setInt(1, eventId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Attendee attendee = new Attendee(
	                        rs.getString("first_name"),
	                        rs.getString("last_name"),
	                        rs.getString("email"),
	                        rs.getString("phone_number"),
	                        rs.getString("address")
	                    );
	                    attendee.setAttendeeId(rs.getInt("attendee_id"));
	                    attendees.add(attendee);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return attendees;
	    }
}


