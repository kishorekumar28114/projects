package BillingSystem;
import java.sql.*;
public class PaymentDAO {
	 public void addPayment(Payment payment) throws PaymentFailedException {
	        String sql = "INSERT INTO Payment (payment_id, invoice_id, amount, payment_type, payment_date) VALUES (?, ?, ?, ?, CURDATE())";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, payment.getpaymentId());
	            pstmt.setInt(2, payment.getInvoiceId());
	            pstmt.setDouble(3, payment.getAmount());
	            pstmt.setString(4, payment.getPaymentType());
	            pstmt.executeUpdate();
	            updateInvoiceStatus(payment.getInvoiceId(), "Paid");
	        } catch (SQLException e) {
	            throw new PaymentFailedException("Payment processing failed: " + e.getMessage());
	        }
	    }

	    private void updateInvoiceStatus(int invoiceId, String status) throws SQLException {
	        String sql = "UPDATE Invoice SET status = ? WHERE invoice_id = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, status);
	            pstmt.setInt(2, invoiceId);
	            pstmt.executeUpdate();
	        }
	    }
	}
   
