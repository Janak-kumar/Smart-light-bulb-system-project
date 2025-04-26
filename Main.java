import java.util.List;


// Base class - Inheritance
class LightDevice {
    protected boolean isOn;

    public void turnOn() {
        isOn = true;
        System.out.println("Light is turned ON.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println("Light is turned OFF.");
    }
}

// Composition class
class Timer {
    private int duration; // in seconds

    public Timer(int duration) {
        this.duration = duration;
    }

    public void start() {
        System.out.println("Timer started for " + duration + " seconds.");
        try {
            Thread.sleep(duration * 1000L);
            System.out.println("Timer finished.");
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted.");
        }
    }
}

// Derived class - Inherits LightDevice, uses composition
class SmartLightBulb extends LightDevice {
    private Timer timer; // Composition

    public SmartLightBulb(int duration) {
        this.timer = new Timer(duration);
    }

    public void turnOnWithTimer() {
        super.turnOn();
        timer.start();
        super.turnOff();
    }
}

// Aggregation example: Room has SmartLightBulbs
class Room {
    private String roomName;
    private List<SmartLightBulb> bulbs; // Aggregation

    public Room(String roomName, List<SmartLightBulb> bulbs) {
        this.roomName = roomName;
        this.bulbs = bulbs;
    }

    public void controlAllLights() {
        System.out.println("Controlling lights in: " + roomName);
        for (SmartLightBulb bulb : bulbs) {
            bulb.turnOnWithTimer();
        }
    }
}

// Threading: A smart bulb that blinks
class BlinkingBulb extends SmartLightBulb implements Runnable {

    public BlinkingBulb(int duration) {
        super(duration);
    }

    @Override
    public void run() {
        try {
            System.out.println("Blinking started...");
            for (int i = 0; i < 5; i++) {
                super.turnOn();
                Thread.sleep(500);
                super.turnOff();
                Thread.sleep(500);
            }
            System.out.println("Blinking ended.");
        } catch (InterruptedException e) {
            System.out.println("Blinking interrupted.");
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        // Creating SmartLightBulbs
        SmartLightBulb bulb1 = new SmartLightBulb(2);
        SmartLightBulb bulb2 = new SmartLightBulb(3);

        // Aggregating bulbs into a room
        List<SmartLightBulb> bulbList = List.of(bulb1, bulb2);
        Room livingRoom = new Room("Living Room", bulbList);
        livingRoom.controlAllLights();

        // Threaded blinking bulb
        BlinkingBulb blinkingBulb = new BlinkingBulb(1);
        Thread blinkThread = new Thread(blinkingBulb);
        blinkThread.start();
    }
}
