package com.example;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StudentTest {
    
    @Test
    public void testStudentAssertions() {
        // Create test student
        Student student = new Student("Alice Johnson", 20);
        
        // assertEquals - Test string equality
        assertEquals("Student name should be Alice Johnson", "Alice Johnson", student.getName());
        
        // assertEquals - Test integer equality
        assertEquals("Student age should be 20", 20, student.getAge());
        
        // assertTrue - Test boolean conditions
        assertTrue("New student should be active", student.isActive());
        assertTrue("Student name should contain 'Alice'", student.getName().contains("Alice"));
        
        // assertFalse - Test false conditions
        assertFalse("Student should not be deactivated initially", !student.isActive());
        
        // Add some grades for testing
        student.addGrade(85.5);
        student.addGrade(92.0);
        student.addGrade(78.5);
        
        // assertEquals with delta for floating point numbers
        assertEquals("Average should be calculated correctly", 
                    85.33, student.getAverageGrade(), 0.01);
        
        // assertTrue - Test passing student
        assertTrue("Student with good grades should be passing", student.isPassingStudent());
        
        // assertNotNull - Test object is not null
        assertNotNull("Student object should not be null", student);
        assertNotNull("Student name should not be null", student.getName());
        assertNotNull("Grades list should not be null", student.getGrades());
        
        // assertNull - Test null values
        assertNull("Best friend should be null initially", student.getBestFriend());
        
        // Test list/collection assertions
        List<Double> grades = student.getGrades();
        assertEquals("Should have 3 grades", 3, grades.size());
        assertTrue("Grades should contain 85.5", grades.contains(85.5));
        assertFalse("Grades should not contain 100.0", grades.contains(100.0));
        
        // assertArrayEquals - Test array equality
        String[] expectedSubjects = {"Math", "Science", "English", "History"};
        String[] actualSubjects = student.getSubjects();
        assertArrayEquals("Subjects array should match expected", expectedSubjects, actualSubjects);
    }
    
    @Test
    public void testStudentObjectComparisons() {
        Student student1 = new Student("Bob Smith", 19);
        Student student2 = new Student("Bob Smith", 19);
        Student student3 = student1;
        
        // assertSame - Test same object reference
        assertSame("student3 should reference the same object as student1", student1, student3);
        
        // assertNotSame - Test different object references
        assertNotSame("student1 and student2 should be different objects", student1, student2);
        
        // Even though they have same data, they are different objects
        assertEquals("Names should be equal", student1.getName(), student2.getName());
        assertEquals("Ages should be equal", student1.getAge(), student2.getAge());
    }
    
    @Test
    public void testStudentStateChanges() {
        Student student = new Student("Charlie Brown", 18);
        
        // Test initial state
        assertTrue("Student should be active initially", student.isActive());
        
        // Change state and test
        student.deactivate();
        assertFalse("Student should be inactive after deactivation", student.isActive());
        
        // Test grade average with no grades
        assertEquals("Average should be 0 with no grades", 0.0, student.getAverageGrade(), 0.001);
        assertFalse("Student with no grades should not be passing", student.isPassingStudent());
        
        // Add failing grades
        student.addGrade(45.0);
        student.addGrade(55.0);
        assertFalse("Student with failing grades should not be passing", student.isPassingStudent());
        
        // Add passing grades
        student.addGrade(95.0);
        student.addGrade(85.0);
        assertTrue("Student with good overall average should be passing", student.isPassingStudent());
    }
    
    @Test
    public void testStringAssertions() {
        Student student = new Student("Diana Prince", 22);
        student.addGrade(88.5);
        
        String info = student.getStudentInfo();
        
        // String content assertions
        assertNotNull("Student info should not be null", info);
        assertTrue("Info should contain student name", info.contains("Diana Prince"));
        assertTrue("Info should contain age", info.contains("22"));
        assertTrue("Info should start with 'Student:'", info.startsWith("Student:"));
        assertFalse("Info should not contain negative values", info.contains("-"));
        
        // Test string equality
        String expectedStart = "Student: Diana Prince";
        assertTrue("Info should start correctly", info.startsWith(expectedStart));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAge() {
        // Test that exception is thrown for negative age
        Student student = new Student("Test Student", 20);
        student.setAge(-5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGrade() {
        // Test that exception is thrown for invalid grade
        Student student = new Student("Test Student", 20);
        student.addGrade(150.0); // Should throw exception
    }
    
    @Test
    public void testCustomAssertionMessages() {
        Student student = new Student("Eve Adams", 21);
        
        // All assertions with custom failure messages
        assertEquals("Student name assertion failed", "Eve Adams", student.getName());
        assertTrue("Student active status assertion failed", student.isActive());
        assertFalse("Student should not be inactive initially", !student.isActive());
        assertNotNull("Student grades list should be initialized", student.getGrades());
        assertNull("Best friend should be null by default", student.getBestFriend());
        
        // Test empty grades list
        assertTrue("New student should have empty grades list", student.getGrades().isEmpty());
        assertEquals("Empty grades list should have size 0", 0, student.getGrades().size());
    }
}