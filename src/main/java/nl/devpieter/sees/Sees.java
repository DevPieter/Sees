package nl.devpieter.sees;

import nl.devpieter.sees.Annotations.EventListener;
import nl.devpieter.sees.Event.CancelableEvent;
import nl.devpieter.sees.Event.Event;
import nl.devpieter.sees.Listener.Listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sees {

    private static final Sees INSTANCE = new Sees();

    public static Sees getInstance() {
        return INSTANCE;
    }

    private final List<Listener> listeners = new ArrayList<>();


    public void subscribe(Listener listener) {
        if (listener == null) throw new IllegalArgumentException("Listener cannot be null.");
        this.listeners.add(listener);
    }

    public void unsubscribe(Listener listener) {
        if (listener == null) throw new IllegalArgumentException("Listener cannot be null.");
        this.listeners.remove(listener);
    }

    public boolean call(Event event) {
        if (event == null) throw new IllegalArgumentException("Event cannot be null.");

        for (Listener listener : this.listeners) {
            List<Method> methods = Arrays.stream(listener.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(EventListener.class))
                    .filter(method -> method.getParameterCount() == 1)
                    .filter(method -> method.getParameterTypes()[0].isAssignableFrom(event.getClass())).toList();

            for (Method method : methods) {
                try {
                    method.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (event instanceof CancelableEvent cancelable) return cancelable.isCancelled();
        else return false;
    }
}
