package nl.devpieter.sees.models;

import nl.devpieter.sees.listener.SListener;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public record ListenerContainer(SListener listener, int priority, List<AnnotatedMethod> methods) {
}
