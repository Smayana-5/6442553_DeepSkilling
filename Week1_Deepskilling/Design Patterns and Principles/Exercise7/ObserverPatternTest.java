// ObserverPatternTest.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObserverPatternTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Stock Market Monitoring System ===\n");
        
        // Create stock market
        StockMarket stockMarket = new StockMarket();
        
        // Create observers
        List<Observer> availableObservers = new ArrayList<>();
        availableObservers.add(new MobileApp("StockTracker Pro", "user123"));
        availableObservers.add(new MobileApp("InvestorAlert", "trader456"));
        availableObservers.add(new WebApp("FinancePortal.com", "investor@email.com"));
        availableObservers.add(new WebApp("MarketWatch.com", "analyst@email.com"));
        availableObservers.add(new EmailNotificationService("CriticalAlerts"));
        
        // Register some observers initially
        stockMarket.registerObserver(availableObservers.get(0));
        stockMarket.registerObserver(availableObservers.get(2));
        
        stockMarket.displayAllStocks();
        
        while (true) {
            System.out.println("\n=== Stock Market Menu ===");
            System.out.println("1. Update stock price manually");
            System.out.println("2. Simulate market activity");
            System.out.println("3. Add new stock");
            System.out.println("4. Register observer");
            System.out.println("5. Deregister observer");
            System.out.println("6. View all stocks");
            System.out.println("7. Auto-simulate market (5 updates)");
            System.out.println("8. Exit");
            System.out.print("Choose an option (1-8): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    updateStockManually(scanner, stockMarket);
                    break;
                case 2:
                    stockMarket.simulateMarketActivity();
                    break;
                case 3:
                    addNewStock(scanner, stockMarket);
                    break;
                case 4:
                    registerObserver(scanner, stockMarket, availableObservers);
                    break;
                case 5:
                    deregisterObserver(scanner, stockMarket, availableObservers);
                    break;
                case 6:
                    stockMarket.displayAllStocks();
                    break;
                case 7:
                    autoSimulateMarket(stockMarket);
                    break;
                case 8:
                    System.out.println("Thank you for using the Stock Market Monitoring System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void updateStockManually(Scanner scanner, StockMarket stockMarket) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter new price: $");
        double price = scanner.nextDouble();
        stockMarket.updateStockPrice(symbol, price);
    }
    
    private static void addNewStock(Scanner scanner, StockMarket stockMarket) {
        System.out.print("Enter new stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter initial price: $");
        double price = scanner.nextDouble();
        stockMarket.addNewStock(symbol, price);
    }
    
    private static void registerObserver(Scanner scanner, StockMarket stockMarket, List<Observer> availableObservers) {
        System.out.println("\nAvailable observers:");
        for (int i = 0; i < availableObservers.size(); i++) {
            Observer obs = availableObservers.get(i);
            String info = "";
            if (obs instanceof MobileApp) {
                info = ((MobileApp) obs).getAppInfo();
            } else if (obs instanceof WebApp) {
                info = ((WebApp) obs).getWebInfo();
            } else if (obs instanceof EmailNotificationService) {
                info = "Email Service";
            }
            System.out.println((i + 1) + ". " + info);
        }
        
        System.out.print("Select observer to register (1-" + availableObservers.size() + "): ");
        int index = scanner.nextInt() - 1;
        
        if (index >= 0 && index < availableObservers.size()) {
            stockMarket.registerObserver(availableObservers.get(index));
        } else {
            System.out.println("Invalid selection!");
        }
    }
    
    private static void deregisterObserver(Scanner scanner, StockMarket stockMarket, List<Observer> availableObservers) {
        System.out.println("\nSelect observer to deregister:");
        for (int i = 0; i < availableObservers.size(); i++) {
            Observer obs = availableObservers.get(i);
            String info = "";
            if (obs instanceof MobileApp) {
                info = ((MobileApp) obs).getAppInfo();
            } else if (obs instanceof WebApp) {
                info = ((WebApp) obs).getWebInfo();
            } else if (obs instanceof EmailNotificationService) {
                info = "Email Service";
            }
            System.out.println((i + 1) + ". " + info);
        }
        
        System.out.print("Select observer to deregister (1-" + availableObservers.size() + "): ");
        int index = scanner.nextInt() - 1;
        
        if (index >= 0 && index < availableObservers.size()) {
            stockMarket.deregisterObserver(availableObservers.get(index));
        } else {
            System.out.println("Invalid selection!");
        }
    }
    
    private static void autoSimulateMarket(StockMarket stockMarket) {
        System.out.println("\n🎲 Auto-simulating market activity (5 updates)...");
        for (int i = 1; i <= 5; i++) {
            System.out.println("\n--- Update " + i + "/5 ---");
            stockMarket.simulateMarketActivity();
            try {
                Thread.sleep(1500); // 1.5 second delay between updates
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n✅ Auto-simulation complete!");
    }
}