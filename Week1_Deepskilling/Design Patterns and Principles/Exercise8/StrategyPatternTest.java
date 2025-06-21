// ======= Exercise 8: Strategy Pattern - Payment System =======

// 1. Define Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// 2. Implement Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String holderName;
    
    public CreditCardPayment(String cardNumber, String holderName) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing $" + amount + " payment via Credit Card");
        System.out.println("Card Number: ****-****-****-" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Cardholder: " + holderName);
        System.out.println("Credit Card payment successful!\n");
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Processing $" + amount + " payment via PayPal");
        System.out.println("PayPal Account: " + email);
        System.out.println("PayPal payment successful!\n");
    }
}

// 3. Implement Context Class
class PaymentContext {
    private PaymentStrategy strategy;
    
    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void executePayment(double amount) {
        if (strategy == null) {
            System.out.println("No payment strategy selected!");
            return;
        }
        strategy.pay(amount);
    }
}

// 4. Test Class
public class StrategyPatternTest {
    public static void main(String[] args) {
        System.out.println("=== Strategy Pattern - Payment System Demo ===\n");
        
        // Create different payment strategies
        PaymentStrategy creditCard = new CreditCardPayment("1234567890123456", "John Doe");
        PaymentStrategy paypal = new PayPalPayment("john.doe@email.com");
        
        // Create payment context
        PaymentContext paymentContext = new PaymentContext(creditCard);
        
        // Test Credit Card payment
        System.out.println("1. Testing Credit Card Payment:");
        paymentContext.executePayment(150.75);
        
        // Switch to PayPal payment
        System.out.println("2. Testing PayPal Payment:");
        paymentContext.setStrategy(paypal);
        paymentContext.executePayment(89.99);
        
        // Test runtime strategy selection
        System.out.println("3. Runtime Strategy Selection:");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        System.out.print("Enter payment amount: $");
        double amount = scanner.nextDouble();
        
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.print("Enter choice (1 or 2): ");
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                paymentContext.setStrategy(new CreditCardPayment("9876543210987654", "Jane Smith"));
                break;
            case 2:
                paymentContext.setStrategy(new PayPalPayment("jane.smith@email.com"));
                break;
            default:
                System.out.println("Invalid choice!");
                scanner.close();
                return;
        }
        
        paymentContext.executePayment(amount);
        scanner.close();
        
        System.out.println("Strategy Pattern demonstration completed!");
    }
}