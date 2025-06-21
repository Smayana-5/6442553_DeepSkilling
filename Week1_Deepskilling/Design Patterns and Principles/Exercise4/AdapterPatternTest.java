import java.util.Scanner;

public class AdapterPatternTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Payment Processing System ===\n");
        
        // Create different payment gateways
        PayPalGateway payPalGateway = new PayPalGateway();
        StripeGateway stripeGateway = new StripeGateway();
        SquareGateway squareGateway = new SquareGateway();
        
        // Create adapters
        PaymentProcessor payPalProcessor = new PayPalAdapter(payPalGateway);
        PaymentProcessor stripeProcessor = new StripeAdapter(stripeGateway);
        PaymentProcessor squareProcessor = new SquareAdapter(squareGateway);
        
        while (true) {
            System.out.println("\nChoose a payment method:");
            System.out.println("1. PayPal");
            System.out.println("2. Stripe");
            System.out.println("3. Square");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            
            int choice = scanner.nextInt();
            
            if (choice == 4) {
                System.out.println("Thank you for using our payment system!");
                break;
            }
            
            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            
            System.out.print("Enter payment amount: $");
            double amount = scanner.nextDouble();
            
            System.out.print("Enter currency (USD, EUR, GBP): ");
            String currency = scanner.next().toUpperCase();
            
            PaymentProcessor processor = null;
            String paymentMethod = "";
            
            switch (choice) {
                case 1:
                    processor = payPalProcessor;
                    paymentMethod = "PayPal";
                    break;
                case 2:
                    processor = stripeProcessor;
                    paymentMethod = "Stripe";
                    break;
                case 3:
                    processor = squareProcessor;
                    paymentMethod = "Square";
                    break;
            }
            
            System.out.println("\n--- Processing Payment via " + paymentMethod + " ---");
            boolean success = processor.processPayment(amount, currency);
            
            if (success) {
                System.out.println("✓ Payment Status: " + processor.getPaymentStatus());
            } else {
                System.out.println("✗ Payment failed. Please try again.");
            }
            
            System.out.println("----------------------------------------");
        }
        
        scanner.close();
    }
}
