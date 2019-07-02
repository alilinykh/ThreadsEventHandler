import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    public Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }


    synchronized public void push(String message) {

        if (tracker.get(message) == null) {
            tracker.put(message,1);
        }
        else {
            tracker.put(message, tracker.get(message) + 1);
        }
    }

    synchronized public Boolean has(String message) {
        if (tracker.containsKey(message)&& tracker.get(message) > 0) {
            return true;
        }
        else
            return false;
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();
        this.tracker.putIfAbsent(message,1);
        this.tracker.computeIfPresent(message, (m, prev) -> prev - 1);
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }

}
