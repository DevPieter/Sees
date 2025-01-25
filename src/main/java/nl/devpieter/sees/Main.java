package nl.devpieter.sees;

import nl.devpieter.sees.Annotations.EventListener;
import nl.devpieter.sees.Event.Event;
import nl.devpieter.sees.Event.ReturnableEvent;
import nl.devpieter.sees.Listener.Listener;

public class Main {

    public static void main(String[] args) {

        Sees sees = Sees.getInstance();
        sees.subscribe(new MyListener());

        sees.call(new MyEvent("Hello, World! from MyEvent"));
        sees.call(new MyRecordEvent("Hello, World! from MyRecordEvent"));

        String result = sees.callWithResult(new MyReturnableEvent("Hello, World! from MyReturnableEvent"));
        System.out.println(result);
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

class MyReturnableEvent implements ReturnableEvent<String> {

    private final String message;
    private String result;

    public MyReturnableEvent(String message) {
        this.message = message;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }
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

    /***
     * This event listener has the default priority (10).
     * It will be called before the lower-priority event listeners.
     */
    @EventListener
    public void onMyReturnableEvent(MyReturnableEvent event) {
        System.out.println(event.getMessage());
        event.setResult("You said: " + event.getMessage());
    }

    /***
     * This event listener has a lower priority (5).
     * It will be called after the higher-priority event listeners.
     */
    @EventListener(priority = 5)
    public void onMyReturnableEvent2(MyReturnableEvent event) {
        // Append to the result set by the higher-priority event listener
        event.setResult(event.getResult() + " (2)");
    }
}
