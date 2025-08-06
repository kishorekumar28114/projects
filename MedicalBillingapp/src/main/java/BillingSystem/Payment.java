package BillingSystem;

public abstract class Payment {
    protected int paymentId;
    protected int invoiceId;
    protected double amount;
    protected String paymentType;

    public Payment(int paymentId, int invoiceId, double amount, String paymentType) {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public abstract void processPayment() throws PaymentFailedException;

	public int getInvoiceId() {
		// TODO Auto-generated method stub
		return invoiceId;
	}

	public double getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}

	public int getpaymentId() {
		// TODO Auto-generated method stub
		return paymentId;
	}

	public String getPaymentType() {
		// TODO Auto-generated method stub
		return paymentType;
	}
}

class CreditPayment extends Payment {
    public CreditPayment(int paymentId, int invoiceId, double amount) {
        super(paymentId, invoiceId, amount, "Credit");
    }

    @Override
    public void processPayment() throws PaymentFailedException {
        if (amount <= 0) throw new PaymentFailedException("Invalid payment amount for credit.");
        System.out.println("Processing credit payment of $" + amount + " for invoice " + invoiceId);
    }
}

class DebitPayment extends Payment {
    public DebitPayment(int paymentId, int invoiceId, double amount) {
        super(paymentId, invoiceId, amount, "Debit");
    }

    @Override
    public void processPayment() throws PaymentFailedException {
        if (amount <= 0) throw new PaymentFailedException("Invalid payment amount for debit.");
        System.out.println("Processing debit payment of $" + amount + " for invoice " + invoiceId);
    }
}
