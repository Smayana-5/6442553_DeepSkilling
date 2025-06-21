import java.text.DecimalFormat;
import java.util.*;

public class FinancialForecaster {
    private List<Double> historicalData;
    private List<Double> growthRates;
    private Scanner scanner;
    private DecimalFormat df;
    
    public FinancialForecaster() {
        this.historicalData = new ArrayList<>();
        this.growthRates = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.df = new DecimalFormat("#,##0.00");
    }
    
    public static void main(String[] args) {
        FinancialForecaster forecaster = new FinancialForecaster();
        forecaster.runInteractiveSession();
    }
    
    public void runInteractiveSession() {
        System.out.println("===============================================");
        System.out.println("    FINANCIAL FORECASTING TOOL");
        System.out.println("    Recursive Algorithm Based Predictions");
        System.out.println("===============================================\n");
        
        inputHistoricalData();
        calculateGrowthRates();
        showDataSummary();
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    performSimpleForecast();
                    break;
                case 2:
                    performAdvancedForecast();
                    break;
                case 3:
                    performRangeForecast();
                    break;
                case 4:
                    addMoreData();
                    break;
                case 5:
                    showDataSummary();
                    break;
                case 6:
                    System.out.println("\nThank you for using the Financial Forecasting Tool!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void inputHistoricalData() {
        System.out.println("Enter your historical financial data:");
        System.out.println("(Type 'done' when finished, minimum 2 values required)\n");
        
        int count = 1;
        while (true) {
            System.out.print("Value #" + count + ": $");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("done")) {
                if (historicalData.size() < 2) {
                    System.out.println("Please enter at least 2 values for forecasting.");
                    continue;
                }
                break;
            }
            
            try {
                // Remove dollar signs and commas if present
                input = input.replace("$", "").replace(",", "");
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Please enter a positive value.");
                    continue;
                }
                historicalData.add(value);
                count++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'done'.");
            }
        }
    }
    
    private void calculateGrowthRates() {
        growthRates.clear();
        for (int i = 1; i < historicalData.size(); i++) {
            double previousValue = historicalData.get(i - 1);
            double currentValue = historicalData.get(i);
            
            if (previousValue != 0) {
                double growthRate = (currentValue - previousValue) / previousValue;
                growthRates.add(growthRate);
            } else {
                growthRates.add(0.0);
            }
        }
    }
    
    private void showDataSummary() {
        System.out.println("\n--- DATA SUMMARY ---");
        System.out.println("Historical Values: " + historicalData.size() + " data points");
        System.out.print("Values: ");
        for (int i = 0; i < historicalData.size(); i++) {
            System.out.print("$" + df.format(historicalData.get(i)));
            if (i < historicalData.size() - 1) System.out.print(", ");
        }
        
        if (!growthRates.isEmpty()) {
            double avgGrowth = growthRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            System.out.println("\nAverage Growth Rate: " + String.format("%.2f%%", avgGrowth * 100));
            System.out.println("Latest Value: $" + df.format(historicalData.get(historicalData.size() - 1)));
        }
        System.out.println();
    }
    
    private void showMenu() {
        System.out.println("\n--- FORECASTING OPTIONS ---");
        System.out.println("1. Simple Forecast (Single Period)");
        System.out.println("2. Advanced Forecast (Multiple Methods)");
        System.out.println("3. Range Forecast (Multiple Periods)");
        System.out.println("4. Add More Historical Data");
        System.out.println("5. Show Data Summary");
        System.out.println("6. Exit");
        System.out.print("\nChoose an option (1-6): ");
    }
    
    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private int getPeriods() {
        while (true) {
            System.out.print("Enter number of periods to forecast: ");
            try {
                int periods = Integer.parseInt(scanner.nextLine().trim());
                if (periods > 0 && periods <= 50) {
                    return periods;
                } else {
                    System.out.println("Please enter a number between 1 and 50.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private void performSimpleForecast() {
        int periods = getPeriods();
        double avgGrowth = growthRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double forecast = recursiveCompoundForecast(historicalData.get(historicalData.size() - 1), periods, avgGrowth);
        
        System.out.println("\n--- SIMPLE FORECAST RESULT ---");
        System.out.println("Periods ahead: " + periods);
        System.out.println("Method: Compound Growth (Average Rate)");
        System.out.println("Growth Rate Used: " + String.format("%.2f%%", avgGrowth * 100));
        System.out.println("Predicted Value: $" + df.format(forecast));
        System.out.println("Growth from Current: " + String.format("%.2f%%", 
            ((forecast - historicalData.get(historicalData.size() - 1)) / historicalData.get(historicalData.size() - 1)) * 100));
    }
    
    private void performAdvancedForecast() {
        int periods = getPeriods();
        double currentValue = historicalData.get(historicalData.size() - 1);
        
        // Different forecasting methods
        double avgGrowth = growthRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double compoundForecast = recursiveCompoundForecast(currentValue, periods, avgGrowth);
        double conservativeForecast = recursiveConservativeForecast(currentValue, periods);
        double trendForecast = recursiveTrendForecast(currentValue, periods);
        
        System.out.println("\n--- ADVANCED FORECAST RESULTS ---");
        System.out.println("Periods ahead: " + periods);
        System.out.println("Current Value: $" + df.format(currentValue));
        System.out.println();
        System.out.println("Method 1 - Compound Growth: $" + df.format(compoundForecast));
        System.out.println("Method 2 - Conservative (80% growth): $" + df.format(conservativeForecast));
        System.out.println("Method 3 - Trend-Based: $" + df.format(trendForecast));
        System.out.println();
        
        double average = (compoundForecast + conservativeForecast + trendForecast) / 3;
        System.out.println("Average of All Methods: $" + df.format(average));
        System.out.println("Range: $" + df.format(Math.min(Math.min(compoundForecast, conservativeForecast), trendForecast)) + 
                          " - $" + df.format(Math.max(Math.max(compoundForecast, conservativeForecast), trendForecast)));
    }
    
    private void performRangeForecast() {
        System.out.print("Enter maximum periods to forecast (1-20): ");
        int maxPeriods;
        try {
            maxPeriods = Integer.parseInt(scanner.nextLine().trim());
            if (maxPeriods < 1 || maxPeriods > 20) {
                System.out.println("Using default of 5 periods.");
                maxPeriods = 5;
            }
        } catch (NumberFormatException e) {
            System.out.println("Using default of 5 periods.");
            maxPeriods = 5;
        }
        
        double currentValue = historicalData.get(historicalData.size() - 1);
        double avgGrowth = growthRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        
        System.out.println("\n--- RANGE FORECAST RESULTS ---");
        System.out.println("Method: Compound Growth");
        System.out.println("Growth Rate: " + String.format("%.2f%%", avgGrowth * 100));
        System.out.println();
        System.out.printf("%-8s %-12s %-12s\n", "Period", "Value", "Growth");
        System.out.println("--------------------------------");
        
        for (int i = 1; i <= maxPeriods; i++) {
            double forecast = recursiveCompoundForecast(currentValue, i, avgGrowth);
            double totalGrowth = ((forecast - currentValue) / currentValue) * 100;
            System.out.printf("%-8d $%-11s %+.1f%%\n", i, df.format(forecast), totalGrowth);
        }
    }
    
    private void addMoreData() {
        System.out.println("\n--- ADD MORE DATA ---");
        System.out.println("Current data points: " + historicalData.size());
        System.out.println("Enter additional values (type 'done' when finished):");
        
        int count = historicalData.size() + 1;
        boolean addedData = false;
        
        while (true) {
            System.out.print("Value #" + count + ": $");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            
            try {
                input = input.replace("$", "").replace(",", "");
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Please enter a positive value.");
                    continue;
                }
                historicalData.add(value);
                addedData = true;
                count++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'done'.");
            }
        }
        
        if (addedData) {
            calculateGrowthRates();
            System.out.println("Data updated successfully!");
            showDataSummary();
        } else {
            System.out.println("No new data added.");
        }
    }
    
    // RECURSIVE FORECASTING METHODS
    
    /**
     * Simple recursive compound growth forecast
     */
    private double recursiveCompoundForecast(double currentValue, int periodsRemaining, double growthRate) {
        // Base case
        if (periodsRemaining == 0) {
            return currentValue;
        }
        
        // Recursive case: apply growth and continue
        double nextValue = currentValue * (1 + growthRate);
        return recursiveCompoundForecast(nextValue, periodsRemaining - 1, growthRate);
    }
    
    /**
     * Conservative recursive forecast (reduces growth rate)
     */
    private double recursiveConservativeForecast(double currentValue, int periodsRemaining) {
        if (periodsRemaining == 0) {
            return currentValue;
        }
        
        double avgGrowth = growthRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double conservativeGrowth = avgGrowth * 0.8; // 80% of average growth
        
        double nextValue = currentValue * (1 + conservativeGrowth);
        return recursiveConservativeForecast(nextValue, periodsRemaining - 1);
    }
    
    /**
     * Trend-based recursive forecast (uses recent trend)
     */
    private double recursiveTrendForecast(double currentValue, int periodsRemaining) {
        if (periodsRemaining == 0) {
            return currentValue;
        }
        
        // Use last 3 growth rates if available, otherwise use average
        double trendGrowth;
        if (growthRates.size() >= 3) {
            int size = growthRates.size();
            trendGrowth = (growthRates.get(size - 1) + growthRates.get(size - 2) + growthRates.get(size - 3)) / 3.0;
        } else {
            trendGrowth = growthRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        }
        
        double nextValue = currentValue * (1 + trendGrowth);
        return recursiveTrendForecast(nextValue, periodsRemaining - 1);
    }
}