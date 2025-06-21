import java.util.*;

// Order class to represent customer orders
class Order {
    private String orderId;
    private String customerName;
    private double totalPrice;
    
    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }
    
    // Getters
    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getTotalPrice() { return totalPrice; }
    
    @Override
    public String toString() {
        return String.format("Order #%s: %-15s - $%.2f", orderId, customerName, totalPrice);
    }
}

// Main class with sorting algorithms and user interface
public class OrderSortingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Order> orders = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println(" E-Commerce Order Sorting System");
        System.out.println("=====================================");
        
        // Initialize with sample data
        initializeSampleOrders();
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    displayOrders("Current Orders");
                    break;
                case 2:
                    addNewOrder();
                    break;
                case 3:
                    sortWithBubbleSort();
                    break;
                case 4:
                    sortWithQuickSort();
                    break;
                case 5:
                    System.out.println("\n Thank you for using the Order Sorting System!");
                    return;
                default:
                    System.out.println(" Invalid choice! Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private static void showMenu() {
        System.out.println("\n MENU OPTIONS:");
        System.out.println("1. View All Orders");
        System.out.println("2. Add New Order");
        System.out.println("3. Sort Orders (Bubble Sort)");
        System.out.println("4. Sort Orders (Quick Sort)");
        System.out.println("5. Exit");
        System.out.print("\nEnter your choice (1-5): ");
    }
    
    private static int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }
    
    private static void initializeSampleOrders() {
        orders.add(new Order("ORD001", "Alice Johnson", 250.75));
        orders.add(new Order("ORD002", "Bob Smith", 89.99));
        orders.add(new Order("ORD003", "Carol Davis", 1200.00));
        orders.add(new Order("ORD004", "David Wilson", 45.50));
        orders.add(new Order("ORD005", "Emma Brown", 675.25));
        orders.add(new Order("ORD006", "Frank Miller", 320.80));
        orders.add(new Order("ORD007", "Grace Lee", 15.99));
        
        System.out.println(" Sample orders loaded successfully!");
    }
    
    private static void displayOrders(String title) {
        System.out.println("\n" + title);
        System.out.println("=".repeat(50));
        
        if (orders.isEmpty()) {
            System.out.println(" No orders found.");
            return;
        }
        
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". " + orders.get(i));
        }
        
        System.out.printf("\n Total Orders: %d\n", orders.size());
    }
    
    private static void addNewOrder() {
        System.out.println("\n ADD NEW ORDER");
        System.out.println("==================");
        
        try {
            System.out.print("Enter Order ID: ");
            String orderId = scanner.nextLine().trim();
            
            System.out.print("Enter Customer Name: ");
            String customerName = scanner.nextLine().trim();
            
            System.out.print("Enter Total Price: $");
            double totalPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            if (orderId.isEmpty() || customerName.isEmpty() || totalPrice < 0) {
                System.out.println(" Invalid input! Please enter valid data.");
                return;
            }
            
            orders.add(new Order(orderId, customerName, totalPrice));
            System.out.println(" Order added successfully!");
            
        } catch (Exception e) {
            System.out.println(" Invalid input! Please try again.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    private static void sortWithBubbleSort() {
        if (orders.isEmpty()) {
            System.out.println("📭 No orders to sort.");
            return;
        }
        
        System.out.println("\n BUBBLE SORT");
        System.out.println("================");
        System.out.println("1. Low to High (Ascending)");
        System.out.println("2. High to Low (Descending)");
        System.out.print("Choose sorting order (1-2): ");
        
        int choice = getChoice();
        boolean ascending = (choice == 1);
        
        long startTime = System.currentTimeMillis();
        bubbleSort(orders, ascending);
        long endTime = System.currentTimeMillis();
        
        String sortType = ascending ? "Low to High" : "High to Low";
        displayOrders("Bubble Sort Results - " + sortType);
        System.out.printf("  Sorting completed in %d ms\n", (endTime - startTime));
    }
    
    private static void sortWithQuickSort() {
        if (orders.isEmpty()) {
            System.out.println(" No orders to sort.");
            return;
        }
        
        System.out.println("\n QUICK SORT");
        System.out.println("===============");
        System.out.println("1. Low to High (Ascending)");
        System.out.println("2. High to Low (Descending)");
        System.out.print("Choose sorting order (1-2): ");
        
        int choice = getChoice();
        boolean ascending = (choice == 1);
        
        long startTime = System.currentTimeMillis();
        quickSort(orders, 0, orders.size() - 1, ascending);
        long endTime = System.currentTimeMillis();
        
        String sortType = ascending ? "Low to High" : "High to Low";
        displayOrders(" Quick Sort Results - " + sortType);
        System.out.printf("  Sorting completed in %d ms\n", (endTime - startTime));
    }
    
    // Bubble Sort Algorithm
    private static void bubbleSort(List<Order> orders, boolean ascending) {
        int n = orders.size();
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                boolean shouldSwap;
                
                if (ascending) {
                    shouldSwap = orders.get(j).getTotalPrice() > orders.get(j + 1).getTotalPrice();
                } else {
                    shouldSwap = orders.get(j).getTotalPrice() < orders.get(j + 1).getTotalPrice();
                }
                
                if (shouldSwap) {
                    // Swap orders
                    Order temp = orders.get(j);
                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);
                    swapped = true;
                }
            }
            
            // If no swapping occurred, array is sorted
            if (!swapped) break;
        }
    }
    
    // Quick Sort Algorithm
    private static void quickSort(List<Order> orders, int low, int high, boolean ascending) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high, ascending);
            
            quickSort(orders, low, pivotIndex - 1, ascending);
            quickSort(orders, pivotIndex + 1, high, ascending);
        }
    }
    
    private static int partition(List<Order> orders, int low, int high, boolean ascending) {
        double pivot = orders.get(high).getTotalPrice();
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            boolean condition;
            
            if (ascending) {
                condition = orders.get(j).getTotalPrice() <= pivot;
            } else {
                condition = orders.get(j).getTotalPrice() >= pivot;
            }
            
            if (condition) {
                i++;
                // Swap orders
                Order temp = orders.get(i);
                orders.set(i, orders.get(j));
                orders.set(j, temp);
            }
        }
        
        // Place pivot in correct position
        Order temp = orders.get(i + 1);
        orders.set(i + 1, orders.get(high));
        orders.set(high, temp);
        
        return i + 1;
    }
}