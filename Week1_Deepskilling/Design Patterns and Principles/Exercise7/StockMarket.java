// StockMarket.java - Concrete subject
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockMarket implements Stock {
    private List<Observer> observers;
    private Map<String, Double> stockPrices;
    private Map<String, Double> previousPrices;
    private String lastUpdatedStock;
    
    public StockMarket() {
        observers = new ArrayList<>();
        stockPrices = new HashMap<>();
        previousPrices = new HashMap<>();
        
        // Initialize with some sample stocks
        initializeSampleStocks();
    }
    
    private void initializeSampleStocks() {
        stockPrices.put("AAPL", 150.00);
        stockPrices.put("GOOGL", 2800.00);
        stockPrices.put("MSFT", 330.00);
        stockPrices.put("AMZN", 3200.00);
        stockPrices.put("TSLA", 800.00);
        
        // Copy to previous prices
        previousPrices.putAll(stockPrices);
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("✅ Observer registered. Total observers: " + observers.size());
    }
    
    @Override
    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("❌ Observer deregistered. Total observers: " + observers.size());
    }
    
    @Override
    public void notifyObservers() {
        if (lastUpdatedStock != null) {
            double currentPrice = stockPrices.get(lastUpdatedStock);
            double previousPrice = previousPrices.get(lastUpdatedStock);
            double change = currentPrice - previousPrice;
            
            System.out.println("📢 Notifying " + observers.size() + " observers about " + lastUpdatedStock);
            
            for (Observer observer : observers) {
                observer.update(lastUpdatedStock, currentPrice, change);
            }
        }
    }
    
    public void updateStockPrice(String symbol, double newPrice) {
        if (stockPrices.containsKey(symbol)) {
            previousPrices.put(symbol, stockPrices.get(symbol));
            stockPrices.put(symbol, newPrice);
            lastUpdatedStock = symbol;
            
            System.out.println("📈 Stock price updated: " + symbol + " = $" + newPrice);
            notifyObservers();
        } else {
            System.out.println("❌ Stock symbol not found: " + symbol);
        }
    }
    
    public void addNewStock(String symbol, double price) {
        stockPrices.put(symbol, price);
        previousPrices.put(symbol, price);
        System.out.println("🆕 New stock added: " + symbol + " = $" + price);
    }
    
    public void simulateMarketActivity() {
        System.out.println("🎲 Simulating market activity...");
        
        String[] symbols = stockPrices.keySet().toArray(new String[0]);
        String randomSymbol = symbols[(int) (Math.random() * symbols.length)];
        
        double currentPrice = stockPrices.get(randomSymbol);
        double changePercent = (Math.random() - 0.5) * 0.1; // ±5% change
        double newPrice = currentPrice * (1 + changePercent);
        newPrice = Math.round(newPrice * 100.0) / 100.0; // Round to 2 decimal places
        
        updateStockPrice(randomSymbol, newPrice);
    }
    
    public void displayAllStocks() {
        System.out.println("\n📊 Current Stock Prices:");
        for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
            String symbol = entry.getKey();
            double current = entry.getValue();
            double previous = previousPrices.get(symbol);
            double change = current - previous;
            String changeStr = (change >= 0 ? "+" : "") + String.format("%.2f", change);
            String arrow = change > 0 ? "📈" : change < 0 ? "📉" : "➡️";
            
            System.out.println("   " + symbol + ": $" + String.format("%.2f", current) + 
                             " (" + changeStr + ") " + arrow);
        }
    }
}