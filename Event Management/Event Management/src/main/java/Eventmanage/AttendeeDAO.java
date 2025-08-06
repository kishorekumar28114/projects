package Eventmanage;
import java.sql.*;
import java.sql.SQLException;
public class AttendeeDAO {
	  public void createAttendee(Attendee atten) {
	        String sql = "INSERT INTO Attendees (first_name, last_name, email, phone_number, address) VALUES (?, ?, ?, ?, ?)";
	        try (Connection conn = DBConnect.getConnection();
	        	PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
	            stmt.setString(1, atten.getFirstName());
	            stmt.setString(2, atten.getLastName());
	            stmt.setString(3, atten.getEmail());
	            stmt.setString(4, atten.getPhoneNumber());
	            stmt.setString(5, atten.getAddress());
	            stmt.executeUpdate();
	            ResultSet a=stmt.getGeneratedKeys();
	            if(a.next())
	            {
	            	int key=a.getInt(1);
	            	System.out.println("Attendee registered successfully. Your Id is:" +key);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}

