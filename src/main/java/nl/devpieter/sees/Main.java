package nl.devpieter.sees;

import nl.devpieter.sees.Annotations.EventListener;
import nl.devpieter.sees.Event.Event;
import nl.devpieter.sees.Listener.Listener;

public class Main {

    public static void main(String[] args) {

        Sees sees = Sees.getInstance();
        sees.subscribe(new MyListener());

        sees.call(new MyEvent("Hello, World! from MyEvent"));
        sees.call(new MyRecordEvent("Hello, World! from MyRecordEvent"));
    }
}

class MyEvent implements Event {

    private final String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

record MyRecordEvent(String message) implements Event {
}

class MyListener implements Listener {

    @EventListener
    public void onMyEvent(MyEvent event) {
        System.out.println(event.getMessage());
    }

    @EventListener
    public void onMyRecordEvent(MyRecordEvent event) {
        System.out.println(event.message());
    }
}
