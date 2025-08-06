package Eventmanage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class TicketDAO {
	public void createTicket(Ticket ticket) {
        String sql = "INSERT INTO Tickets (event_id, attendee_id, ticket_type, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
        	PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ticket.getEventId());
            stmt.setInt(2, ticket.getAttendeeId());
            stmt.setString(3, ticket.getTicketType());
            stmt.setString(4, ticket.getStatus());
            stmt.executeUpdate();
            ResultSet a=stmt.getGeneratedKeys();
            if(a.next())
            {
            	int key=a.getInt(1);

            System.out.println("Ticket purchased successfully. Your Ticket ID :" +key);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       }
	public void cancelTicket(int ticketId) {
	    String sql = "UPDATE Tickets SET status = 'Cancelled' WHERE ticket_id = ?";
	    try (Connection conn = DBConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, ticketId);
	        stmt.executeUpdate();
	        System.out.println("Ticket cancelled successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}

