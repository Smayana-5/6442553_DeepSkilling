// PaymentProcessor.java - Target interface
public interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
    String getPaymentStatus();
}




// AdapterPatternTest.java
