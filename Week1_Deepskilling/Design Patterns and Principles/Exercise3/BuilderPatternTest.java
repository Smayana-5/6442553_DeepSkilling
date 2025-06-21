
// BuilderPatternTest.java

import java.util.Scanner;
public class BuilderPatternTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Computer Builder Pattern Demo ===\n");
        
        // Pre-configured computers
        System.out.println("1. Gaming Computer:");
        Computer gamingPC = new Computer.Builder()
                .setCPU("Intel i9-13900K")
                .setRAM("32GB DDR5")
                .setStorage("1TB NVMe SSD")
                .setGraphicsCard("NVIDIA RTX 4080")
                .setMotherboard("ASUS ROG Strix Z790")
                .setBluetooth(true)
                .setWiFi(true)
                .build();
        System.out.println(gamingPC);
        System.out.println();
        
        System.out.println("2. Office Computer:");
        Computer officePC = new Computer.Builder()
                .setCPU("Intel i5-12400")
                .setRAM("16GB DDR4")
                .setStorage("512GB SSD")
                .setMotherboard("MSI B660M")
                .setBluetooth(true)
                .setWiFi(true)
                .build();
        System.out.println(officePC);
        System.out.println();
        
        System.out.println("3. Budget Computer:");
        Computer budgetPC = new Computer.Builder()
                .setCPU("AMD Ryzen 5 5600G")
                .setRAM("8GB DDR4")
                .setStorage("256GB SSD")
                .setBluetooth(false)
                .setWiFi(true)
                .build();
        System.out.println(budgetPC);
        System.out.println();
        
        // Interactive computer builder
        System.out.println("=== Build Your Custom Computer ===");
        Computer.Builder customBuilder = new Computer.Builder();
        
        System.out.print("Enter CPU (or press Enter to skip): ");
        String cpu = scanner.nextLine().trim();
        if (!cpu.isEmpty()) {
            customBuilder.setCPU(cpu);
        }
        
        System.out.print("Enter RAM (or press Enter to skip): ");
        String ram = scanner.nextLine().trim();
        if (!ram.isEmpty()) {
            customBuilder.setRAM(ram);
        }
        
        System.out.print("Enter Storage (or press Enter to skip): ");
        String storage = scanner.nextLine().trim();
        if (!storage.isEmpty()) {
            customBuilder.setStorage(storage);
        }
        
        System.out.print("Enter Graphics Card (or press Enter to skip): ");
        String gpu = scanner.nextLine().trim();
        if (!gpu.isEmpty()) {
            customBuilder.setGraphicsCard(gpu);
        }
        
        System.out.print("Enter Motherboard (or press Enter to skip): ");
        String motherboard = scanner.nextLine().trim();
        if (!motherboard.isEmpty()) {
            customBuilder.setMotherboard(motherboard);
        }
        
        System.out.print("Include Bluetooth? (y/n): ");
        String bluetooth = scanner.nextLine().trim().toLowerCase();
        customBuilder.setBluetooth(bluetooth.equals("y") || bluetooth.equals("yes"));
        
        System.out.print("Include WiFi? (y/n): ");
        String wifi = scanner.nextLine().trim().toLowerCase();
        customBuilder.setWiFi(wifi.equals("y") || wifi.equals("yes"));
        
        Computer customPC = customBuilder.build();
        System.out.println("\nYour Custom Computer:");
        System.out.println(customPC);
        
        scanner.close();
    }
}