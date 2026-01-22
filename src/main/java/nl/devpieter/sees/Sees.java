package nl.devpieter.sees;

import nl.devpieter.sees.annotations.SEventListener;
import nl.devpieter.sees.event.SCancelableEvent;
import nl.devpieter.sees.event.SEvent;
import nl.devpieter.sees.event.SReturnableEvent;
import nl.devpieter.sees.exceptions.EventDispatchException;
import nl.devpieter.sees.listener.SListener;
import nl.devpieter.sees.models.AnnotatedMethod;
import nl.devpieter.sees.models.ListenerContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
public final class Sees {

    private static final Sees SHARED_INSTANCE = new Sees();

    private final List<ListenerContainer> listeners = new ArrayList<>();

    /**
     * Returns the singleton instance of Sees.
     * <p>
     * This method ensures only one instance of Sees is created and used throughout the application.
     * You can still create multiple instances, but it is recommended to use the singleton instance
     * for consistency and to avoid unnecessary complexity in event management.
     *
     * @return the singleton instance of Sees
     * @deprecated use {@link #getSharedInstance()} instead
     */
    @Deprecated(since = "1.2.1, use getSharedInstance() instead", forRemoval = true)
    public static Sees getInstance() {
        return SHARED_INSTANCE;
    }

    /**
     * Creates a new instance of Sees.
     * <p>
     * This method allows the creation of separate Sees instances if needed.
     * However, it is generally recommended to use the shared singleton instance
     * via {@link #getSharedInstance()} for consistent event management.
     *
     * @return a new instance of Sees
     */
    @Contract(" -> new")
    public static @NotNull Sees createInstance() {
        return new Sees();
    }

    /**
     * Returns the shared singleton instance of Sees.
     * <p>
     * This method provides access to the single shared instance of Sees used throughout the application.
     * It is recommended to use this instance for consistent event management.
     *
     * @return the shared singleton instance of Sees
     */
    public static Sees getSharedInstance() {
        return SHARED_INSTANCE;
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
    public synchronized void subscribe(@NotNull SListener listener) {
        List<AnnotatedMethod> annotatedMethods = Arrays.stream(listener.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(SEventListener.class))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> SEvent.class.isAssignableFrom(method.getParameterTypes()[0]))
                .map(method -> {
                    SEventListener annotation = method.getAnnotation(SEventListener.class);
                    method.setAccessible(true);

                    return new AnnotatedMethod(
                            method,
                            listener,
                            (Class<? extends SEvent>) method.getParameterTypes()[0],
                            annotation.priority(),
                            annotation.ignoreCancelled()
                    );
                })
                .sorted(Comparator.comparingInt(AnnotatedMethod::priority).reversed().thenComparing(am -> am.method().getName()))
                .collect(Collectors.toList());

        listeners.add(new ListenerContainer(listener, listener.priority(), annotatedMethods));
        listeners.sort(Comparator.comparingInt(ListenerContainer::priority).reversed().thenComparing(lc -> lc.listener().getClass().getName()));
    }

    /**
     * Unsubscribes a previously registered listener.
     * <p>
     * The listener and all of its associated annotated methods will no longer receive events after this call.
     *
     * @param listener the listener to unsubscribe
     * @throws NullPointerException if the listener is {@code null}
     */
    public synchronized void unsubscribe(@NotNull SListener listener) {
        listeners.removeIf(container -> container.listener() == listener);
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
        List<ListenerContainer> snapshot;

        synchronized (this) {
            snapshot = new ArrayList<>(listeners);
        }

        boolean wasCancelled = false;
        SCancelableEvent cancelableEvent = event instanceof SCancelableEvent ce ? ce : null;

        for (ListenerContainer container : snapshot) {
            for (AnnotatedMethod annotatedMethod : container.methods()) {

                if (!annotatedMethod.eventType().isAssignableFrom(event.getClass())) {
                    continue;
                }

                if (wasCancelled && annotatedMethod.ignoreCancelled()) {
                    continue;
                }

                try {
                    annotatedMethod.method().invoke(annotatedMethod.listener(), event);
                } catch (Exception e) {
                    throw new EventDispatchException(event.getClass(), annotatedMethod, e);
                }

                if (!wasCancelled && cancelableEvent != null && cancelableEvent.isCancelled()) {
                    wasCancelled = true;
                }
            }
        }

        return wasCancelled;
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
        dispatch(event);
        return event.getResult();
    }
}
