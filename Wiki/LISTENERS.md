### Creating a listener

To create a listener, you create a class and implement the `Listener` interface.

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

### Subscribing and unsubscribing a listener

To subscribe or unsubscribe a listener, you use an instance of `Sees` and use the `subscribe` or `unsubscribe`
method.

1. Get the default instance of `Sees` (or create a new instance).
2. Subscribe or unsubscribe a listener.
3. Done.

```java
public class Main {
    public static void main(String[] args) {

        // Get the default instance of `Sees`.
        Sees sees = Sees.getInstance();

        MyListener listener = new MyListener();

        // Subscribe a listener.
        sees.subscribe(listener);

        // Unsubscribe a listener.
        sees.unsubscribe(listener);
    }
}
```