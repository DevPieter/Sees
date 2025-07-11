package nl.devpieter.sees.event;

/**
 * Represents an event that can return a result in Sees.
 * <p>
 * This interface allows events to carry a modifiable result value,
 * which can be accessed after dispatching using {@link #getResult()}.
 *
 * <pre>{@code
 * public class UserNameEvent implements SReturnableEvent<String> {
 *     private String result;
 *
 *     @Override
 *     public String getResult() {
 *         return result;
 *     }
 *
 *     @Override
 *     public void setResult(String result) {
 *         this.result = result;
 *     }
 * }
 * }</pre>
 *
 * @param <T> the type of the result
 */
public interface SReturnableEvent<T> extends SEvent {

    /**
     * Retrieves the result of the event.
     *
     * @return the result value
     */
    T getResult();

    /**
     * Sets the result of the event.
     *
     * @param result the new result value
     */
    void setResult(T result);
}

