// EmailNotificationService.java - Additional observer
public class EmailNotificationService implements Observer {
    private String serviceName;
    
    public EmailNotificationService(String serviceName) {
        this.serviceName = serviceName;
    }
    
    @Override
    public void update(String stockSymbol, double price, double change) {
        if (Math.abs(change) > 10) { // Only send emails for significant changes
            String changeStr = (change >= 0 ? "+" : "") + String.format("%.2f", change);
            System.out.println("📧 [" + serviceName + "] EMAIL SENT: ALERT! " + 
                              stockSymbol + " has changed significantly to $" + 
                              String.format("%.2f", price) + " (" + changeStr + ")");
        }
    }
}