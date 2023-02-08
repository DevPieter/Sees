package nl.devpieter.sees;

import nl.devpieter.sees.Annotations.EventListener;
import nl.devpieter.sees.Event.Event;
import nl.devpieter.sees.Listener.Listener;

public class Main {
    public static void main(String[] args) {

        Sees sees = Sees.getInstance();
        sees.subscribe(new ExampleListener());

        sees.call(new ExampleEvent("Hello", "World!"));
    }
}

record ExampleEvent(String name, String message) implements Event {
}

class ExampleListener implements Listener {
    @EventListener
    public void onExampleEvent(ExampleEvent event) {
        System.out.println(event.name() + ": " + event.message());
    }
}