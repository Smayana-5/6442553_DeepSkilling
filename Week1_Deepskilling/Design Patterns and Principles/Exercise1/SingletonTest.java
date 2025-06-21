// SingletonPatternExample - Complete implementation
import java.util.Scanner;

// Logger.java - Singleton Pattern Implementation
class Logger {
    // Private static instance of the Logger class
    private static Logger instance;
    
    // Private constructor to prevent external instantiation
    private Logger() {
        System.out.println("Logger instance created!");
    }
    
    // Public static method to get the singleton instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    // Method to log messages
    public void log(String message) {
        System.out.println("[LOG] " + java.time.LocalDateTime.now() + ": " + message);
    }
    
    // Method to log error messages
    public void logError(String error) {
        System.out.println("[ERROR] " + java.time.LocalDateTime.now() + ": " + error);
    }
    
    // Method to log info messages
    public void logInfo(String info) {
        System.out.println("[INFO] " + java.time.LocalDateTime.now() + ": " + info);
    }
}

// SingletonTest.java - Test class to verify Singleton implementation
public class SingletonTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Singleton Pattern Demo - Logger ===");
        System.out.println("This demo shows that only one Logger instance is created.\n");
        
        // Get first instance
        System.out.println("1. Getting first Logger instance...");
        Logger logger1 = Logger.getInstance();
        
        // Get second instance
        System.out.println("\n2. Getting second Logger instance...");
        Logger logger2 = Logger.getInstance();
        
        // Check if both references point to the same object
        System.out.println("\n3. Testing if both instances are the same:");
        System.out.println("logger1 == logger2: " + (logger1 == logger2));
        System.out.println("logger1.hashCode(): " + logger1.hashCode());
        System.out.println("logger2.hashCode(): " + logger2.hashCode());
        
        // Interactive logging demo
        System.out.println("\n=== Interactive Logging Demo ===");
        boolean continueLogging = true;
        
        while (continueLogging) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Log a message");
            System.out.println("2. Log an error");
            System.out.println("3. Log info");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter message to log: ");
                        String message = scanner.nextLine();
                        logger1.log(message);
                        break;
                    case 2:
                        System.out.print("Enter error to log: ");
                        String error = scanner.nextLine();
                        logger2.logError(error);
                        break;
                    case 3:
                        System.out.print("Enter info to log: ");
                        String info = scanner.nextLine();
                        logger1.logInfo(info);
                        break;
                    case 4:
                        continueLogging = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter 1-4.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Final verification
        System.out.println("\n=== Final Verification ===");
        Logger logger3 = Logger.getInstance();
        System.out.println("Third instance created? " + (logger3 == logger1));
        System.out.println("All three references point to same object: " + 
                          (logger1 == logger2 && logger2 == logger3));
        
        scanner.close();
        System.out.println("\nSingleton Pattern demonstration completed!");
    }
}