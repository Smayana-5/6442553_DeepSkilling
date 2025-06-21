// ProxyImage.java - Proxy class
public class ProxyImage implements Image {
    private String filename;
    private RealImage realImage;
    private boolean isCached;
    private String cachedInfo;
    private long lastAccessed;
    private int accessCount;
    
    public ProxyImage(String filename) {
        this.filename = filename;
        this.isCached = false;
        this.accessCount = 0;
    }
    
    @Override
    public void display() {
        accessCount++;
        lastAccessed = System.currentTimeMillis();
        
        System.out.println("📋 Proxy: Request to display '" + filename + "' (Access #" + accessCount + ")");
        
        // Lazy initialization - create real image only when needed
        if (realImage == null) {
            System.out.println("🔄 Proxy: Creating real image instance...");
            realImage = new RealImage(filename);
            isCached = true;
        } else {
            System.out.println("⚡ Proxy: Using cached image instance");
        }
        
        realImage.display();
    }
    
    @Override
    public String getImageInfo() {
        if (realImage != null) {
            cachedInfo = realImage.getImageInfo();
            return cachedInfo;
        }
        return "Image not loaded yet - use display() to load";
    }
    
    public void printProxyStats() {
        System.out.println("📊 Proxy Statistics for '" + filename + "':");
        System.out.println("   - Access count: " + accessCount);
        System.out.println("   - Cached: " + (isCached ? "Yes" : "No"));
        System.out.println("   - Last accessed: " + 
                          (lastAccessed > 0 ? new java.util.Date(lastAccessed) : "Never"));
        System.out.println("   - Real image loaded: " + (realImage != null ? "Yes" : "No"));
    }
}

