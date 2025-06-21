public class Computer {
    private String CPU;
    private String RAM;
    private String storage;
    private String graphicsCard;
    private String motherboard;
    private boolean hasBluetooth;
    private boolean hasWiFi;
    
    // Private constructor that takes Builder as parameter
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.motherboard = builder.motherboard;
        this.hasBluetooth = builder.hasBluetooth;
        this.hasWiFi = builder.hasWiFi;
    }
    
    // Static nested Builder class
    public static class Builder {
        private String CPU;
        private String RAM;
        private String storage;
        private String graphicsCard;
        private String motherboard;
        private boolean hasBluetooth;
        private boolean hasWiFi;
        
        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }
        
        public Builder setRAM(String RAM) {
            this.RAM = RAM;
            return this;
        }
        
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        
        public Builder setMotherboard(String motherboard) {
            this.motherboard = motherboard;
            return this;
        }
        
        public Builder setBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }
        
        public Builder setWiFi(boolean hasWiFi) {
            this.hasWiFi = hasWiFi;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
    
    @Override
    public String toString() {
        return "Computer Configuration:\n" +
               "CPU: " + (CPU != null ? CPU : "Not specified") + "\n" +
               "RAM: " + (RAM != null ? RAM : "Not specified") + "\n" +
               "Storage: " + (storage != null ? storage : "Not specified") + "\n" +
               "Graphics Card: " + (graphicsCard != null ? graphicsCard : "Not specified") + "\n" +
               "Motherboard: " + (motherboard != null ? motherboard : "Not specified") + "\n" +
               "Bluetooth: " + (hasBluetooth ? "Yes" : "No") + "\n" +
               "WiFi: " + (hasWiFi ? "Yes" : "No");
    }
}
