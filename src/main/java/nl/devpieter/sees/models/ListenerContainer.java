package nl.devpieter.sees.models;

import nl.devpieter.sees.listener.SListener;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * Internal record holding a listener instance along with its priority and associated annotated methods.
 *
 * @param listener the listener instance
 * @param priority the priority of the listener
 * @param methods  the list of annotated methods belonging to the listener
 */
@ApiStatus.Internal
public record ListenerContainer(
        SListener listener,
        int priority,
        List<AnnotatedMethod> methods
) {
}
