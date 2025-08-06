package BillingSystem;

public class Invoice {
	private int invoiceId;
	 private int patientId;
	 private double amount;
	 private String status;

	 // Constructor
	 public Invoice(int invoiceId, int patientId, double amount, String status) {
	     this.invoiceId = invoiceId;
	     this.patientId = patientId;
	     this.amount = amount;
	     this.status = status;
	 }

	 // Getters and Setters
	 public int getInvoiceId() { return invoiceId; }
	 public int getPatientId() { return patientId; }
	 public double getAmount() { return amount; }
	 public String getStatus() { return status; }
	 public void setStatus(String status) { this.status = status; }
}