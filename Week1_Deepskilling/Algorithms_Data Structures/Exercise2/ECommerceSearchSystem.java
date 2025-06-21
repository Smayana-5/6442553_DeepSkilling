import java.util.*;

// Product class with search attributes
class Product implements Comparable<Product> {
    private int productId;
    private String productName;
    private String category;
    private double price;
    
    public Product(int productId, String productName, String category, double price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }
    
    // Getters
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }
    
    @Override
    public String toString() {
        return String.format("ID: %-3d | %-20s | %-12s | $%.2f", 
                           productId, productName, category, price);
    }
}

// Main E-commerce Search System
class ECommerceSearchSystem {
    private ArrayList<Product> products;           // For linear search
    private ArrayList<Product> sortedProducts;     // For binary search
    private Scanner scanner;
    
    public ECommerceSearchSystem() {
        products = new ArrayList<>();
        sortedProducts = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeSampleData();
    }
    
    // Initialize with sample products
    private void initializeSampleData() {
        Product[] sampleProducts = {
            new Product(101, "iPhone 15 Pro", "Electronics", 999.99),
            new Product(205, "Nike Air Max", "Footwear", 129.99),
            new Product(150, "Samsung Galaxy S24", "Electronics", 899.99),
            new Product(301, "Adidas Sneakers", "Footwear", 89.99),
            new Product(175, "MacBook Air M2", "Electronics", 1199.99),
            new Product(420, "Levi's Jeans", "Clothing", 79.99),
            new Product(350, "Sony Headphones", "Electronics", 399.99),
            new Product(225, "Converse Shoes", "Footwear", 55.99),
            new Product(180, "iPad Pro", "Electronics", 799.99),
            new Product(275, "T-Shirt", "Clothing", 29.99),
            new Product(110, "Dell Laptop", "Electronics", 699.99),
            new Product(320, "Puma Jacket", "Clothing", 89.99)
        };
        
        for (Product product : sampleProducts) {
            addProduct(product);
        }
    }
    
    // Add product to both arrays
    public void addProduct(Product product) {
        products.add(product);
        // Insert in sorted position
        int insertIndex = Collections.binarySearch(sortedProducts, product);
        if (insertIndex < 0) {
            insertIndex = -insertIndex - 1;
        }
        sortedProducts.add(insertIndex, product);
    }
    
    // Linear Search by Product ID - O(n)
    public Product linearSearchById(int productId) {
        long startTime = System.nanoTime();
        
        for (Product product : products) {
            if (product.getProductId() == productId) {
                long endTime = System.nanoTime();
                System.out.printf("Linear search completed in: %.2f microseconds\n", 
                                (endTime - startTime) / 1000.0);
                return product;
            }
        }
        
        long endTime = System.nanoTime();
        System.out.printf("Linear search completed in: %.2f microseconds\n", 
                        (endTime - startTime) / 1000.0);
        return null;
    }
    
