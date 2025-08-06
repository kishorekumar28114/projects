package BillingSystem;

public class Patient {
	private int patientId;
	 private String name;
	 private String address;
	 private String contact;

	 // Constructor
	 public Patient(int patientId, String name, String address, String contact) {
	     this.patientId = patientId;
	     this.name = name;
	     this.address = address;
	     this.contact = contact;
	 }

	 // Getters and Setters
	 public int getPatientId() { return patientId; }
	 public String getName() { return name; }
	 public String getAddress() { return address; }
	 public String getContact() { return contact; }
}
	 
