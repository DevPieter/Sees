package nl.devpieter.sees.event;

/**
 * Represents an event that can be cancelled in Sees.
 * <p>
 * Implementing this interface allows an event to be cancelled by calling {@link #cancel()}.
 * Event handlers can check if the event was cancelled using {@link #isCancelled()}.
 *
 * <pre>{@code
 * public class UserLoginEvent implements SCancelableEvent {
 *     // Implementation here
 * }
 * }</pre>
 */
public interface SCancelableEvent extends SEvent {

    /**
     * Cancels the event if it is cancellable.
     * <p>
     * Has no effect if {@link #isCancellable()} returns {@code false}.
     */
    void cancel();

    /**
     * Indicates whether this event supports cancellation.
     * <p>
     * If {@code false}, calling {@link #cancel()} will have no effect.
     *
     * @return {@code true} if the event can be cancelled, {@code false} otherwise
     */
    boolean isCancellable();

    /**
     * Checks if the event has been cancelled.
     *
     * @return {@code true} if the event was cancelled, {@code false} otherwise
     */
    boolean isCancelled();
}

