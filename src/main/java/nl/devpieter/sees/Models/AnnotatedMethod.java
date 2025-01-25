package nl.devpieter.sees.Models;

import nl.devpieter.sees.Listener.Listener;

import java.lang.reflect.Method;

public record AnnotatedMethod(Method method, Listener listener, int priority) {
}
