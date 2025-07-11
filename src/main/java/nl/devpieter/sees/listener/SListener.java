package nl.devpieter.sees.listener;

/**
 * Interface for listeners in Sees.
 * <p>
 * Classes implementing {@code SListener} can be registered to listen for dispatched events.
 * <p>
 * The {@code priority} method determines the order of execution for this listener.
 * Listeners with higher priority values are executed before those with lower values.
 * The default priority is {@code 10}.
 *
 * <pre>{@code
 * public class UserListener implements SListener {
 *     @Override
 *     public int priority() {
 *         return 50; // High priority
 *     }
 * }
 * }</pre>
 */
public interface SListener {

    /**
     * Determines the execution priority for the event listener.
     * <p>
     * Higher values indicate earlier execution during event dispatching.
     * The default priority is {@code 10}.
     *
     * @return the priority value used to order listeners
     */
    default int priority() {
        return 10;
    }
}
