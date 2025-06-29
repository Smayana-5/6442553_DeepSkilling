package com.example;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    
    // Test fixture - shared test data
    private Calculator calculator;
    private double delta = 0.001; // For floating point comparisons
    
    // Setup method - runs before each test
    @Before
    public void setUp() {
        System.out.println("Setting up test...");
        // Arrange - Initialize the calculator for each test
        calculator = new Calculator();
    }
    
    // Teardown method - runs after each test
    @After
    public void tearDown() {
        System.out.println("Cleaning up after test...");
        // Clean up resources if needed
        calculator.clear();
        calculator = null;
    }
    
    @Test
    public void testAddition() {
        // Arrange
        double firstNumber = 10.0;
        double secondNumber = 5.0;
        double expectedResult = 15.0;
        
        // Act
        double actualResult = calculator.add(firstNumber, secondNumber);
        
        // Assert
        assertEquals("Addition should return correct sum", expectedResult, actualResult, delta);
        assertEquals("Calculator should store the result", expectedResult, calculator.getResult(), delta);
    }
    
    @Test
    public void testSubtraction() {
        // Arrange
        double minuend = 20.0;
        double subtrahend = 8.0;
        double expectedResult = 12.0;
        
        // Act
        double actualResult = calculator.subtract(minuend, subtrahend);
        
        // Assert
        assertEquals("Subtraction should return correct difference", expectedResult, actualResult, delta);
        assertEquals("Calculator should store the result", expectedResult, calculator.getResult(), delta);
    }
    
    @Test
    public void testMultiplication() {
        // Arrange
        double multiplicand = 6.0;
        double multiplier = 4.0;
        double expectedResult = 24.0;
        
        // Act
        double actualResult = calculator.multiply(multiplicand, multiplier);
        
        // Assert
        assertEquals("Multiplication should return correct product", expectedResult, actualResult, delta);
        assertEquals("Calculator should store the result", expectedResult, calculator.getResult(), delta);
    }
    
    @Test
    public void testDivision() {
        // Arrange
        double dividend = 15.0;
        double divisor = 3.0;
        double expectedResult = 5.0;
        
        // Act
        double actualResult = calculator.divide(dividend, divisor);
        
        // Assert
        assertEquals("Division should return correct quotient", expectedResult, actualResult, delta);
        assertEquals("Calculator should store the result", expectedResult, calculator.getResult(), delta);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDivisionByZero() {
        // Arrange
        double dividend = 10.0;
        double divisor = 0.0;
        
        // Act & Assert
        // This should throw IllegalArgumentException
        calculator.divide(dividend, divisor);
    }
    
    @Test
    public void testClear() {
        // Arrange
        calculator.add(10, 5); // Set some result first
        double expectedResult = 0.0;
        
        // Act
        calculator.clear();
        
        // Assert
        assertEquals("Clear should reset result to zero", expectedResult, calculator.getResult(), delta);
    }
    
    @Test
    public void testInitialState() {
        // Arrange & Act
        // Calculator is already initialized in setUp()
        double expectedInitialResult = 0.0;
        
        // Assert
        assertEquals("Calculator should start with zero result", expectedInitialResult, calculator.getResult(), delta);
    }
}