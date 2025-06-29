package com.example;
public class Calculator {
    private double result;
    
    public Calculator() {
        this.result = 0.0;
    }
    
    public double add(double a, double b) {
        result = a + b;
        return result;
    }
    
    public double subtract(double a, double b) {
        result = a - b;
        return result;
    }
    
    public double multiply(double a, double b) {
        result = a * b;
        return result;
    }
    
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        result = a / b;
        return result;
    }
    
    public double getResult() {
        return result;
    }
    
    public void clear() {
        result = 0.0;
    }
}
