import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s", id, title, author);
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private List<Book> sortedBooks = new ArrayList<>();
    
    public void addBook(int id, String title, String author) {
        books.add(new Book(id, title, author));
        updateSortedBooks();
    }
    
    private void updateSortedBooks() {
        sortedBooks = new ArrayList<>(books);
        sortedBooks.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
    }
    
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println(" No books in the library yet!");
            return;
        }
        
        System.out.println("\n All Books in Library:");
        System.out.println("=" .repeat(50));
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
    // Linear Search - O(n)
    public List<Book> linearSearch(String title) {
        List<Book> results = new ArrayList<>();
        int comparisons = 0;
        
        for (Book book : books) {
            comparisons++;
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        
        System.out.println(" Linear Search: " + comparisons + " comparisons made");
        return results;
    }
    
    // Binary Search - O(log n)
    public List<Book> binarySearch(String title) {
        if (sortedBooks.isEmpty()) return new ArrayList<>();
        
        List<Book> results = new ArrayList<>();
        int comparisons = 0;
        int left = 0, right = sortedBooks.size() - 1;
        
        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            String midTitle = sortedBooks.get(mid).getTitle().toLowerCase();
            String searchTitle = title.toLowerCase();
            
            if (midTitle.contains(searchTitle)) {
                results.add(sortedBooks.get(mid));
                
                // Find other matches around this position
                int leftIndex = mid - 1;
                while (leftIndex >= 0 && sortedBooks.get(leftIndex).getTitle().toLowerCase().contains(searchTitle)) {
                    results.add(0, sortedBooks.get(leftIndex));
                    leftIndex--;
                }
                
                int rightIndex = mid + 1;
                while (rightIndex < sortedBooks.size() && sortedBooks.get(rightIndex).getTitle().toLowerCase().contains(searchTitle)) {
                    results.add(sortedBooks.get(rightIndex));
                    rightIndex++;
                }
                break;
            } else if (midTitle.compareTo(searchTitle) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        System.out.println(" Binary Search: " + comparisons + " comparisons made");
        return results;
    }
    
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }
    
    public int getTotalBooks() {
        return books.size();
    }
}

public class LibraryManagementSystem {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        initializeLibrary();
        showWelcome();
        
        while (true) {
            showMenu();
            int choice = getChoice();
            handleChoice(choice);
        }
    }
    
    private static void initializeLibrary() {
        // Pre-populate with some books
        library.addBook(1, "The Great Gatsby", "F. Scott Fitzgerald");
        library.addBook(2, "To Kill a Mockingbird", "Harper Lee");
        library.addBook(3, "1984", "George Orwell");
        library.addBook(4, "Pride and Prejudice", "Jane Austen");
        library.addBook(5, "Animal Farm", "George Orwell");
        library.addBook(6, "The Hobbit", "J.R.R. Tolkien");
        library.addBook(7, "Harry Potter", "J.K. Rowling");
        library.addBook(8, "Lord of the Rings", "J.R.R. Tolkien");
    }
    
    private static void showWelcome() {
        System.out.println("\n  WELCOME TO DIGITAL LIBRARY ");
        System.out.println("================================");
        System.out.println("Your books are waiting for you!");
    }
    
    private static void showMenu() {
        System.out.println("\n What would you like to do?");
        System.out.println("1  Add a new book");
        System.out.println("2  View all books");
        System.out.println("3  Search by title (Linear Search)");
        System.out.println("4  Search by title (Binary Search)");
        System.out.println("5  Search by author");
        System.out.println("6 Compare search performance");
        System.out.println("7  Exit");
        System.out.print("\n Enter your choice (1-7): ");
    }
    
    private static int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }
    
    private static void handleChoice(int choice) {
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1 -> addBook();
            case 2 -> viewAllBooks();
            case 3 -> searchLinear();
            case 4 -> searchBinary();
            case 5 -> searchByAuthor();
            case 6 -> comparePerformance();
            case 7 -> exitProgram();
            default -> System.out.println(" Invalid choice! Please enter 1-7.");
        }
    }
    
    private static void addBook() {
        System.out.println("\n Adding a New Book");
        System.out.println("-------------------");
        
        System.out.print("Enter book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        
        library.addBook(id, title, author);
        System.out.println(" Book added successfully!");
    }
    
    private static void viewAllBooks() {
        System.out.println("\n Library Collection");
        System.out.println("--------------------");
        library.displayAllBooks();
        System.out.println("\nTotal books: " + library.getTotalBooks());
    }
    
    private static void searchLinear() {
        System.out.print("\n Enter title to search (Linear): ");
        String title = scanner.nextLine();
        
        long startTime = System.nanoTime();
        List<Book> results = library.linearSearch(title);
        long endTime = System.nanoTime();
        
        displaySearchResults(results, "Linear Search");
        System.out.printf(" Time taken: %.2f microseconds\n", (endTime - startTime) / 1000.0);
    }
    
    private static void searchBinary() {
        System.out.print("\n Enter title to search (Binary): ");
        String title = scanner.nextLine();
        
        long startTime = System.nanoTime();
        List<Book> results = library.binarySearch(title);
        long endTime = System.nanoTime();
        
        displaySearchResults(results, "Binary Search");
        System.out.printf("Time taken: %.2f microseconds\n", (endTime - startTime) / 1000.0);
    }
    
    private static void searchByAuthor() {
        System.out.print("\n Enter author name to search: ");
        String author = scanner.nextLine();
        
        List<Book> results = library.searchByAuthor(author);
        displaySearchResults(results, "Author Search");
    }
    
    private static void comparePerformance() {
        System.out.print("\n Enter title for performance comparison: ");
        String title = scanner.nextLine();
        
        System.out.println("\n PERFORMANCE COMPARISON");
        System.out.println("========================");
        
        // Linear Search
        long startTime = System.nanoTime();
        List<Book> linearResults = library.linearSearch(title);
        long linearTime = System.nanoTime() - startTime;
        
        // Binary Search
        startTime = System.nanoTime();
        List<Book> binaryResults = library.binarySearch(title);
        long binaryTime = System.nanoTime() - startTime;
        
        System.out.println("\n Results:");
        System.out.printf(" Linear Search: %.2f microseconds\n", linearTime / 1000.0);
        System.out.printf("Binary Search: %.2f microseconds\n", binaryTime / 1000.0);
        
        if (binaryTime < linearTime) {
            System.out.printf(" Binary search was %.1fx faster!\n", (double) linearTime / binaryTime);
        }
        
        System.out.println("\n Algorithm Analysis:");
        System.out.println("• Linear Search: O(n) - checks every book");
        System.out.println("• Binary Search: O(log n) - divides search space");
        System.out.println("• For " + library.getTotalBooks() + " books, binary search is more efficient!");
    }
    
    private static void displaySearchResults(List<Book> results, String searchType) {
        System.out.println("\n " + searchType + " Results:");
        System.out.println("=" .repeat(30));
        
        if (results.isEmpty()) {
            System.out.println(" No books found!");
        } else {
            System.out.println(" Found " + results.size() + " book(s):");
            for (Book book : results) {
                System.out.println("  " + book);
            }
        }
    }
    
    private static void exitProgram() {
        System.out.println("\n Thank you for using Digital Library!");
        System.out.println(" Happy reading! ");
        scanner.close();
        System.exit(0);
    }
}