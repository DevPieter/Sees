# Super Easy Event System (Sees)

## Sees is a simple-to-use event system for Java.

`Sees` makes it easy to create, call and listen to events. It is designed to be simple and easy to use.

## Installation

```TODO```

## Events

### Creating a new event

You can create a new event by implementing the `Event` interface.

1. Create a new class.
2. Implement the `Event` interface.
3. Done.

```java
public class MyEvent implements Event {
    private final String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

### Creating a new event using records

You can simplify the creation of a new event by using records.

1. Create a new record.
2. Implement the `Event` interface.
3. Done.

```java
public record MyRecordEvent(String message) implements Event {
}
```

## Listeners

### Creating a new listener

You can create a new listener by implementing the `Listener` interface.

1. Create a new class.
2. Implement the `Listener` interface.
3. Create a method that accepts an event as a parameter.
4. Add the `@EventListener` annotation to the method.
5. Done.

```java
public class MyListener implements Listener {
    @EventListener
    public void onMyEvent(MyEvent event) {
        System.out.println(event.getMessage());
    }
}
```

## Registering and calling an event

You can register a listener and call an event by using the `Sees` class.

1. Get the default instance of `Sees` (or create a new instance).
2. Register a new listener.
3. Call an event.
4. Done.

```java
public class Main {
    public static void main(String[] args) {

        // Get the default instance of `Sees`.
        Sees sees = Sees.getInstance();

        // Register a new listener.
        sees.subscribe(new MyListener());

        // Call an event.
        sees.call(new MyEvent("Hello", "World!"));
    }
}
```