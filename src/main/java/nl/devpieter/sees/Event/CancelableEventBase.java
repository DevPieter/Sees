package nl.devpieter.sees.Event;

public abstract class CancelableEventBase implements CancelableEvent {

    private boolean cancelled;

    @Override
    public void cancel() {
        if (this.isCancellable()) this.cancelled = true;
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
