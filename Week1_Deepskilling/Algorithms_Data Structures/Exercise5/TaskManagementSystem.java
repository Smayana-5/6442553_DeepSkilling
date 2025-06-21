import java.util.Scanner;

// Task class to represent individual tasks
class Task {
    private int taskId;
    private String taskName;
    private String status;
    private Task next;
    
    // Constructor
    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.next = null;
    }
    
    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Task getNext() {
        return next;
    }
    
    public void setNext(Task next) {
        this.next = next;
    }
    
    @Override
    public String toString() {
        return "Task ID: " + taskId + " | Name: " + taskName + " | Status: " + status;
    }
}

// TaskManager class implementing singly linked list
class TaskManager {
    private Task head;
    private int taskCounter;
    
    public TaskManager() {
        this.head = null;
        this.taskCounter = 1;
    }
    
    // Add a new task to the beginning of the list
    public void addTask(String taskName, String status) {
        Task newTask = new Task(taskCounter++, taskName, status);
        
        if (head == null) {
            head = newTask;
        } else {
            newTask.setNext(head);
            head = newTask;
        }
        
        System.out.println("✓ Task added successfully!");
        System.out.println(newTask);
    }
    
    // Search for a task by ID
    public Task searchTask(int taskId) {
        Task current = head;
        
        while (current != null) {
            if (current.getTaskId() == taskId) {
                return current;
            }
            current = current.getNext();
        }
        
        return null;
    }
    
    // Search for a task by name
    public Task searchTaskByName(String taskName) {
        Task current = head;
        
        while (current != null) {
            if (current.getTaskName().equalsIgnoreCase(taskName)) {
                return current;
            }
            current = current.getNext();
        }
        
        return null;
    }
    
    // Traverse and display all tasks
    public void traverseTasks() {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }
        
        System.out.println("\n========== ALL TASKS ==========");
        Task current = head;
        int count = 1;
        
        while (current != null) {
            System.out.println(count + ". " + current);
            current = current.getNext();
            count++;
        }
        System.out.println("==============================");
    }
    
    // Delete a task by ID
    public boolean deleteTask(int taskId) {
        if (head == null) {
            return false;
        }
        
        // If the task to delete is the first node
        if (head.getTaskId() == taskId) {
            head = head.getNext();
            return true;
        }
        
        Task current = head;
        
        while (current.getNext() != null) {
            if (current.getNext().getTaskId() == taskId) {
                current.setNext(current.getNext().getNext());
                return true;
            }
            current = current.getNext();
        }
        
        return false;
    }
    
    // Update task status
    public boolean updateTaskStatus(int taskId, String newStatus) {
        Task task = searchTask(taskId);
        
        if (task != null) {
            task.setStatus(newStatus);
            return true;
        }
        
        return false;
    }
    
    // Get total number of tasks
    public int getTotalTasks() {
        int count = 0;
        Task current = head;
        
        while (current != null) {
            count++;
            current = current.getNext();
        }
        
        return count;
    }
}

// Main class with interactive menu
public class TaskManagementSystem {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println(" Welcome to Task Management System!");
        System.out.println("=====================================");
        
        while (true) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addNewTask();
                    break;
                case 2:
                    searchTaskMenu();
                    break;
                case 3:
                    taskManager.traverseTasks();
                    break;
                case 4:
                    deleteTaskMenu();
                    break;
                case 5:
                    updateTaskStatusMenu();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    System.out.println("Thank you for using Task Management System! 👋");
                    System.exit(0);
                    break;
                default:
                    System.out.println(" Invalid choice! Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Add Task");
        System.out.println("2. Search Task");
        System.out.println("3. View All Tasks");
        System.out.println("4. Delete Task");
        System.out.println("5. Update Task Status");
        System.out.println("6. View Statistics");
        System.out.println("7. Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice (1-7): ");
    }
    
    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void addNewTask() {
        System.out.println("\n--- Add New Task ---");
        
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine().trim();
        
        if (taskName.isEmpty()) {
            System.out.println(" Task name cannot be empty!");
            return;
        }
        
        System.out.print("Enter task status (Pending/In Progress/Completed): ");
        String status = scanner.nextLine().trim();
        
        if (status.isEmpty()) {
            status = "Pending";
        }
        
        taskManager.addTask(taskName, status);
    }
    
    private static void searchTaskMenu() {
        System.out.println("\n--- Search Task ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.print("Choose search method (1-2): ");
        
        int searchChoice = getChoice();
        
        switch (searchChoice) {
            case 1:
                searchById();
                break;
            case 2:
                searchByName();
                break;
            default:
                System.out.println(" Invalid choice!");
        }
    }
    
    private static void searchById() {
        System.out.print("Enter task ID: ");
        int taskId = getChoice();
        
        Task task = taskManager.searchTask(taskId);
        
        if (task != null) {
            System.out.println("✓ Task found:");
            System.out.println(task);
        } else {
            System.out.println("Task with ID " + taskId + " not found!");
        }
    }
    
    private static void searchByName() {
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine().trim();
        
        Task task = taskManager.searchTaskByName(taskName);
        
        if (task != null) {
            System.out.println("✓ Task found:");
            System.out.println(task);
        } else {
            System.out.println(" Task with name '" + taskName + "' not found!");
        }
    }
    
    private static void deleteTaskMenu() {
        System.out.println("\n--- Delete Task ---");
        System.out.print("Enter task ID to delete: ");
        int taskId = getChoice();
        
        if (taskManager.deleteTask(taskId)) {
            System.out.println("✓ Task with ID " + taskId + " deleted successfully!");
        } else {
            System.out.println(" Task with ID " + taskId + " not found!");
        }
    }
    
    private static void updateTaskStatusMenu() {
        System.out.println("\n--- Update Task Status ---");
        System.out.print("Enter task ID: ");
        int taskId = getChoice();
        
        System.out.print("Enter new status (Pending/In Progress/Completed): ");
        String newStatus = scanner.nextLine().trim();
        
        if (taskManager.updateTaskStatus(taskId, newStatus)) {
            System.out.println("✓ Task status updated successfully!");
            Task updatedTask = taskManager.searchTask(taskId);
            System.out.println(updatedTask);
        } else {
            System.out.println(" Task with ID " + taskId + " not found!");
        }
    }
    
    private static void displayStatistics() {
        System.out.println("\n--- Task Statistics ---");
        System.out.println("Total Tasks: " + taskManager.getTotalTasks());
        
        if (taskManager.getTotalTasks() == 0) {
            System.out.println("No tasks to display statistics.");
        }
    }
}
