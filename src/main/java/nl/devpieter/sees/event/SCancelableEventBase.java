package nl.devpieter.sees.event;

/**
 * Base class for cancellable events in Sees.
 * <p>
 * This abstract class provides a default implementation of {@link SCancelableEvent}
 * where cancellation is supported by default. Subclasses can override {@link #isCancellable()}
 * to change this behavior.
 */
public abstract class SCancelableEventBase implements SCancelableEvent {

    private boolean cancelled;

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        if (this.isCancellable()) this.cancelled = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancellable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}

