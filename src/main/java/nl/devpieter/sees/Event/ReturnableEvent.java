package nl.devpieter.sees.Event;

public interface ReturnableEvent<T> extends Event {

    T getResult();

    void setResult(T result);
}
