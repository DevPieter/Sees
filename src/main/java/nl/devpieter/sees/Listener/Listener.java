package nl.devpieter.sees.Listener;

public interface Listener {

    /**
     * Specifies the priority of the event listener, the higher the priority the earlier the event listener will be called.
     * The default priority is set to 10.
     *
     * @return the priority of the event listener
     */
    default int priority() {
        return 10;
    }
}
