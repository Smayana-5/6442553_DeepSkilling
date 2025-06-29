package com.example;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private int age;
    private List<Double> grades;
    private boolean isActive;
    
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.grades = new ArrayList<>();
        this.isActive = true;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }
    
    public void addGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        grades.add(grade);
    }
    
    public List<Double> getGrades() {
        return new ArrayList<>(grades); // Return copy to prevent external modification
    }
    
    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
    
    public boolean isPassingStudent() {
        return getAverageGrade() >= 60.0;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void deactivate() {
        this.isActive = false;
    }
    
    public String getStudentInfo() {
        return String.format("Student: %s, Age: %d, Average: %.2f", name, age, getAverageGrade());
    }
    
    public String[] getSubjects() {
        return new String[]{"Math", "Science", "English", "History"};
    }
    
    public Student getBestFriend() {
        return null; // For testing null assertions
    }
}