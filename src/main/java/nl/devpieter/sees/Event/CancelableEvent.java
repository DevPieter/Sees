package nl.devpieter.sees.Event;

public interface CancelableEvent extends Event {

    void cancel();

    boolean isCancellable();

    boolean isCancelled();
}
