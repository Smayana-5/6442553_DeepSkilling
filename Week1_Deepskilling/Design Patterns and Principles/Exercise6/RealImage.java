public class RealImage implements Image {
    private String filename;
    private String imageData;
    private boolean isLoaded;
    
    public RealImage(String filename) {
        this.filename = filename;
        this.isLoaded = false;
        loadImageFromServer();
    }
    
    private void loadImageFromServer() {
        System.out.println("🌐 Loading image '" + filename + "' from remote server...");
        
        // Simulate network delay
        try {
            Thread.sleep(2000 + (int)(Math.random() * 1000)); // 2-3 seconds delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulate loading image data
        this.imageData = "Image data for " + filename + " (Size: " + 
                        (int)(Math.random() * 5000 + 1000) + " KB)";
        this.isLoaded = true;
        
        System.out.println("✅ Image '" + filename + "' loaded successfully!");
    }
    
    @Override
    public void display() {
        if (isLoaded) {
            System.out.println("🖼️  Displaying image: " + filename);
            System.out.println("   Content: " + imageData);
        } else {
            System.out.println("❌ Image not loaded yet!");
        }
    }
    
    @Override
    public String getImageInfo() {
        return isLoaded ? imageData : "Image not loaded";
    }
}