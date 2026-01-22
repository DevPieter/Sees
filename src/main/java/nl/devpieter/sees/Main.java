package nl.devpieter.sees;

import nl.devpieter.sees.annotations.SEventListener;
import nl.devpieter.sees.event.SCancelableEventBase;
import nl.devpieter.sees.listener.SListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class Main {

    public static void main(String[] args) {
        Sees sees = Sees.getSharedInstance();

        sees.subscribe(new MyListener1());

        boolean cancelled = sees.dispatch(new UserLoggingInEvent("Pieter"));

        if (cancelled) {
            System.out.println("Login was cancelled by a listener.");
        } else {
            System.out.println("Login was successful.");
        }
    }

    public static class UserLoggingInEvent extends SCancelableEventBase {

        private final String username;

        public UserLoggingInEvent(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class MyListener1 implements SListener {

        /*
         * Order:
         * 1. onUserLoggingIn4 (highest priority)
         * 2. onUserLoggingIn1 (cancels)
         * 3. onUserLoggingIn2 (skipped, cancelled)
         * 4. onUserLoggingIn3
         * 5. onUserLoggingIn5 (last due to its name, even though same priority as others)
         *
         * Sorting is done by:
         * 1. Priority of Listener
         * 2. Name of Listener (only if same priority)
         * 3. Priority of Method
         * 4. Name of Method (only if same priority)
         * */

        @SEventListener(ignoreCancelled = false)
        public void onUserLoggingIn5(UserLoggingInEvent event) {
            System.out.println("Listener 1, Method 5.");
        }

        @SEventListener(ignoreCancelled = false)
        public void onUserLoggingIn1(UserLoggingInEvent event) {
            System.out.println("Listener 1, Method 1. (Cancelling event)");
            event.cancel();
        }

        @SEventListener(ignoreCancelled = true)
        public void onUserLoggingIn2(UserLoggingInEvent event) {
            System.out.println("Listener 1, Method 2.");
        }

        @SEventListener(ignoreCancelled = false)
        public void onUserLoggingIn3(UserLoggingInEvent event) {
            System.out.println("Listener 1, Method 3.");
        }

        @SEventListener(ignoreCancelled = true, priority = 20)
        public void onUserLoggingIn4(UserLoggingInEvent event) {
            System.out.println("Listener 1, Method 4. (Highest priority)");
        }
    }
}
