// StripeAdapter.java - Adapter for Stripe
public class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripeGateway;
    
    public StripeAdapter(StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        return stripeGateway.chargeCard(amount, currency);
    }
    
    @Override
    public String getPaymentStatus() {
        return stripeGateway.getPaymentInfo();
    }
}