// MobileApp.java - Concrete observer
public class MobileApp implements Observer {
    private String appName;
    private String userId;
    
    public MobileApp(String appName, String userId) {
        this.appName = appName;
        this.userId = userId;
    }
    
    @Override
    public void update(String stockSymbol, double price, double change) {
        String changeStr = (change >= 0 ? "+" : "") + String.format("%.2f", change);
        String trend = change > 0 ? "📈" : change < 0 ? "📉" : "➡️";
        
        System.out.println("📱 [" + appName + " - " + userId + "] PUSH NOTIFICATION: " + 
                          stockSymbol + " is now $" + String.format("%.2f", price) + 
                          " (" + changeStr + ") " + trend);
    }
    
    public String getAppInfo() {
        return appName + " (" + userId + ")";
    }
}