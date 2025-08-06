package BillingSystem;
import java.util.*;
public class Main {
	 private static PatientDAO patientDAO = new PatientDAO();
	 private static InvoiceDAO invoiceDAO = new InvoiceDAO();
	 private static PaymentDAO paymentDAO = new PaymentDAO();
	 private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		 while (true) {
	            System.out.println("\n=== Billing and Payment System ===");
	            System.out.println("1. Add Patient");
	            System.out.println("2. View Patient");
	            System.out.println("3. Add Invoice");
	            System.out.println("4. View Invoice");
	            System.out.println("5. Process Payment");
	            System.out.println("6. Exit");
	            System.out.print("Choose an option: ");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); 

	            switch (choice) {
	                case 1: addPatient();
	                break;
	                case 2: viewPatient();
	                break;
	                case 3: addInvoice();
	                break;
	                case 4: viewInvoice();
	                break;
	                case 5: processPayment();
	                break;
	                case 6: {
	                    System.out.println("Exiting...");
	                    System.exit(0);
	                    break;
	                }
	                default: System.out.println("Invalid option. Please try again.");
	            }
	        }

	}
	 private static void addPatient() {
	        System.out.println("\n--- Add Patient ---");
	        System.out.print("Enter Patient ID: ");
	        int patientId = scanner.nextInt();
	        scanner.nextLine();
	        System.out.print("Enter Name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter Address: ");
	        String address = scanner.nextLine();
	        System.out.print("Enter Contact: ");
	        String contact = scanner.nextLine();

	        Patient patient = new Patient(patientId, name, address, contact);
	        patientDAO.addPatient(patient);
	        System.out.println("Patient added successfully.");
	    }

	    private static void viewPatient() {
	        System.out.println("\n--- View Patient ---");
	        System.out.print("Enter Patient ID: ");
	        int patientId = scanner.nextInt();
	        Patient patient = patientDAO.getPatient(patientId);
	        if (patient != null) {
	            System.out.println("Patient ID: " + patient.getPatientId());
	            System.out.println("Name: " + patient.getName());
	            System.out.println("Address: " + patient.getAddress());
	            System.out.println("Contact: " + patient.getContact());
	        } else {
	            System.out.println("Patient not found.");
	        }
	    }

	    private static void addInvoice() {
	        System.out.println("\n--- Add Invoice ---");
	        System.out.print("Enter Invoice ID: ");
	        int invoiceId = scanner.nextInt();
	        System.out.print("Enter Patient ID: ");
	        int patientId = scanner.nextInt();
	        System.out.print("Enter Amount: ");
	        double amount = scanner.nextDouble();
	        scanner.nextLine();

	        Invoice invoice = new Invoice(invoiceId, patientId, amount, "Pending");
	        invoiceDAO.addInvoice(invoice);
	        System.out.println("Invoice added successfully.");
	    }

	    private static void viewInvoice() {
	        System.out.println("\n--- View Invoice ---");
	        System.out.print("Enter Invoice ID: ");
	        int invoiceId = scanner.nextInt();
	        Invoice invoice = invoiceDAO.getInvoice(invoiceId);
	        if (invoice != null) {
	            System.out.println("Invoice ID: " + invoice.getInvoiceId());
	            System.out.println("Patient ID: " + invoice.getPatientId());
	            System.out.println("Amount: " + invoice.getAmount());
	            System.out.println("Status: " + invoice.getStatus());
	        } else {
	            System.out.println("Invoice not found.");
	        }
	    }

	    private static void processPayment() {
	        System.out.println("\n--- Process Payment ---");
	        System.out.print("Enter Payment ID: ");
	        int paymentId = scanner.nextInt();
	        System.out.print("Enter Invoice ID: ");
	        int invoiceId = scanner.nextInt();
	        System.out.print("Enter Payment Amount: ");
	        double amount = scanner.nextDouble();
	        scanner.nextLine();
	        System.out.print("Enter Payment Type (Credit/Debit): ");
	        String paymentType = scanner.nextLine();

	        Payment payment = paymentType.equalsIgnoreCase("Credit") ?
	                new CreditPayment(paymentId, invoiceId, amount) :
	                new DebitPayment(paymentId, invoiceId, amount);

	        try {
	            paymentDAO.addPayment(payment);
	            System.out.println("Payment processed successfully.");
	        } catch (PaymentFailedException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
}
