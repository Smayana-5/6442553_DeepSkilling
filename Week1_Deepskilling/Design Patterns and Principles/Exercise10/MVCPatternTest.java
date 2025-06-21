// ======= Exercise 10: MVC Pattern - Student Management System =======

// 1. Define Model Class
class Student {
    private String name;
    private String id;
    private String grade;
    
    public Student(String name, String id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getGrade() {
        return grade;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', id='" + id + "', grade='" + grade + "'}";
    }
}

// 2. Define View Class
class StudentView {
    public void displayStudentDetails(Student student) {
        System.out.println("=== Student Details ===");
        System.out.println("Name: " + student.getName());
        System.out.println("ID: " + student.getId());
        System.out.println("Grade: " + student.getGrade());
        System.out.println("========================\n");
    }
    
    public void displayStudentList(java.util.List<Student> students) {
        System.out.println("=== All Students ===");
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i).toString());
            }
        }
        System.out.println("====================\n");
    }
    
    public void displayMessage(String message) {
        System.out.println(">>> " + message + "\n");
    }
    
    public void displayMenu() {
        System.out.println("=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. View Student by ID");
        System.out.println("3. Update Student");
        System.out.println("4. View All Students");
        System.out.println("5. Delete Student");
        System.out.println("0. Exit");
        System.out.println("==================================");
        System.out.print("Enter your choice: ");
    }
}

// 3. Define Controller Class
class StudentController {
    private java.util.List<Student> students;
    private StudentView view;
    
    public StudentController(StudentView view) {
        this.view = view;
        this.students = new java.util.ArrayList<>();
    }
    
    // Add student
    public void addStudent(String name, String id, String grade) {
        // Check if student with same ID already exists
        if (findStudentById(id) != null) {
            view.displayMessage("Error: Student with ID " + id + " already exists!");
            return;
        }
        
        Student student = new Student(name, id, grade);
        students.add(student);
        view.displayMessage("Student added successfully!");
    }
    
    // Find student by ID
    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    
    // View student details
    public void viewStudent(String id) {
        Student student = findStudentById(id);
        if (student != null) {
            view.displayStudentDetails(student);
        } else {
            view.displayMessage("Student with ID " + id + " not found!");
        }
    }
    
    // Update student
    public void updateStudent(String id, String newName, String newGrade) {
        Student student = findStudentById(id);
        if (student != null) {
            if (newName != null && !newName.trim().isEmpty()) {
                student.setName(newName);
            }
            if (newGrade != null && !newGrade.trim().isEmpty()) {
                student.setGrade(newGrade);
            }
            view.displayMessage("Student updated successfully!");
            view.displayStudentDetails(student);
        } else {
            view.displayMessage("Student with ID " + id + " not found!");
        }
    }
    
    // Delete student
    public void deleteStudent(String id) {
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            view.displayMessage("Student with ID " + id + " deleted successfully!");
        } else {
            view.displayMessage("Student with ID " + id + " not found!");
        }
    }
    
    // View all students
    public void viewAllStudents() {
        view.displayStudentList(students);
    }
    
    // Get student count
    public int getStudentCount() {
        return students.size();
    }
}

// 4. Test Class (Main Application)
public class MVCPatternTest {
    public static void main(String[] args) {
        System.out.println("=== MVC Pattern - Student Management Demo ===\n");
        
        // Create view and controller
        StudentView view = new StudentView();
        StudentController controller = new StudentController(view);
        
        // Add some sample students for demonstration
        System.out.println("Adding sample students...");
        controller.addStudent("John Doe", "S001", "A");
        controller.addStudent("Jane Smith", "S002", "B+");
        controller.addStudent("Mike Johnson", "S003", "A-");
        
        // Display all students
        controller.viewAllStudents();
        
        // Test updating a student
        System.out.println("Updating student S002...");
        controller.updateStudent("S002", "Jane Wilson", "A");
        
        // Interactive mode
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        while (true) {
            view.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1: // Add Student
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter student grade: ");
                    String grade = scanner.nextLine();
                    controller.addStudent(name, id, grade);
                    break;
                    
                case 2: // View Student by ID
                    System.out.print("Enter student ID: ");
                    String searchId = scanner.nextLine();
                    controller.viewStudent(searchId);
                    break;
                    
                case 3: // Update Student
                    System.out.print("Enter student ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new name (or press Enter to skip): ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new grade (or press Enter to skip): ");
                    String newGrade = scanner.nextLine();
                    controller.updateStudent(updateId, newName, newGrade);
                    break;
                    
                case 4: // View All Students
                    controller.viewAllStudents();
                    break;
                    
                case 5: // Delete Student
                    System.out.print("Enter student ID to delete: ");
                    String deleteId = scanner.nextLine();
                    controller.deleteStudent(deleteId);
                    break;
                    
                case 0: // Exit
                    view.displayMessage("Thank you for using Student Management System!");
                    view.displayMessage("Total students managed: " + controller.getStudentCount());
                    scanner.close();
                    return;
                    
                default:
                    view.displayMessage("Invalid choice! Please try again.");
            }
        }
    }
}