    // Binary Search by Product ID - O(log n)
    public Product binarySearchById(int productId) {
        long startTime = System.nanoTime();
        
        int left = 0;
        int right = sortedProducts.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Product midProduct = sortedProducts.get(mid);
            
            if (midProduct.getProductId() == productId) {
                long endTime = System.nanoTime();
                System.out.printf("Binary search completed in: %.2f microseconds\n", 
                                (endTime - startTime) / 1000.0);
                return midProduct;
            } else if (midProduct.getProductId() < productId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        long endTime = System.nanoTime();
        System.out.printf("Binary search completed in: %.2f microseconds\n", 
                        (endTime - startTime) / 1000.0);
        return null;
    }
    
    // Search by product name (partial matching)
    public ArrayList<Product> searchByName(String searchName) {
        ArrayList<Product> results = new ArrayList<>();
        String lowerSearchName = searchName.toLowerCase();
        
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(lowerSearchName)) {
                results.add(product);
            }
        }
        return results;
    }
    
    // Search by category
    public ArrayList<Product> searchByCategory(String category) {
        ArrayList<Product> results = new ArrayList<>();
        String lowerCategory = category.toLowerCase();
        
        for (Product product : products) {
            if (product.getCategory().toLowerCase().equals(lowerCategory)) {
                results.add(product);
            }
        }
        return results;
    }
    
    // Search by price range
    public ArrayList<Product> searchByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Product> results = new ArrayList<>();
        
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }
        return results;
    }
    
    // Display all products
    public void displayAllProducts() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                        ALL PRODUCTS");
        System.out.println("=".repeat(70));
        System.out.printf("%-6s %-20s %-12s %-10s\n", "ID", "Product Name", "Category", "Price");
        System.out.println("-".repeat(70));
        
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("=".repeat(70));
        System.out.println("Total Products: " + products.size());
    }
    
    // Display search results
    private void displayResults(ArrayList<Product> results, String searchType) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                    SEARCH RESULTS - " + searchType);
        System.out.println("=".repeat(70));
        
        if (results.isEmpty()) {
            System.out.println("No products found matching your search criteria.");
        } else {
            System.out.printf("%-6s %-20s %-12s %-10s\n", "ID", "Product Name", "Category", "Price");
            System.out.println("-".repeat(70));
            for (Product product : results) {
                System.out.println(product);
            }
            System.out.println("-".repeat(70));
            System.out.println("Found " + results.size() + " product(s)");
        }
        System.out.println("=".repeat(70));
    }
    
    // Main menu system
    public void runSystem() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("           WELCOME TO E-COMMERCE SEARCH SYSTEM");
        System.out.println("=".repeat(70));
        
        while (true) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    performLinearSearch();
                    break;
                case 3:
                    performBinarySearch();
                    break;
                case 4:
                    performNameSearch();
                    break;
                case 5:
                    performCategorySearch();
                    break;
                case 6:
                    performPriceRangeSearch();
                    break;
                case 7:
                    compareSearchPerformance();
                    break;
                case 8:
                    addNewProduct();
                    break;
                case 9:
                    System.out.println("\nThank you for using E-commerce Search System!");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println(" Invalid choice! Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1.View All Products");
        System.out.println("2. Linear Search by ID");
        System.out.println("3. Binary Search by ID");
        System.out.println("4.  Search by Product Name");
        System.out.println("5. Search by Category");
        System.out.println("6. Search by Price Range");
        System.out.println("7. Compare Search Performance");
        System.out.println("8. Add New Product");
        System.out.println("9. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-9): ");
    }
    
    private int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }
    
    private void performLinearSearch() {
        System.out.print("\n Enter Product ID to search: ");
        try {
            int productId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nPerforming Linear Search...");
            Product result = linearSearchById(productId);
            
            if (result != null) {
                System.out.println(" Product Found!");
                System.out.println("=".repeat(50));
                System.out.println(result);
                System.out.println("=".repeat(50));
            } else {
                System.out.println(" Product not found with ID: " + productId);
            }
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter a valid product ID.");
            scanner.nextLine();
        }
    }
    
    private void performBinarySearch() {
        System.out.print("\n Enter Product ID to search: ");
        try {
            int productId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nPerforming Binary Search...");
            Product result = binarySearchById(productId);
            
            if (result != null) {
                System.out.println(" Product Found!");
                System.out.println("=".repeat(50));
                System.out.println(result);
                System.out.println("=".repeat(50));
            } else {
                System.out.println(" Product not found with ID: " + productId);
            }
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter a valid product ID.");
            scanner.nextLine();
        }
    }
    
    private void performNameSearch() {
        System.out.print("\n  Enter product name to search: ");
        String searchName = scanner.nextLine().trim();
        
        if (searchName.isEmpty()) {
            System.out.println(" Please enter a valid product name.");
            return;
        }
        
        ArrayList<Product> results = searchByName(searchName);
        displayResults(results, "NAME SEARCH");
    }
    
    private void performCategorySearch() {
        System.out.println("\n Available Categories: Electronics, Footwear, Clothing");
        System.out.print("Enter category to search: ");
        String category = scanner.nextLine().trim();
        
        if (category.isEmpty()) {
            System.out.println(" Please enter a valid category.");
            return;
        }
        
        ArrayList<Product> results = searchByCategory(category);
        displayResults(results, "CATEGORY SEARCH");
    }
    
    private void performPriceRangeSearch() {
        try {
            System.out.print("\n Enter minimum price: $");
            double minPrice = scanner.nextDouble();
            System.out.print("Enter maximum price: $");
            double maxPrice = scanner.nextDouble();
            scanner.nextLine();
            
            if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
                System.out.println(" Invalid price range! Please enter valid prices.");
                return;
            }
            
            ArrayList<Product> results = searchByPriceRange(minPrice, maxPrice);
            displayResults(results, "PRICE RANGE SEARCH");
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter valid prices.");
            scanner.nextLine();
        }
    }
    
    private void compareSearchPerformance() {
        System.out.print("\n Enter Product ID to compare search performance: ");
        try {
            int productId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              PERFORMANCE COMPARISON");
            System.out.println("=".repeat(60));
            System.out.println("Dataset Size: " + products.size() + " products");
            System.out.println("Search Target: Product ID " + productId);
            System.out.println("-".repeat(60));
            
            System.out.println("\n LINEAR SEARCH PERFORMANCE:");
            Product result1 = linearSearchById(productId);
            
            System.out.println("\n BINARY SEARCH PERFORMANCE:");
            Product result2 = binarySearchById(productId);
            
            System.out.println("\n ANALYSIS:");
            System.out.println("• Linear Search: O(n) - checks each element sequentially");
            System.out.println("• Binary Search: O(log n) - divides search space in half");
            System.out.println("• Binary search is faster for large datasets!");
            System.out.println("=".repeat(60));
            
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter a valid product ID.");
            scanner.nextLine();
        }
    }
    
    private void addNewProduct() {
        try {
            System.out.println("\nADD NEW PRODUCT");
            System.out.println("=".repeat(40));
            
            System.out.print("Enter Product ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            // Check if ID already exists
            if (linearSearchById(id) != null) {
                System.out.println("Product with ID " + id + " already exists!");
                return;
            }
            
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter Category: ");
            String category = scanner.nextLine().trim();
            
            System.out.print("Enter Price: $");
            double price = scanner.nextDouble();
            scanner.nextLine();
            
            if (name.isEmpty() || category.isEmpty() || price < 0) {
                System.out.println(" Invalid input! Please enter valid product details.");
                return;
            }
            
            Product newProduct = new Product(id, name, category, price);
            addProduct(newProduct);
            
            System.out.println(" Product added successfully!");
            System.out.println("=".repeat(40));
            System.out.println(newProduct);
            System.out.println("=".repeat(40));
            
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter valid product details.");
            scanner.nextLine();
        }
    }
    
    // Main method
    public static void main(String[] args) {
        ECommerceSearchSystem system = new ECommerceSearchSystem();
        system.runSystem();
    }
}