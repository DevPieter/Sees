package nl.devpieter.sees.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {

    /**
     * Specifies the priority of the event listener, the higher the priority the earlier the event listener will be called.
     * The default priority is set to 10.
     *
     * @return the priority of the event listener
     */
    int priority() default 10;
}
