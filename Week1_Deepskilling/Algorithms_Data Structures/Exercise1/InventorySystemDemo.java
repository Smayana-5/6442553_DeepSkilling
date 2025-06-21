import java.util.*;

// Product class representing individual inventory items
class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    
    // Constructor
    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    // Setters
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    // Method to update quantity (for stock adjustments)
    public void updateQuantity(int quantityChange) {
        this.quantity += quantityChange;
    }
    
    // Method to calculate total value of this product in inventory
    public double getTotalValue() {
        return quantity * price;
    }
    
    @Override
    public String toString() {
        return String.format("Product{ID='%s', Name='%s', Quantity=%d, Price=%.2f, Total Value=%.2f}",
                productId, productName, quantity, price, getTotalValue());
    }
}

// Inventory Management System class
class InventoryManagementSystem {
    private HashMap<String, Product> inventory;
    
    // Constructor
    public InventoryManagementSystem() {
        this.inventory = new HashMap<>();
    }
    
    // Add a new product to inventory
    public boolean addProduct(Product product) {
        if (inventory.containsKey(product.getProductId())) {
            System.out.println("Product with ID " + product.getProductId() + " already exists!");
            return false;
        }
        inventory.put(product.getProductId(), product);
        System.out.println("Product added successfully: " + product);
        return true;
    }
    
    // Add product with parameters (overloaded method)
    public boolean addProduct(String productId, String productName, int quantity, double price) {
        Product product = new Product(productId, productName, quantity, price);
        return addProduct(product);
    }
    
    // Update an existing product
    public boolean updateProduct(String productId, String newName, Integer newQuantity, Double newPrice) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        
        // Update only non-null values
        if (newName != null) {
            product.setProductName(newName);
        }
        if (newQuantity != null) {
            product.setQuantity(newQuantity);
        }
        if (newPrice != null) {
            product.setPrice(newPrice);
        }
        
        System.out.println("Product updated successfully: " + product);
        return true;
    }
    
    // Update product quantity (for stock adjustments)
    public boolean updateQuantity(String productId, int quantityChange) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        
        int newQuantity = product.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            System.out.println("Cannot update quantity. Insufficient stock!");
            return false;
        }
        
        product.updateQuantity(quantityChange);
        System.out.println("Quantity updated. New stock: " + product.getQuantity());
        return true;
    }
    
    // Delete a product from inventory
    public boolean deleteProduct(String productId) {
        Product removedProduct = inventory.remove(productId);
        if (removedProduct == null) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        System.out.println("Product deleted successfully: " + removedProduct);
        return true;
    }
    
    // Search for a product by ID
    public Product searchProduct(String productId) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found!");
        }
        return product;
    }
    
    // Search products by name (partial match)
    public List<Product> searchByName(String productName) {
        List<Product> results = new ArrayList<>();
        for (Product product : inventory.values()) {
            if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }
    
    // Display all products in inventory
    public void displayAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }
        
        System.out.println("\n=== INVENTORY REPORT ===");
        System.out.println("Total Products: " + inventory.size());
        //System.out.println("------------------------");
        
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
        
        //System.out.println("------------------------");
        System.out.printf("Total Inventory Value: %.2f%n", getTotalInventoryValue());
    }
    
    // Get products with low stock (below threshold)
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product product : inventory.values()) {
            if (product.getQuantity() <= threshold) {
                lowStockProducts.add(product);
            }
        }
        return lowStockProducts;
    }
    
    // Calculate total inventory value
    public double getTotalInventoryValue() {
        double totalValue = 0;
        for (Product product : inventory.values()) {
            totalValue += product.getTotalValue();
        }
        return totalValue;
    }
    
    // Get inventory size
    public int getInventorySize() {
        return inventory.size();
    }
    
    // Check if inventory is empty
    public boolean isEmpty() {
        return inventory.isEmpty();
    }
}

