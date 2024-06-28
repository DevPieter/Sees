package nl.devpieter.sees;

import nl.devpieter.sees.Annotations.EventListener;
import nl.devpieter.sees.Event.CancelableEvent;
import nl.devpieter.sees.Event.Event;
import nl.devpieter.sees.Listener.Listener;
import nl.devpieter.sees.Models.AnnotatedMethod;

import java.util.*;
import java.util.stream.Collectors;

public class Sees {

    private static Sees INSTANCE;

    public static Sees getInstance() {
        if (INSTANCE == null) INSTANCE = new Sees();
        return INSTANCE;
    }

    private final Map<Listener, List<AnnotatedMethod>> listeners = new HashMap<>();

    public void subscribe(Listener listener) {
        if (listener == null) throw new IllegalArgumentException("Listener cannot be null.");

        List<AnnotatedMethod> annotatedMethods = Arrays.stream(listener.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(EventListener.class))
                .filter(method -> method.getParameterCount() == 1)
                .map(method -> new AnnotatedMethod(method, listener))
                .collect(Collectors.toList());

        this.listeners.put(listener, annotatedMethods);
    }

    public void unsubscribe(Listener listener) {
        if (listener == null) throw new IllegalArgumentException("Listener cannot be null.");
        this.listeners.remove(listener);
    }

    public boolean call(Event event) {
        if (event == null) throw new IllegalArgumentException("Event cannot be null.");

        this.listeners.values().stream()
                .flatMap(Collection::stream)
                .filter(annotatedMethod -> {
                    Class<?>[] parameterTypes = annotatedMethod.method().getParameterTypes();
                    return parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(event.getClass());
                })
                .forEach(annotatedMethod -> {
                    try {
                        annotatedMethod.method().invoke(annotatedMethod.listener(), event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        return event instanceof CancelableEvent cancelable && cancelable.isCancelled();
    }
}
