package nl.devpieter.sees.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking methods as event listeners in Sees.
 * <p>
 * Methods annotated with {@code @SEventListener} are automatically invoked
 * when a matching event is dispatched. This annotation should only be applied
 * to methods that accept a single event parameter.
 * <p>
 * The {@code priority} attribute determines the order of execution for this listener.
 * Methods with higher priority values are executed before those with lower values.
 * The default priority is {@code 10}.
 * <p>
 * The {@code ignoreCancelled} attribute specifies whether the listener should
 * be invoked for events that have been cancelled. If set to {@code true}, the listener
 * will be skipped for cancelled events. The default value is {@code true}.
 *
 * <pre>{@code
 * @SEventListener(priority = 50, ignoreCancelled = false)
 * public void onUserLoggedIn(UserLoggedInEvent event) {
 *     // Implementation here
 * }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SEventListener {

    /**
     * Determines the execution priority for the event listener.
     * <p>
     * Higher values indicate earlier execution during event dispatching.
     * The default priority is {@code 10}.
     *
     * @return the priority value used to order listeners
     */
    int priority() default 10;

    /**
     * Indicates whether the listener should ignore cancelled events.
     * <p>
     * If set to {@code true}, the listener will not be invoked for events that have been cancelled.
     * The default value is {@code true}.
     *
     * @return {@code true} if the listener should ignore cancelled events, {@code false} otherwise
     */
    boolean ignoreCancelled() default true;
}