// Interactive main class for the inventory system
public class InventorySystemDemo
 {
    private static InventoryManagementSystem ims = new InventoryManagementSystem();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        //System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     WAREHOUSE INVENTORY SYSTEM      ║");
      //  System.out.println("║          Welcome Manager!           ║");
        //System.out.println("╚══════════════════════════════════════╝");
        
        // Add some sample data
        initializeSampleData();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addProductMenu();
                    break;
                case 2:
                    updateProductMenu();
                    break;
                case 3:
                    deleteProductMenu();
                    break;
                case 4:
                    searchProductMenu();
                    break;
                case 5:
                    displayAllProductsMenu();
                    break;
                case 6:
                    stockManagementMenu();
                    break;
                case 7:
                    reportsMenu();
                    break;
                case 8:
                    lowStockAlertMenu();
                    break;
                case 9:
                    System.out.println("\n Thank you for using Warehouse Inventory System!");
                    System.out.println("All data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println(" Invalid choice! Please select 1-9.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" INVENTORY MENU ");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. Search Product");
        System.out.println("5. View All Products");
        System.out.println("6. Stock Management");
        System.out.println("7. Reports & Analytics");
        System.out.println("8. Low Stock Alerts");
        System.out.println("9. Exit System");
        System.out.println("=".repeat(50));
    }
    
    private static void addProductMenu() {
        System.out.println("\n ADD NEW PRODUCT");
        System.out.println("-".repeat(20));
        
        String productId = getStringInput("Enter Product ID: ");
        String productName = getStringInput("Enter Product Name: ");
        int quantity = getIntInput("Enter Quantity: ");
        double price = getDoubleInput("Enter Price: $");
        
        if (ims.addProduct(productId, productName, quantity, price)) {
            System.out.println("Product added successfully!");
        }
    }
    
    private static void updateProductMenu() {
        System.out.println("\nUPDATE PRODUCT");
        System.out.println("-".repeat(20));
        
        String productId = getStringInput("Enter Product ID to update: ");
        Product existing = ims.searchProduct(productId);
        
        if (existing == null) {
            return;
        }
        
        System.out.println("Current details: " + existing);
        System.out.println("\n(Press Enter to keep current value)");
        
        String newName = getOptionalStringInput("New Product Name [" + existing.getProductName() + "]: ");
        Integer newQuantity = getOptionalIntInput("New Quantity [" + existing.getQuantity() + "]: ");
        Double newPrice = getOptionalDoubleInput("New Price [" + existing.getPrice() + "]: ");
        
        if (ims.updateProduct(productId, 
                            newName.isEmpty() ? null : newName,
                            newQuantity == -1 ? null : newQuantity,
                            newPrice == -1 ? null : newPrice)) {
            System.out.println(" Product updated successfully!");
        }
    }
    
    private static void deleteProductMenu() {
        System.out.println("\n DELETE PRODUCT");
        System.out.println("-".repeat(20));
        
        String productId = getStringInput("Enter Product ID to delete: ");
        Product product = ims.searchProduct(productId);
        
        if (product != null) {
            System.out.println("Product to delete: " + product);
            String confirm = getStringInput("Are you sure? (yes/no): ");
            
            if (confirm.toLowerCase().startsWith("y")) {
                if (ims.deleteProduct(productId)) {
                    System.out.println(" Product deleted successfully!");
                }
            } else {
                System.out.println(" Deletion cancelled.");
            }
        }
    }
    
    private static void searchProductMenu() {
        System.out.println("\n SEARCH PRODUCT");
        System.out.println("-".repeat(20));
        System.out.println("1. Search by Product ID");
        System.out.println("2. Search by Product Name");
        
        int choice = getIntInput("Choose search method: ");
        
        switch (choice) {
            case 1:
                String productId = getStringInput("Enter Product ID: ");
                Product product = ims.searchProduct(productId);
                if (product != null) {
                    System.out.println("Found: " + product);
                }
                break;
            case 2:
                String productName = getStringInput("Enter Product Name (partial match): ");
                List<Product> results = ims.searchByName(productName);
                if (results.isEmpty()) {
                    System.out.println(" No products found matching '" + productName + "'");
                } else {
                    System.out.println(" Found " + results.size() + " product(s):");
                    for (Product p : results) {
                        System.out.println("  " + p);
                    }
                }
                break;
            default:
                System.out.println(" Invalid choice!");
        }
    }
    
    private static void displayAllProductsMenu() {
        System.out.println("\n ALL PRODUCTS");
        ims.displayAllProducts();
    }
    
    private static void stockManagementMenu() {
        System.out.println("\n STOCK MANAGEMENT");
        System.out.println("-".repeat(25));
        System.out.println("1. Add Stock (Received Inventory)");
        System.out.println("2. Remove Stock (Sold/Damaged)");
        
        int choice = getIntInput("Choose operation: ");
        String productId = getStringInput("Enter Product ID: ");
        
        switch (choice) {
            case 1:
                int addQuantity = getIntInput("Enter quantity to add: ");
                if (ims.updateQuantity(productId, addQuantity)) {
                    System.out.println("Stock added successfully!");
                }
                break;
            case 2:
                int removeQuantity = getIntInput("Enter quantity to remove: ");
                if (ims.updateQuantity(productId, -removeQuantity)) {
                    System.out.println(" Stock removed successfully!");
                }
                break;
            default:
                System.out.println(" Invalid choice!");
        }
    }
    
    private static void reportsMenu() {
        System.out.println("\n REPORTS & ANALYTICS");
        System.out.println("-".repeat(25));
        System.out.println(" Total Products: " + ims.getInventorySize());
        System.out.printf(" Total Inventory Value: $%.2f%n", ims.getTotalInventoryValue());
        
        if (!ims.isEmpty()) {
            System.out.println("\n📋 Complete Inventory:");
            ims.displayAllProducts();
        }
    }
    
    private static void lowStockAlertMenu() {
        System.out.println("\n LOW STOCK ALERTS");
        System.out.println("-".repeat(25));
        
        int threshold = getIntInput("Enter low stock threshold: ");
        List<Product> lowStockProducts = ims.getLowStockProducts(threshold);
        
        if (lowStockProducts.isEmpty()) {
            System.out.println(" No products with low stock (≤" + threshold + ")");
        } else {
            System.out.println(" Products with low stock (≤" + threshold + "):");
            for (Product p : lowStockProducts) {
                System.out.println("   " + p);
            }
            System.out.println("\n Consider reordering these items!");
        }
    }
    
    private static void initializeSampleData() {
        System.out.println(" Initializing system with sample data...");
        ims.addProduct("LAP001", "Dell Laptop", 15, 999.99);
        ims.addProduct("MOU001", "Wireless Mouse", 45, 25.99);
        ims.addProduct("KEY001", "Mechanical Keyboard", 30, 75.50);
        ims.addProduct("MON001", "24-inch Monitor", 12, 299.99);
        ims.addProduct("HDD001", "External Hard Drive", 8, 89.99);
        System.out.println(" Sample data loaded!");
    }
    
    // Helper methods for input handling
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number!");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid decimal number!");
            }
        }
    }
    
    private static String getOptionalStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static Integer getOptionalIntInput(String prompt) {
        String input = getOptionalStringInput(prompt);
        if (input.isEmpty()) {
            return -1; // Flag for no change
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(" Invalid number, keeping current value.");
            return -1;
        }
    }
    
    private static Double getOptionalDoubleInput(String prompt) {
        String input = getOptionalStringInput(prompt);
        if (input.isEmpty()) {
            return -1.0; // Flag for no change
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println(" Invalid number, keeping current value.");
            return -1.0;
        }
    }
}