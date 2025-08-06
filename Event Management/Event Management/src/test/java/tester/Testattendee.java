package tester;
import Eventmanage.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;
import static org.junit.Assert.*;
public class Testattendee {
	 private AttendeeDAO attendeeDAO;
	    private Connection connection;

	    
	    public void setUp() throws Exception {
	        attendeeDAO = new AttendeeDAO();
	        connection = DBConnect.getConnection();
	    }

	 
	    public void tearDown() throws Exception {
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    @Test
	    public void testCreateAttendee() {
	        Attendee attendee = new Attendee("John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
	        attendeeDAO.createAttendee(attendee);

	        String query = "SELECT * FROM Attendees WHERE email = 'john.doe@example.com'";
	        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
	            assertTrue(rs.next());
	            assertEquals("John", rs.getString("first_name"));
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
