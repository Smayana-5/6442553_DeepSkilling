// ProxyPatternTest.java
import java.util.Scanner;

public class ProxyPatternTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Image Viewer with Proxy Pattern ===\n");
        
        // Sample images
        String[] sampleImages = {
            "vacation_photo.jpg",
            "sunset_landscape.png", 
            "family_portrait.jpg",
            "nature_wallpaper.png",
            "city_skyline.jpg"
        };
        
        System.out.println("Available sample images:");
        for (int i = 0; i < sampleImages.length; i++) {
            System.out.println((i + 1) + ". " + sampleImages[i]);
        }
        
        while (true) {
            System.out.println("\n=== Image Viewer Menu ===");
            System.out.println("1. View sample image");
            System.out.println("2. View custom image");
            System.out.println("3. View image multiple times (caching demo)");
            System.out.println("4. Show cache statistics");
            System.out.println("5. Clear cache");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    viewSampleImage(scanner, sampleImages);
                    break;
                case 2:
                    viewCustomImage(scanner);
                    break;
                case 3:
                    cachingDemo(scanner, sampleImages);
                    break;
                case 4:
                    ImageCache.printCacheStats();
                    break;
                case 5:
                    ImageCache.clearCache();
                    break;
                case 6:
                    System.out.println("Thank you for using the Image Viewer!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void viewSampleImage(Scanner scanner, String[] sampleImages) {
        System.out.println("\nSelect an image to view:");
        for (int i = 0; i < sampleImages.length; i++) {
            System.out.println((i + 1) + ". " + sampleImages[i]);
        }
        System.out.print("Enter image number: ");
        
        int imageNum = scanner.nextInt();
        if (imageNum >= 1 && imageNum <= sampleImages.length) {
            String filename = sampleImages[imageNum - 1];
            ProxyImage image = ImageCache.getImage(filename);
            System.out.println();
            image.display();
            System.out.println();
            image.printProxyStats();
        } else {
            System.out.println("Invalid image number!");
        }
    }
    
    private static void viewCustomImage(Scanner scanner) {
        System.out.print("Enter image filename: ");
        String filename = scanner.nextLine();
        
        ProxyImage image = ImageCache.getImage(filename);
        System.out.println();
        image.display();
        System.out.println();
        image.printProxyStats();
    }
    
    private static void cachingDemo(Scanner scanner, String[] sampleImages) {
        System.out.println("\n=== Caching Demo ===");
        System.out.println("This demo shows how the proxy pattern improves performance through caching.");
        System.out.println("First access will be slow (loading from server), subsequent accesses will be fast.\n");
        
        String demoImage = sampleImages[0]; // Use first sample image
        ProxyImage image = ImageCache.getImage(demoImage);
        
        for (int i = 1; i <= 3; i++) {
            System.out.println("--- Access #" + i + " ---");
            long startTime = System.currentTimeMillis();
            image.display();
            long endTime = System.currentTimeMillis();
            System.out.println("⏱️  Time taken: " + (endTime - startTime) + "ms");
            System.out.println();
            
            if (i < 3) {
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
        }
        
        image.printProxyStats();
    }
}