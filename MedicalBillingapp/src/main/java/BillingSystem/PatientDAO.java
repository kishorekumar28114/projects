package BillingSystem;
import java.sql.*;
public class PatientDAO {
	 public void addPatient(Patient patient) {
	        String sql = "INSERT INTO Patient (patient_id, name, address, contact) VALUES (?, ?, ?, ?)";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, patient.getPatientId());
	            pstmt.setString(2, patient.getName());
	            pstmt.setString(3, patient.getAddress());
	            pstmt.setString(4, patient.getContact());
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public Patient getPatient(int patientId) {
	        String sql = "SELECT * FROM Patient WHERE patient_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, patientId);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                return new Patient(rs.getInt("patient_id"), rs.getString("name"), rs.getString("address"), rs.getString("contact"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public void updatePatient(Patient patient) {
	        String sql = "UPDATE Patient SET name = ?, address = ?, contact = ? WHERE patient_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, patient.getName());
	            pstmt.setString(2, patient.getAddress());
	            pstmt.setString(3, patient.getContact());
	            pstmt.setInt(4, patient.getPatientId());
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void deletePatient(int patientId) {
	        String sql = "DELETE FROM Patient WHERE patient_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, patientId);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
