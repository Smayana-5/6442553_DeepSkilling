// PayPalAdapter.java - Adapter for PayPal
public class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPalGateway;
    
    public PayPalAdapter(PayPalGateway payPalGateway) {
        this.payPalGateway = payPalGateway;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        // Convert currency if needed (simplified)
        if (!currency.equals("USD")) {
            System.out.println("Converting " + currency + " to USD...");
        }
        return payPalGateway.makePayment(amount);
    }
    
    @Override
    public String getPaymentStatus() {
        return payPalGateway.getTransactionStatus();
    }
}

