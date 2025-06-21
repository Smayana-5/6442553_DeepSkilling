// StripeGateway.java - Another adaptee class
public class StripeGateway {
    public boolean chargeCard(double amount, String curr) {
        System.out.println("Charging " + amount + " " + curr + " through Stripe...");
        // Simulate payment processing
        boolean success = Math.random() > 0.15; // 85% success rate
        if (success) {
            System.out.println("Stripe payment successful!");
        } else {
            System.out.println("Stripe payment failed!");
        }
        return success;
    }
    
    public String getPaymentInfo() {
        return "Stripe payment processed";
    }
}