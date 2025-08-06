package BillingSystem;

public interface Payable {
	 void generateInvoice();
	 void makePayment(double amount) throws PaymentFailedException;
	 void viewBillingHistory();
}
