package com.example;

/**
 * Interface representing an external API that our service depends on.
 * This will be mocked in our tests.
 */
public interface ExternalApi {
    /**
     * Retrieves data from the external source
     * @return String data
     */
    String getData();
    
    /**
     * Retrieves data by specific ID
     * @param id the identifier for the data
     * @return String data for the given ID
     */
    String getDataWithId(String id);
    
    /**
     * Processes the provided data
     * @param data the data to process
     */
    void processData(String data);
}