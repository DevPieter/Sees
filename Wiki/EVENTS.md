### Creating an event

It's easy to create an event. Just create a class and implement the `Event` interface.

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

### Creating an event using records

You can also create an event using records. Just create a record and implement the `Event` interface.

1. Create a new record.
2. Implement the `Event` interface.
3. Done.

```java
public record MyRecordEvent(String message) implements Event {
}
```

### Calling an event

To call an event, you use an instance of `Sees` and use the `call` method.

1. Get the default instance of `Sees` (or create a new instance).
2. Call an event. (This method returns a `boolean` value that indicates if the event was canceled.)
3. Done.

```java
public class Main {
    public static void main(String[] args) {

        // Get the default instance of `Sees`.
        Sees sees = Sees.getInstance();

        // Call an event.
        boolean cancelled = sees.call(new MyEvent("Hello", "World!"));
    }
}
```