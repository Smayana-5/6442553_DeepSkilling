// SquareAdapter.java - Adapter for Square
public class SquareAdapter implements PaymentProcessor {
    private SquareGateway squareGateway;
    
    public SquareAdapter(SquareGateway squareGateway) {
        this.squareGateway = squareGateway;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        return squareGateway.processTransaction(amount, currency);
    }
    
    @Override
    public String getPaymentStatus() {
        return squareGateway.getDetails();
    }
}