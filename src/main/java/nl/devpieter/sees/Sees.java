package nl.devpieter.sees;

import nl.devpieter.sees.annotations.SEventListener;
import nl.devpieter.sees.event.SCancelableEvent;
import nl.devpieter.sees.event.SEvent;
import nl.devpieter.sees.event.SReturnableEvent;
import nl.devpieter.sees.listener.SListener;
import nl.devpieter.sees.models.AnnotatedMethod;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Core class for managing events and listeners in Sees.
 * <p>
 * This singleton class handles registration of listeners and dispatching of events
 * to the appropriate annotated methods. Listeners are executed in order based on
 * their priority and the priority of the methods annotated with {@link nl.devpieter.sees.annotations.SEventListener}.
 *
 * <pre>{@code
 * Sees sees = Sees.getInstance();
 * sees.subscribe(new UserListener());
 * sees.dispatch(new UserLoggedInEvent("Pieter"));
 * }</pre>
 */
public class Sees {

    private static Sees INSTANCE;

    private Map<SListener, List<AnnotatedMethod>> listeners = new LinkedHashMap<>();

    /**
     * Returns the singleton instance of Sees.
     * <p>
     * This method ensures only one instance of Sees is created and used throughout the application.
     * You can still create multiple instances, but it is recommended to use the singleton instance
     * for consistency and to avoid unnecessary complexity in event management.
     *
     * @return the singleton instance of Sees
     */
    public static Sees getInstance() {
        if (INSTANCE == null) INSTANCE = new Sees();
        return INSTANCE;
    }

    /**
     * Subscribes a listener to receive events.
     * <p>
     * This method scans the provided listener for methods annotated with
     * {@link nl.devpieter.sees.annotations.SEventListener} and registers them for future event dispatches.
     * Methods must accept exactly one parameter of a type that implements {@link nl.devpieter.sees.event.SEvent}.
     * <p>
     * Listener and method priorities are respected during event execution.
     *
     * @param listener the listener to subscribe
     * @throws NullPointerException if the listener is {@code null}
     */
    public void subscribe(@NotNull SListener listener) {
        List<AnnotatedMethod> annotatedMethods = Arrays.stream(listener.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(SEventListener.class))
                .filter(method -> method.getParameterCount() == 1)
                .map(method -> {
                    SEventListener annotation = method.getAnnotation(SEventListener.class);
                    return new AnnotatedMethod(method, listener, annotation.priority());
                })
                .sorted(Comparator.comparingInt(AnnotatedMethod::priority).reversed())
                .collect(Collectors.toList());

        this.listeners.put(listener, annotatedMethods);
        this.sortListeners();
    }

    /**
     * Unsubscribes a previously registered listener.
     * <p>
     * The listener and all of its associated annotated methods will no longer receive events after this call.
     *
     * @param listener the listener to unsubscribe
     * @throws NullPointerException if the listener is {@code null}
     */
    public void unsubscribe(@NotNull SListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Dispatches an event to all applicable listeners.
     * <p>
     * All methods annotated with {@link nl.devpieter.sees.annotations.SEventListener} that
     * accept the provided event type (or its supertype) will be invoked.
     * <p>
     * If the event is an instance of {@link nl.devpieter.sees.event.SCancelableEvent}, this method returns whether
     * the event was cancelled during dispatching.
     *
     * @param event the event to dispatch
     * @return {@code true} if the event is cancellable and was cancelled, {@code false} otherwise
     * @throws NullPointerException if the event is {@code null}
     */
    public boolean dispatch(@NotNull SEvent event) {
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

        return event instanceof SCancelableEvent cancelable && cancelable.isCancelled();
    }

    /**
     * Dispatches a returnable event and retrieves its result.
     * <p>
     * This method behaves like {@link #dispatch(SEvent)} but is intended for use with
     * events implementing {@link nl.devpieter.sees.event.SReturnableEvent}. After all listeners have processed
     * the event, the result value is returned.
     *
     * @param event the returnable event to dispatch
     * @param <T>   the type of the return value
     * @return the result provided by the dispatched event
     * @throws NullPointerException if the event is {@code null}
     */
    public <T> T dispatchWithResult(@NotNull SReturnableEvent<T> event) {
        this.dispatch(event);
        return event.getResult();
    }

    /**
     * Sorts the registered listeners based on their declared priority.
     * <p>
     * Listeners with higher priority values are ordered first.
     * This ensures that event dispatching respects listener priority levels.
     */
    private void sortListeners() {
        this.listeners = this.listeners.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<SListener, List<AnnotatedMethod>> entry) -> entry.getKey().priority()).reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }
}
