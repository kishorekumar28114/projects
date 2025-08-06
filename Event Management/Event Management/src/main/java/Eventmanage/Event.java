package Eventmanage;

public class Event {
	 private int eventId;
	    private String eventName;
	    private int eventTypeid;
	    private String startDate;
	    private String endDate;
	    private String venue;
	    private double ticketPrice;
	    private int totalSeats;
	    private int availableSeats;
	    private String eventDescription;

	    public Event(String eventName, int eventTypeid, String startDate, String endDate, String venue, double ticketPrice, int totalSeats, int availableSeats, String eventDescription) {
	        this.eventName = eventName;
	        this.eventTypeid = eventTypeid;
	        this.startDate = startDate;
	        this.endDate = endDate;
	        this.venue = venue;
	        this.ticketPrice = ticketPrice;
	        this.totalSeats = totalSeats;
	        this.availableSeats = availableSeats;
	        this.eventDescription = eventDescription;
	    }

	    public int getEventId()
	    {
	    	return eventId; 
	    }
	    public void setEventId(int eventId)
	    {
	    	this.eventId = eventId;
	    }
	    public String getEventName()
	    {
	    	return eventName;
	    }
	    public int getEventTypeid() 
	    {
	    	return eventTypeid; 
	    }
	    public String getStartDate() 
	    {
	    	return startDate; 
	    }
	    public String getEndDate() 
	    { 
	    	return endDate; 
	    }
	    public String getVenue() 
	    {
	    	return venue;
	    }
	    public double getTicketPrice() 
	    {
	    	return ticketPrice;
	    }
	    public int getTotalSeats() 
	    {
	    	return totalSeats; 
	    }
	    public int getAvailableSeats()
	    {
	    	return availableSeats; 
	    }
	    public String getEventDescription() 
	    {
	    	return eventDescription;
	    }
}

