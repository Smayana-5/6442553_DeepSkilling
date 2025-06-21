// ======= Exercise 9: Command Pattern - Home Automation System =======

// 1. Define Command Interface
interface Command {
    void execute();
    void undo();
}

// 2. Implement Receiver Class
class Light {
    private String location;
    private boolean isOn;
    
    public Light(String location) {
        this.location = location;
        this.isOn = false;
    }
    
    public void turnOn() {
        isOn = true;
        System.out.println(location + " light is now ON");
    }
    
    public void turnOff() {
        isOn = false;
        System.out.println(location + " light is now OFF");
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public String getLocation() {
        return location;
    }
}

// Additional receiver for demonstration
class Fan {
    private String location;
    private boolean isOn;
    
    public Fan(String location) {
        this.location = location;
        this.isOn = false;
    }
    
    public void turnOn() {
        isOn = true;
        System.out.println(location + " fan is now ON");
    }
    
    public void turnOff() {
        isOn = false;
        System.out.println(location + " fan is now OFF");
    }
    
    public boolean isOn() {
        return isOn;
    }
}

// 3. Implement Concrete Commands
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOn();
    }
    
    @Override
    public void undo() {
        light.turnOff();
    }
}

class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOff();
    }
    
    @Override
    public void undo() {
        light.turnOn();
    }
}

class FanOnCommand implements Command {
    private Fan fan;
    
    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute() {
        fan.turnOn();
    }
    
    @Override
    public void undo() {
        fan.turnOff();
    }
}

class FanOffCommand implements Command {
    private Fan fan;
    
    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute() {
        fan.turnOff();
    }
    
    @Override
    public void undo() {
        fan.turnOn();
    }
}

// No Operation Command (Null Object Pattern)
class NoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("No command assigned");
    }
    
    @Override
    public void undo() {
        System.out.println("No command to undo");
    }
}

// 4. Implement Invoker Class
class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;
    private int slots;
    
    public RemoteControl(int slots) {
        this.slots = slots;
        onCommands = new Command[slots];
        offCommands = new Command[slots];
        
        Command noCommand = new NoCommand();
        for (int i = 0; i < slots; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        if (slot >= 0 && slot < slots) {
            onCommands[slot] = onCommand;
            offCommands[slot] = offCommand;
        }
    }
    
    public void onButtonPressed(int slot) {
        if (slot >= 0 && slot < slots) {
            onCommands[slot].execute();
            undoCommand = onCommands[slot];
        }
    }
    
    public void offButtonPressed(int slot) {
        if (slot >= 0 && slot < slots) {
            offCommands[slot].execute();
            undoCommand = offCommands[slot];
        }
    }
    
    public void undoButtonPressed() {
        undoCommand.undo();
    }
    
    public void displayRemote() {
        System.out.println("\n--- Remote Control ---");
        for (int i = 0; i < slots; i++) {
            System.out.println("Slot " + i + ": " + onCommands[i].getClass().getSimpleName() + 
                             " | " + offCommands[i].getClass().getSimpleName());
        }
        System.out.println("Undo: " + undoCommand.getClass().getSimpleName());
        System.out.println("---------------------\n");
    }
}

// 5. Test Class
public class CommandPatternTest {
    public static void main(String[] args) {
        System.out.println("=== Command Pattern - Home Automation Demo ===\n");
        
        // Create receiver objects
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        Fan ceilingFan = new Fan("Bedroom");
        
        // Create command objects
        Command livingRoomLightOn = new LightOnCommand(livingRoomLight);
        Command livingRoomLightOff = new LightOffCommand(livingRoomLight);
        Command kitchenLightOn = new LightOnCommand(kitchenLight);
        Command kitchenLightOff = new LightOffCommand(kitchenLight);
        Command fanOn = new FanOnCommand(ceilingFan);
        Command fanOff = new FanOffCommand(ceilingFan);
        
        // Create remote control with 3 slots
        RemoteControl remote = new RemoteControl(3);
        
        // Set commands to remote slots
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, kitchenLightOn, kitchenLightOff);
        remote.setCommand(2, fanOn, fanOff);
        
        // Display remote configuration
        remote.displayRemote();
        
        // Test commands
        System.out.println("1. Testing Light Commands:");
        remote.onButtonPressed(0);  // Living room light on
        remote.offButtonPressed(0); // Living room light off
        remote.undoButtonPressed(); // Undo (turn on again)
        
        System.out.println("\n2. Testing Kitchen Light:");
        remote.onButtonPressed(1);  // Kitchen light on
        remote.undoButtonPressed(); // Undo (turn off)
        
        System.out.println("\n3. Testing Fan Commands:");
        remote.onButtonPressed(2);  // Fan on
        remote.offButtonPressed(2); // Fan off
        
        // Interactive mode
        System.out.println("\n4. Interactive Mode:");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Living Room Light ON");
            System.out.println("2. Living Room Light OFF");
            System.out.println("3. Kitchen Light ON");
            System.out.println("4. Kitchen Light OFF");
            System.out.println("5. Fan ON");
            System.out.println("6. Fan OFF");
            System.out.println("7. Undo Last Command");
            System.out.println("8. Display Remote Status");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1: remote.onButtonPressed(0); break;
                case 2: remote.offButtonPressed(0); break;
                case 3: remote.onButtonPressed(1); break;
                case 4: remote.offButtonPressed(1); break;
                case 5: remote.onButtonPressed(2); break;
                case 6: remote.offButtonPressed(2); break;
                case 7: remote.undoButtonPressed(); break;
                case 8: remote.displayRemote(); break;
                case 0: 
                    System.out.println("Exiting Command Pattern demonstration...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}