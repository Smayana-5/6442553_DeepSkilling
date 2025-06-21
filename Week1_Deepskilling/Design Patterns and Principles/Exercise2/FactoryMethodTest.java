// FactoryMethodPatternExample - Complete implementation
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// Document.java - Abstract base class for all documents
abstract class Document {
    protected String name;
    protected String content;
    
    public Document(String name) {
        this.name = name;
        this.content = "";
    }
    
    public abstract void create();
    public abstract void open();
    public abstract void save();
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getName() {
        return name;
    }
    
    public String getContent() {
        return content;
    }
}

// WordDocument.java - Concrete document class for Word documents
class WordDocument extends Document {
    public WordDocument(String name) {
        super(name);
    }
    
    @Override
    public void create() {
        System.out.println("Creating Word document: " + name + ".docx");
        this.content = "This is a new Word document.";
    }
    
    @Override
    public void open() {
        System.out.println("Opening Word document: " + name + ".docx");
        System.out.println("Content: " + content);
    }
    
    @Override
    public void save() {
        System.out.println("Saving Word document: " + name + ".docx");
        System.out.println("Document saved successfully!");
    }
}

// PdfDocument.java - Concrete document class for PDF documents
class PdfDocument extends Document {
    public PdfDocument(String name) {
        super(name);
    }
    
    @Override
    public void create() {
        System.out.println("Creating PDF document: " + name + ".pdf");
        this.content = "This is a new PDF document.";
    }
    
    @Override
    public void open() {
        System.out.println("Opening PDF document: " + name + ".pdf");
        System.out.println("Content: " + content);
    }
    
    @Override
    public void save() {
        System.out.println("Saving PDF document: " + name + ".pdf");
        System.out.println("Document saved successfully!");
    }
}

// ExcelDocument.java - Concrete document class for Excel documents
class ExcelDocument extends Document {
    public ExcelDocument(String name) {
        super(name);
    }
    
    @Override
    public void create() {
        System.out.println("Creating Excel document: " + name + ".xlsx");
        this.content = "This is a new Excel spreadsheet with default worksheet.";
    }
    
    @Override
    public void open() {
        System.out.println("Opening Excel document: " + name + ".xlsx");
        System.out.println("Content: " + content);
    }
    
    @Override
    public void save() {
        System.out.println("Saving Excel document: " + name + ".xlsx");
        System.out.println("Document saved successfully!");
    }
}

// DocumentFactory.java - Abstract factory class
abstract class DocumentFactory {
    public abstract Document createDocument(String name);
    
    // Template method that uses the factory method
    public Document processDocument(String name, String content) {
        Document doc = createDocument(name);
        doc.create();
        if (content != null && !content.isEmpty()) {
            doc.setContent(content);
        }
        return doc;
    }
}

// WordDocumentFactory.java - Concrete factory for Word documents
class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String name) {
        return new WordDocument(name);
    }
}

// PdfDocumentFactory.java - Concrete factory for PDF documents
class PdfDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String name) {
        return new PdfDocument(name);
    }
}

// ExcelDocumentFactory.java - Concrete factory for Excel documents
class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String name) {
        return new ExcelDocument(name);
    }
}

// FactoryMethodTest.java - Test class with interactive demo
public class FactoryMethodTest {
    private static List<Document> documents = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Factory Method Pattern Demo - Document Management System ===");
        System.out.println("This demo shows how different document types are created using factories.\n");
        
        boolean continueOperation = true;
        
        while (continueOperation) {
            displayMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        createDocument();
                        break;
                    case 2:
                        listDocuments();
                        break;
                    case 3:
                        openDocument();
                        break;
                    case 4:
                        editDocument();
                        break;
                    case 5:
                        saveDocument();
                        break;
                    case 6:
                        continueOperation = false;
                        System.out.println("Exiting Document Management System...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter 1-6.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
            
            if (continueOperation) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
        System.out.println("Factory Method Pattern demonstration completed!");
    }
    
    private static void displayMenu() {
        System.out.println("\n=== Document Management Menu ===");
        System.out.println("1. Create new document");
        System.out.println("2. List all documents");
        System.out.println("3. Open document");
        System.out.println("4. Edit document content");
        System.out.println("5. Save document");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }
    
    private static void createDocument() {
        System.out.println("\n=== Create New Document ===");
        System.out.println("Select document type:");
        System.out.println("1. Word Document (.docx)");
        System.out.println("2. PDF Document (.pdf)");
        System.out.println("3. Excel Document (.xlsx)");
        System.out.print("Enter choice (1-3): ");
        
        try {
            int typeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            System.out.print("Enter document name (without extension): ");
            String name = scanner.nextLine();
            
            if (name.trim().isEmpty()) {
                System.out.println("Document name cannot be empty!");
                return;
            }
            
            DocumentFactory factory = null;
            String docType = "";
            
            switch (typeChoice) {
                case 1:
                    factory = new WordDocumentFactory();
                    docType = "Word";
                    break;
                case 2:
                    factory = new PdfDocumentFactory();
                    docType = "PDF";
                    break;
                case 3:
                    factory = new ExcelDocumentFactory();
                    docType = "Excel";
                    break;
                default:
                    System.out.println("Invalid document type!");
                    return;
            }
            
            System.out.print("Enter initial content (optional): ");
            String content = scanner.nextLine();
            
            System.out.println("\nCreating " + docType + " document using factory...");
            Document doc = factory.processDocument(name, content);
            documents.add(doc);
            
            System.out.println("Document created and added to the system!");
            
        } catch (Exception e) {
            System.out.println("Invalid input! Please try again.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    private static void listDocuments() {
        System.out.println("\n=== Document List ===");
        if (documents.isEmpty()) {
            System.out.println("No documents found in the system.");
            return;
        }
        
        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            String type = doc.getClass().getSimpleName().replace("Document", "");
            System.out.println((i + 1) + ". " + doc.getName() + " (" + type + ")");
        }
    }
    
    private static void openDocument() {
        if (documents.isEmpty()) {
            System.out.println("No documents available to open.");
            return;
        }
        
        listDocuments();
        System.out.print("Enter document number to open: ");
        
        try {
            int docIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
            
            if (docIndex >= 0 && docIndex < documents.size()) {
                System.out.println("\nOpening document...");
                documents.get(docIndex).open();
            } else {
                System.out.println("Invalid document number!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    private static void editDocument() {
        if (documents.isEmpty()) {
            System.out.println("No documents available to edit.");
            return;
        }
        
        listDocuments();
        System.out.print("Enter document number to edit: ");
        
        try {
            int docIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
            
            if (docIndex >= 0 && docIndex < documents.size()) {
                Document doc = documents.get(docIndex);
                System.out.println("Current content: " + doc.getContent());
                System.out.print("Enter new content: ");
                String newContent = scanner.nextLine();
                
                doc.setContent(newContent);
                System.out.println("Document content updated!");
            } else {
                System.out.println("Invalid document number!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    private static void saveDocument() {
        if (documents.isEmpty()) {
            System.out.println("No documents available to save.");
            return;
        }
        
        listDocuments();
        System.out.print("Enter document number to save: ");
        
        try {
            int docIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline
            
            if (docIndex >= 0 && docIndex < documents.size()) {
                System.out.println("\nSaving document...");
                documents.get(docIndex).save();
            } else {
                System.out.println("Invalid document number!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
}