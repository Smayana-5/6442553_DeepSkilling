// SquareGateway.java - Third adaptee class
public class SquareGateway {
    public boolean processTransaction(double amt, String currency) {
        System.out.println("Processing " + amt + " " + currency + " via Square...");
        // Simulate payment processing
        boolean success = Math.random() > 0.2; // 80% success rate
        if (success) {
            System.out.println("Square payment successful!");
        } else {
            System.out.println("Square payment failed!");
        }
        return success;
    }
    
    public String getDetails() {
        return "Square payment details available";
    }
}

