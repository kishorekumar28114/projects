package BillingSystem;
import java.sql.*;
public class InvoiceDAO {
	 public void addInvoice(Invoice invoice) {
	        String sql = "INSERT INTO Invoice (invoice_id, patient_id, amount, status) VALUES (?, ?, ?, ?)";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, invoice.getInvoiceId());
	            pstmt.setInt(2, invoice.getPatientId());
	            pstmt.setDouble(3, invoice.getAmount());
	            pstmt.setString(4, invoice.getStatus());
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public Invoice getInvoice(int invoiceId) {
	        String sql = "SELECT * FROM Invoice WHERE invoice_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, invoiceId);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                return new Invoice(rs.getInt("invoice_id"), rs.getInt("patient_id"), rs.getDouble("amount"), rs.getString("status"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public void updateInvoiceStatus(int invoiceId, String status) {
	        String sql = "UPDATE Invoice SET status = ? WHERE invoice_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, status);
	            pstmt.setInt(2, invoiceId);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void deleteInvoice(int invoiceId) {
	        String sql = "DELETE FROM Invoice WHERE invoice_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, invoiceId);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
