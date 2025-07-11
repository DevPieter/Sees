package nl.devpieter.sees.event;

/**
 * Marker interface for events in Sees.
 * <p>
 * Classes implementing {@code SEvent} represent dispatchable events that can be
 * consumed by listener methods annotated with {@link nl.devpieter.sees.annotations.SEventListener}.
 * <p>
 * This interface does not declare any methods, its purpose is to provide a
 * type-safe mechanism for identifying and handling events within Sees.
 *
 * <pre>{@code
 * public record UserLoggedInEvent(String username) implements SEvent {
 * }
 * }</pre>
 */
public interface SEvent {
}
