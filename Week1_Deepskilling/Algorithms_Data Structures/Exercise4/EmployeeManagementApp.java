import java.util.Scanner;

// Employee class to represent individual employee records
class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;
    
    // Constructor
    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }
    
    // Getters
    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }
    
    // Display employee information
    public void displayEmployee() {
        System.out.printf("ID: %-6d Name: %-20s Position: %-20s Salary: $%.2f%n", 
                         employeeId, name, position, salary);
    }
}

// Employee Management System class
class EmployeeManagementSystem {
    private Employee[] employees;
    private int size;
    private int maxCapacity;
    
    // Constructor
    public EmployeeManagementSystem(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.employees = new Employee[maxCapacity];
        this.size = 0;
    }
    
    // Add new employee
    public boolean addEmployee(int employeeId, String name, String position, double salary) {
        if (size >= maxCapacity) {
            System.out.println(" Error: System is at full capacity!");
            return false;
        }
        
        if (searchEmployeeIndex(employeeId) != -1) {
            System.out.println(" Error: Employee with ID " + employeeId + " already exists!");
            return false;
        }
        
        employees[size] = new Employee(employeeId, name, position, salary);
        size++;
        System.out.println(" Employee added successfully!");
        return true;
    }
    
    // Search employee by ID (returns Employee object)
    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                return employees[i];
            }
        }
        return null;
    }
    
    // Search employee by ID (returns index)
    private int searchEmployeeIndex(int employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                return i;
            }
        }
        return -1;
    }
    
    // Delete employee
    public boolean deleteEmployee(int employeeId) {
        int index = searchEmployeeIndex(employeeId);
        
        if (index == -1) {
            System.out.println(" Error: Employee with ID " + employeeId + " not found!");
            return false;
        }
        
        String deletedName = employees[index].getName();
        
        // Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            employees[i] = employees[i + 1];
        }
        
        employees[size - 1] = null;
        size--;
        
        System.out.println(" Employee " + deletedName + " (ID: " + employeeId + ") deleted successfully!");
        return true;
    }
    
    // Display all employees
    public void traverseEmployees() {
        if (size == 0) {
            System.out.println(" No employees in the system.");
            return;
        }
        
        System.out.println("\n EMPLOYEE RECORDS (" + size + " employees)");
        System.out.println("=" + "=".repeat(75));
        
        for (int i = 0; i < size; i++) {
            System.out.printf("%2d. ", i + 1);
            employees[i].displayEmployee();
        }
        System.out.println("=" + "=".repeat(75));
    }
    
    // Update employee information
    public boolean updateEmployee(int employeeId) {
        Employee emp = searchEmployee(employeeId);
        
        if (emp == null) {
            System.out.println(" Error: Employee with ID " + employeeId + " not found!");
            return false;
        }
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n Current Employee Details:");
        emp.displayEmployee();
        
        System.out.print("\nEnter new name (or press Enter to keep current): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            emp.setName(newName);
        }
        
        System.out.print("Enter new position (or press Enter to keep current): ");
        String newPosition = scanner.nextLine().trim();
        if (!newPosition.isEmpty()) {
            emp.setPosition(newPosition);
        }
        
        System.out.print("Enter new salary (or 0 to keep current): ");
        try {
            double newSalary = Double.parseDouble(scanner.nextLine().trim());
            if (newSalary > 0) {
                emp.setSalary(newSalary);
            }
        } catch (NumberFormatException e) {
            // Keep current salary if invalid input
        }
        
        System.out.println(" Employee updated successfully!");
        return true;
    }
    
    // Get current employee count
    public int getEmployeeCount() {
        return size;
    }
    
    // Check if system is empty
    public boolean isEmpty() {
        return size == 0;
    }
}

// Main class with interactive menu
public class EmployeeManagementApp {
    private static EmployeeManagementSystem ems;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        // Initialize system
        System.out.print(" Enter maximum capacity for employee system: ");
        int capacity = getIntInput();
        ems = new EmployeeManagementSystem(capacity);
        
        System.out.println("\n Employee Management System Initialized!");
        System.out.println("Maximum Capacity: " + capacity + " employees\n");
        
        // Main menu loop
        while (true) {
            displayMenu();
            int choice = getIntInput();
            handleMenuChoice(choice);
        }
    }
    
    // Display main menu
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("1. Add Employee");
        System.out.println("2. Search Employee");
        System.out.println("3. Display All Employees");
        System.out.println("4.  Update Employee");
        System.out.println("5.  Delete Employee");
        System.out.println("6. System Status");
        System.out.println("7. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-7): ");
    }
    
    // Handle menu selection
    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addEmployeeMenu();
                break;
            case 2:
                searchEmployeeMenu();
                break;
            case 3:
                ems.traverseEmployees();
                break;
            case 4:
                updateEmployeeMenu();
                break;
            case 5:
                deleteEmployeeMenu();
                break;
            case 6:
                displaySystemStatus();
                break;
            case 7:
                exitSystem();
                break;
            default:
                System.out.println(" Invalid choice! Please enter a number between 1-7.");
        }
    }
    
    // Add employee menu
    private static void addEmployeeMenu() {
        System.out.println("\n ADD NEW EMPLOYEE");
        System.out.println("-".repeat(30));
        
        System.out.print("Enter Employee ID: ");
        int id = getIntInput();
        
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter Position: ");
        String position = scanner.nextLine().trim();
        
        System.out.print("Enter Salary: $");
        double salary = getDoubleInput();
        
        ems.addEmployee(id, name, position, salary);
    }
    
    // Search employee menu
    private static void searchEmployeeMenu() {
        System.out.println("\n SEARCH EMPLOYEE");
        System.out.println("-".repeat(25));
        
        System.out.print("Enter Employee ID to search: ");
        int id = getIntInput();
        
        Employee emp = ems.searchEmployee(id);
        if (emp != null) {
            System.out.println("\n Employee Found:");
            System.out.println("-".repeat(40));
            emp.displayEmployee();
        } else {
            System.out.println(" Employee with ID " + id + " not found!");
        }
    }
    
    // Update employee menu
    private static void updateEmployeeMenu() {
        System.out.println("\n UPDATE EMPLOYEE");
        System.out.println("-".repeat(25));
        
        System.out.print("Enter Employee ID to update: ");
        int id = getIntInput();
        
        ems.updateEmployee(id);
    }
    
    // Delete employee menu
    private static void deleteEmployeeMenu() {
        System.out.println("\n DELETE EMPLOYEE");
        System.out.println("-".repeat(25));
        
        System.out.print("Enter Employee ID to delete: ");
        int id = getIntInput();
        
        System.out.print("Are you sure you want to delete this employee? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            ems.deleteEmployee(id);
        } else {
            System.out.println(" Delete operation cancelled.");
        }
    }
    
    // Display system status
    private static void displaySystemStatus() {
        System.out.println("\nSYSTEM STATUS");
        System.out.println("-".repeat(25));
        System.out.println("Current Employees: " + ems.getEmployeeCount());
        System.out.println("System Status: " + (ems.isEmpty() ? "Empty" : "Active"));
    }
    
    // Exit system
    private static void exitSystem() {
        System.out.println("\n Thank you for using Employee Management System!");
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }
    
    // Helper method to get integer input with validation
    private static int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print(" Invalid input! Please enter a valid number: ");
            }
        }
    }
    
    // Helper method to get double input with validation
    private static double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) {
                    return value;
                } else {
                    System.out.print(" Salary cannot be negative! Please enter a valid amount: $");
                }
            } catch (NumberFormatException e) {
                System.out.print(" Invalid input! Please enter a valid number: $");
            }
        }
    }
}