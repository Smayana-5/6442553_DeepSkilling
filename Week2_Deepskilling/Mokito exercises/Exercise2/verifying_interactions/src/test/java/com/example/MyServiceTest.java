package com.example;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for MyService - Exercise 2: Verifying Interactions
 */
public class MyServiceTest {
    
    @Mock
    private ExternalApi mockApi;
    
    private MyService service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new MyService(mockApi);
    }
    
    /**
     * Exercise 2 - Basic Solution: Verify that a method is called
     */
    @Test
    public void testVerifyInteraction() {
        // 1. Mock object is created using @Mock annotation
        // 2. Call the method (this will internally call the mock)
        service.fetchData();
        
        // 3. Verify the interaction - check that getData() was called
        verify(mockApi).getData();
    }
    
    /**
     * Exercise 2 - Extended: Verify method is called with specific arguments
     */
    @Test
    public void testVerifyInteractionWithSpecificArguments() {
        // 2. Call the method with specific arguments
        String testId = "user123";
        service.fetchDataById(testId);
        
        // 3. Verify the interaction with specific arguments
        verify(mockApi).getDataWithId("user123");
        
        // Alternative using eq() matcher for clarity
        verify(mockApi).getDataWithId(eq("user123"));
    }
    
    /**
     * Exercise 2 - Extended: Verify void method with specific arguments
     */
    @Test
    public void testVerifyVoidMethodWithSpecificArguments() {
        // 2. Call the method with specific arguments
        String testData = "important data";
        service.processUserData(testData);
        
        // 3. Verify the interaction with specific arguments
        verify(mockApi).processData("important data");
    }
    
    /**
     * Exercise 2 - Extended: Verify number of interactions
     */
    @Test
    public void testVerifyNumberOfInteractions() {
        // 2. Call the method multiple times
        service.fetchData();
        service.fetchData();
        
        // 3. Verify the method was called exactly 2 times
        verify(mockApi, times(2)).getData();
    }
    
    /**
     * Exercise 2 - Extended: Verify no interactions occurred
     */
    @Test
    public void testVerifyNoInteractions() {
        // Don't call any methods on the service
        
        // 3. Verify no interactions occurred with the mock
        verifyNoInteractions(mockApi);
    }
    
    /**
     * Exercise 2 - Extended: Verify with argument matchers
     */
    @Test
    public void testVerifyWithArgumentMatchers() {
        // Call method with any string
        service.fetchDataById("someId");
        
        // Verify using argument matcher
        verify(mockApi).getDataWithId(anyString());
        
        // Verify using startsWith matcher
        service.fetchDataById("test123");
        verify(mockApi).getDataWithId(startsWith("test"));
    }
}
