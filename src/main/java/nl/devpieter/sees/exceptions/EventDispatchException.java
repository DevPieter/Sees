package nl.devpieter.sees.exceptions;

import nl.devpieter.sees.models.AnnotatedMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EventDispatchException extends RuntimeException {

    public EventDispatchException(@NotNull Class<?> eventClass, @NotNull AnnotatedMethod annotatedMethod, @Nullable Throwable cause) {
        super("Failed to dispatch event: " + eventClass.getSimpleName()
                + " to method: " + annotatedMethod.method().getName()
                + " in listener: " + annotatedMethod.listener().getClass().getSimpleName(), cause);
    }
}
