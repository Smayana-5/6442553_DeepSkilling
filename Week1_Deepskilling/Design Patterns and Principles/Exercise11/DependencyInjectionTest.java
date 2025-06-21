// ======= Exercise 11: Dependency Injection - Customer Management System =======

// 1. Define Repository Interface
interface CustomerRepository {
    Customer findCustomerById(int id);
    void saveCustomer(Customer customer);
    java.util.List<Customer> getAllCustomers();
    boolean deleteCustomer(int id);
    String getRepositoryType();
}

// Customer Model Class
class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    
    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return "Customer{id=" + id + ", name='" + name + "', email='" + email + "', phone='" + phone + "'}";
    }
}

// 2. Implement Concrete Repository Classes
class CustomerRepositoryImpl implements CustomerRepository {
    private java.util.Map<Integer, Customer> customers;
    private int nextId;
    
    public CustomerRepositoryImpl() {
        this.customers = new java.util.HashMap<>();
        this.nextId = 1;
        
        // Add sample data
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        saveCustomer(new Customer(0, "John Doe", "john.doe@email.com", "+1-555-0101"));
        saveCustomer(new Customer(0, "Jane Smith", "jane.smith@email.com", "+1-555-0102"));
        saveCustomer(new Customer(0, "Mike Johnson", "mike.johnson@email.com", "+1-555-0103"));
        saveCustomer(new Customer(0, "Sarah Wilson", "sarah.wilson@email.com", "+1-555-0104"));
        saveCustomer(new Customer(0, "David Brown", "david.brown@email.com", "+1-555-0105"));
    }
    
    @Override
    public Customer findCustomerById(int id) {
        System.out.println("Searching for customer in Database Repository...");
        return customers.get(id);
    }
    
    @Override
    public void saveCustomer(Customer customer) {
        if (customer.getId() == 0) {
            customer.setId(nextId++);
        }
        customers.put(customer.getId(), customer);
        System.out.println("Customer saved to Database Repository");
    }
    
    @Override
    public java.util.List<Customer> getAllCustomers() {
        System.out.println("Fetching all customers from Database Repository...");
        return new java.util.ArrayList<>(customers.values());
    }
    
    @Override
    public boolean deleteCustomer(int id) {
        Customer removed = customers.remove(id);
        if (removed != null) {
            System.out.println("Customer deleted from Database Repository");
            return true;
        }
        return false;
    }
    
    @Override
    public String getRepositoryType() {
        return "Database Repository";
    }
}

// Alternative implementation - File-based repository
class FileCustomerRepository implements CustomerRepository {
    private java.util.Map<Integer, Customer> customers;
    private int nextId;
    
    public FileCustomerRepository() {
        this.customers = new java.util.HashMap<>();
        this.nextId = 1;
        
        // Simulate loading from file
        loadCustomersFromFile();
    }
    
    private void loadCustomersFromFile() {
        // Simulate file loading with different sample data
        saveCustomer(new Customer(0, "Alice Cooper", "alice.cooper@file.com", "+1-444-0201"));
        saveCustomer(new Customer(0, "Bob Martinez", "bob.martinez@file.com", "+1-444-0202"));
        saveCustomer(new Customer(0, "Carol Davis", "carol.davis@file.com", "+1-444-0203"));
    }
    
    @Override
    public Customer findCustomerById(int id) {
        System.out.println("Searching for customer in File Repository...");
        return customers.get(id);
    }
    
    @Override
    public void saveCustomer(Customer customer) {
        if (customer.getId() == 0) {
            customer.setId(nextId++);
        }
        customers.put(customer.getId(), customer);
        System.out.println("Customer saved to File Repository");
    }
    
    @Override
    public java.util.List<Customer> getAllCustomers() {
        System.out.println("Fetching all customers from File Repository...");
        return new java.util.ArrayList<>(customers.values());
    }
    
    @Override
    public boolean deleteCustomer(int id) {
        Customer removed = customers.remove(id);
        if (removed != null) {
            System.out.println("Customer deleted from File Repository");
            return true;
        }
        return false;
    }
    
    @Override
    public String getRepositoryType() {
        return "File Repository";
    }
}

// 3. Define Service Class with Dependency Injection
class CustomerService {
    private CustomerRepository customerRepository;
    
    // Constructor Injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        System.out.println("CustomerService initialized with: " + customerRepository.getRepositoryType());
    }
    
    // Setter Injection (alternative way to inject dependency)
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        System.out.println("CustomerRepository switched to: " + customerRepository.getRepositoryType());
    }
    
    public Customer findCustomerById(int id) {
        System.out.println("CustomerService: Finding customer with ID " + id);
        Customer customer = customerRepository.findCustomerById(id);
        
        if (customer != null) {
            System.out.println("Customer found: " + customer);
        } else {
            System.out.println("Customer with ID " + id + " not found!");
        }
        
        return customer;
    }
    
    public void addCustomer(String name, String email, String phone) {
        Customer customer = new Customer(0, name, email, phone);
        customerRepository.saveCustomer(customer);
        System.out.println("CustomerService: New customer added with ID " + customer.getId());
    }
    
    public void displayAllCustomers() {
        System.out.println("CustomerService: Retrieving all customers");
        java.util.List<Customer> customers = customerRepository.getAllCustomers();
        
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Total customers: " + customers.size());
            for (Customer customer : customers) {
                System.out.println("  " + customer);
            }
        }
    }
    
    public void deleteCustomer(int id) {
        System.out.println("CustomerService: Deleting customer with ID " + id);
        boolean deleted = customerRepository.deleteCustomer(id);
        
        if (deleted) {
            System.out.println("Customer successfully deleted!");
        } else {
            System.out.println("Customer with ID " + id + " not found!");
        }
    }
    
    public String getRepositoryInfo() {
        return customerRepository.getRepositoryType();
    }
}

// 4. Test Class demonstrating Dependency Injection
public class DependencyInjectionTest {
    public static void main(String[] args) {
        System.out.println("=== Dependency Injection - Customer Management Demo ===\n");
        
        // Demonstrate Constructor Injection with different implementations
        System.out.println("1. Testing with Database Repository:");
        CustomerRepository dbRepository = new CustomerRepositoryImpl();
        CustomerService customerService = new CustomerService(dbRepository);
        
        // Test finding customers
        customerService.findCustomerById(1);
        customerService.findCustomerById(2);
        customerService.displayAllCustomers();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Demonstrate Setter Injection - switching repository at runtime
        System.out.println("2. Switching to File Repository (Setter Injection):");
        CustomerRepository fileRepository = new FileCustomerRepository();
        customerService.setCustomerRepository(fileRepository);
        
        customerService.findCustomerById(1);
        customerService.displayAllCustomers();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}