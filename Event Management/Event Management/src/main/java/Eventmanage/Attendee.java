package Eventmanage;

public class Attendee {
	 private int attendeeId;
	 private String firstName;
	 private String lastName;
	 private String email;
     private String phoneNumber;
     private String address;

	    public Attendee(String firstName, String lastName, String email, String phoneNumber, String address) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.email = email;
	        this.phoneNumber = phoneNumber;
	        this.address = address;
	    }

	    public int getAttendeeId() 
	    { 
	    	return attendeeId; 
	    }
	    public void setAttendeeId(int attendeeId) 
	    { 
	    	this.attendeeId = attendeeId;
	    }
	    public String getFirstName() 
	    {
	    	return firstName; 
	    }
	    public String getLastName() 
	    {
	    	return lastName; 
	    }
	    public String getEmail() 
	    {
	    	return email;
	    }
	    public String getPhoneNumber() 
	    {
	    	return phoneNumber; 
	    }
	    public String getAddress() 
	    {
	    	return address; 
	    }
}

