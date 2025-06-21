// PayPalGateway.java - Adaptee class
public class PayPalGateway {
    public boolean makePayment(double amount) {
        System.out.println("Processing $" + amount + " payment through PayPal...");
        // Simulate payment processing
        boolean success = Math.random() > 0.1; // 90% success rate
        if (success) {
            System.out.println("PayPal payment successful!");
        } else {
            System.out.println("PayPal payment failed!");
        }
        return success;
    }
    
    public String getTransactionStatus() {
        return "PayPal transaction completed";
    }
}