package nl.devpieter.sees.exceptions;

import nl.devpieter.sees.models.AnnotatedMethod;

public class EventDispatchException extends RuntimeException {

    public EventDispatchException(Class<?> eventClass, AnnotatedMethod annotatedMethod, Throwable cause) {
        super("Failed to dispatch event: " + eventClass.getSimpleName()
                + " to method: " + annotatedMethod.method().getName()
                + " in listener: " + annotatedMethod.listener().getClass().getSimpleName(), cause);
    }
}
