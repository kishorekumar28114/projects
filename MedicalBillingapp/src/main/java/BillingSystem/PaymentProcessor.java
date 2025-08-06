package BillingSystem;

public class PaymentProcessor extends Thread {
    private Payment payment;

    public PaymentProcessor(Payment payment) {
        this.payment = payment;
    }

    @Override
    public void run() {
        try {
            payment.processPayment();
            System.out.println("Payment processed: " + payment.amount);
        } catch (PaymentFailedException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }
}

