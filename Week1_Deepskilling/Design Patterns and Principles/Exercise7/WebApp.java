// WebApp.java - Another concrete observer
public class WebApp implements Observer {
    private String webSite;
    private String userEmail;
    
    public WebApp(String webSite, String userEmail) {
        this.webSite = webSite;
        this.userEmail = userEmail;
    }
    
    @Override
    public void update(String stockSymbol, double price, double change) {
        String changeStr = (change >= 0 ? "+" : "") + String.format("%.2f", change);
        String alert = change > 5 ? " ⚠️ SIGNIFICANT CHANGE!" : "";
        
        System.out.println("🌐 [" + webSite + " - " + userEmail + "] WEB ALERT: " + 
                          stockSymbol + " updated to $" + String.format("%.2f", price) + 
                          " (Change: " + changeStr + ")" + alert);
    }
    
    public String getWebInfo() {
        return webSite + " (" + userEmail + ")";
    }
}