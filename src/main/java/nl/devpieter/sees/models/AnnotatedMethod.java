package nl.devpieter.sees.models;

import nl.devpieter.sees.event.SEvent;
import nl.devpieter.sees.listener.SListener;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Method;

/**
 * Internal record holding information about a method annotated with {@code @SEventListener}.
 * <p>
 * Stores the method reference, its owning listener instance, and the priority defined in the annotation.
 * This class is used internally by Sees to manage and dispatch events.
 *
 * @param method   the method annotated with {@code @SEventListener}
 * @param listener the listener instance that owns the method
 * @param priority the execution priority of this method
 */
@ApiStatus.Internal
public record AnnotatedMethod(
        Method method,
        SListener listener,
        Class<? extends SEvent> eventType,
        int priority,
        boolean ignoreCancelled
) {
}
