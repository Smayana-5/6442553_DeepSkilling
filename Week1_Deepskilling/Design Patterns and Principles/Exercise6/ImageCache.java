// ImageCache.java - Additional caching utility
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static Map<String, ProxyImage> cache = new HashMap<>();
    private static int totalRequests = 0;
    private static int cacheHits = 0;
    
    public static ProxyImage getImage(String filename) {
        totalRequests++;
        
        if (cache.containsKey(filename)) {
            cacheHits++;
            System.out.println("🎯 Cache HIT for '" + filename + "'");
            return cache.get(filename);
        } else {
            System.out.println("📥 Cache MISS for '" + filename + "' - creating new proxy");
            ProxyImage proxyImage = new ProxyImage(filename);
            cache.put(filename, proxyImage);
            return proxyImage;
        }
    }
    
    public static void printCacheStats() {
        System.out.println("\n📈 Global Cache Statistics:");
        System.out.println("   - Total requests: " + totalRequests);
        System.out.println("   - Cache hits: " + cacheHits);
        System.out.println("   - Cache misses: " + (totalRequests - cacheHits));
        System.out.println("   - Hit ratio: " + 
                          (totalRequests > 0 ? (cacheHits * 100.0 / totalRequests) + "%" : "N/A"));
        System.out.println("   - Images in cache: " + cache.size());
    }
    
    public static void clearCache() {
        cache.clear();
        totalRequests = 0;
        cacheHits = 0;
        System.out.println("🗑️  Cache cleared!");
    }
}

