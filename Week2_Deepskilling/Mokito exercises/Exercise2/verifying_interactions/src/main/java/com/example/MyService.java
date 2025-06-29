package com.example;

/**
 * Service class that depends on ExternalApi.
 * This is the class we want to test.
 */
public class MyService {
    private final ExternalApi externalApi;
    
    /**
     * Constructor with dependency injection
     * @param externalApi the external API dependency
     */
    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }
    
    /**
     * Fetches data using the external API
     * @return the data retrieved from external API
     */
    public String fetchData() {
        return externalApi.getData();
    }
    
    /**
     * Fetches data by ID using the external API
     * @param id the identifier for the data
     * @return the data retrieved for the given ID
     */
    public String fetchDataById(String id) {
        return externalApi.getDataWithId(id);
    }
    
    /**
     * Processes user data using the external API
     * @param userData the user data to process
     */
    public void processUserData(String userData) {
        externalApi.processData(userData);
    }
}