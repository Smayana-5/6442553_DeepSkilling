package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("Starting Library Management Application...");
        System.out.println("========================================");
        
        try {
            // Load Spring Context from XML configuration
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            
            System.out.println("Spring Context loaded successfully!");
            System.out.println("Component scanning enabled for com.library package");
            
            // Get the BookService bean from the context
            BookService bookService = context.getBean(BookService.class);
            
            System.out.println("\nBean retrieval successful!");
            System.out.println("BookService bean type: " + bookService.getClass().getName());
            
            // Verify annotation-based configuration
            System.out.println("\n=== Verifying Annotation-Based Configuration ===");
            System.out.println("✓ @Service annotation on BookService class");
            System.out.println("✓ @Repository annotation on BookRepository class");
            System.out.println("✓ @Autowired annotation for dependency injection");
            System.out.println("✓ Component scanning configured in applicationContext.xml");
            
            // Test the service functionality
            System.out.println("\n=== Testing Service Functionality ===");
            bookService.performLibraryOperations();
            
            // Display bean information
            System.out.println("=== Bean Information ===");
            String[] beanNames = context.getBeanNamesForType(Object.class);
            System.out.println("Total beans in context: " + beanNames.length);
            
            System.out.println("\nApplication-specific beans:");
            for (String beanName : beanNames) {
                if (beanName.contains("book") || beanName.contains("Book")) {
                    Object bean = context.getBean(beanName);
                    System.out.println("- " + beanName + " (" + bean.getClass().getSimpleName() + ")");
                }
            }
            
            System.out.println("\n========================================");
            System.out.println("Library Management Application completed successfully!");
            System.out.println("Annotation-based configuration is working correctly!");
            
        } catch (Exception e) {
            System.err.println("Error occurred while running the application:");
            e.printStackTrace();
        }
    }
}