package Eventmanage;
import java.util.Scanner;
import java.util.*;
public class Main {
	 private static EventDAO eventDAO = new EventDAO();
	 private static AttendeeDAO attendeeDAO = new AttendeeDAO();
     private static TicketDAO ticketDAO = new TicketDAO();

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Event");
            System.out.println("2. View Events");
            System.out.println("3. Register Attendee");
            System.out.println("4. Purchase Ticket");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. View Registered Attendees");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createEvent(sc);
                    break;
                case 2:
                    viewEvents();
                    break;
                case 3:
                    registerAttendee(sc);
                    break;
                case 4:
                    purchaseTicket(sc);
                    break;
                case 5:
                	cancelTicket(sc);
                	break;
                case 6:
                	viewAttendees(sc);
                	break;
                case 7:
                    System.out.println("Exits...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
	}
	 private static void createEvent(Scanner sc) {
	        System.out.print("Enter Event Name: ");
	        String eventName = sc.next();
	        sc.nextLine();
	        System.out.println("Select Event Type:");
	        eventDAO.getEventTypes();
	        System.out.print("Enter Event Type ID: ");
	        int eventTypeId = sc.nextInt();
	        System.out.print("Enter Start Date (YYYY-MM-DD HH:MM): ");
	        String startDate = sc.next();
	        sc.nextLine();
	        System.out.print("Enter End Date (YYYY-MM-DD HH:MM): ");
	        String endDate = sc.next();
	        sc.nextLine();
	        System.out.print("Enter Venue: ");
	        String venue = sc.next();
	        System.out.print("Enter Ticket Price: ");
	        double ticketPrice = sc.nextDouble();
	        System.out.print("Enter Total Seats: ");
	        int totalSeats = sc.nextInt();
	        System.out.print("Enter Available Seats: ");
	        int availableSeats = sc.nextInt();
	        System.out.print("Enter Event Description: ");
	        String eventDescription = sc.next();
	        sc.nextLine();

	        Event event = new Event(eventName, eventTypeId, startDate, endDate, venue, ticketPrice, totalSeats, availableSeats, eventDescription);
	        eventDAO.createEvent(event);
	        System.out.println("Event created successfully!");
	    }

	    private static void viewEvents() {
	        List<Event> events = eventDAO.getAllEvents();
	       if (events.isEmpty()) {
	            System.out.println("No events found.");
	        } else {
	            for (Event event : events) {
	                System.out.println("Event ID: " + event.getEventId());
	                System.out.println("Name: " + event.getEventName());
	                System.out.println("Type: " + event.getEventTypeid());
	                System.out.println("Start Date: " + event.getStartDate());
	                System.out.println("End Date: " + event.getEndDate());
	                System.out.println("Venue: " + event.getVenue());
	                System.out.println("Ticket Price: " + event.getTicketPrice());
	                System.out.println("Available Seats: " + event.getAvailableSeats());
	                System.out.println("Description: " + event.getEventDescription());
	                System.out.println("\n--------------------------------------");
	            }
	        }
	    }

	    private static void registerAttendee(Scanner sc) {
	        System.out.print("Enter First Name: ");
	        String firstName = sc.next();
	        System.out.print("Enter Last Name: ");
	        String lastName = sc.next();
	        System.out.print("Enter Email: ");
	        String email = sc.next();
	        System.out.print("Enter Phone Number: ");
	        String phoneNumber = sc.next();
	        System.out.print("Enter Address: ");
	        String address = sc.next();

	        Attendee attendee = new Attendee(firstName, lastName, email, phoneNumber, address);
	        attendeeDAO.createAttendee(attendee);
	    }

	    private static void purchaseTicket(Scanner sc) {
	    	viewEvents();
	        System.out.print("Enter Event ID: ");
	        int eventId = sc.nextInt();
	        System.out.print("Enter Attendee ID: ");
	        int attendeeId = sc.nextInt();
	        System.out.print("Enter Ticket Type ( Regular, VIP): ");
	        String ticketType = sc.next();
	        Ticket ticket = new Ticket(eventId, attendeeId, ticketType, "Active");
	        ticketDAO.createTicket(ticket);

	        eventDAO.updateSeats(eventId, -1);
	    }
	    private static void cancelTicket(Scanner sc) {
	        System.out.print("Enter Ticket ID to cancel: ");
	        int ticketId = sc.nextInt();
	        ticketDAO.cancelTicket(ticketId);
	    }
	    private static void viewAttendees(Scanner sc) {
	        System.out.print("Enter Event ID to view attendees: ");
	        int eventId = sc.nextInt();
	        List<Attendee> attendees = eventDAO.getAttendeesByEventId(eventId);

	        if (attendees.isEmpty()) {
	            System.out.println("No attendees registered for this event.");
	        } else {
	            for (Attendee attendee : attendees) {
	                System.out.println("Attendee ID: " + attendee.getAttendeeId());
	                System.out.println("Name: " + attendee.getFirstName() + " " + attendee.getLastName());
	                System.out.println("Email: " + attendee.getEmail());
	                System.out.println("Phone: " + attendee.getPhoneNumber());
	                System.out.println("Address: " + attendee.getAddress());
	                System.out.println("--------------------------------------");
	            }
	        }
	    }

}

