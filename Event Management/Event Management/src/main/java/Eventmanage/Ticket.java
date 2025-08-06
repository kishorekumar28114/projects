package Eventmanage;

public class Ticket {
	 private int ticketId;
	    private int eventId;
	    private int attendeeId;
	    private String ticketType;
	    private String purchaseDate;
	    private String status;

	    public Ticket(int eventId, int attendeeId, String ticketType, String status) {
	        this.eventId = eventId;
	        this.attendeeId = attendeeId;
	        this.ticketType = ticketType;
	        this.status = status;
	    }
	    public int getTicketId() 
	    {
	    	return ticketId; 
	    }
	    public void setTicketId(int ticketId) 
	    {
	    	this.ticketId = ticketId; 
	    }
	    public int getEventId() 
	    {
	    	return eventId; 
	    }
	    public int getAttendeeId() 
	    {
	    	return attendeeId; 
	    }
	    public String getTicketType() 
	    {
	    	return ticketType; 
	    }
	    public String getPurchaseDate() 
	    {
	    	return purchaseDate; 
	    }
	    public String getStatus() 
	    {
	    	return status; 
	    }
}


