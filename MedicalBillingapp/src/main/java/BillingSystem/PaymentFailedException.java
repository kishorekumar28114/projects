package BillingSystem;

public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}

class InvalidInvoiceException extends Exception {
    public InvalidInvoiceException(String message) {
        super(message);
    }
}