package com.example;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MyServiceTest {
    
    @Test
    public void testExternalApi() {
        // Step 1: Create a mock object for the external API
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        
        // Step 2: Stub the methods to return predefined values
        when(mockApi.getData()).thenReturn("Mock Data");
        
        // Step 3: Write a test case that uses the mock object
        MyService service = new MyService(mockApi);
        String result = service.fetchData();
        
        // Verify the result
        assertEquals("Mock Data", result);
        
        // Optional: Verify that the method was called
        verify(mockApi).getData();
    }
    
    @Test
    public void testExternalApiWithDifferentData() {
        // Additional test to demonstrate flexibility
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Different Mock Data");
        
        MyService service = new MyService(mockApi);
        String result = service.fetchData();
        
        assertEquals("Different Mock Data", result);
        verify(mockApi, times(1)).getData();
    }
}