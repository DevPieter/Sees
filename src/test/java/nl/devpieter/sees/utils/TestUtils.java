package nl.devpieter.sees.utils;

import nl.devpieter.sees.annotations.SEventListener;
import nl.devpieter.sees.event.SCancelableEventBase;
import nl.devpieter.sees.event.SEvent;
import nl.devpieter.sees.event.SReturnableEvent;
import nl.devpieter.sees.listener.SListener;

public class TestUtils {

    public static class DummyEvent implements SEvent {
    }

    public static class DummyCancelableEvent extends SCancelableEventBase {
    }

    public static class DummyResultEvent implements SReturnableEvent<String> {

        private String result;

        @Override
        public String getResult() {
            return this.result;
        }

        @Override
        public void setResult(String result) {
            this.result = result;
        }
    }

    public static class DummyListener implements SListener {

        public boolean called = false;

        @SEventListener
        public void onDummy(DummyEvent event) {
            this.called = true;
        }
    }

    public static class DummyCancelingListener implements SListener {

        @SEventListener
        public void onDummyCancelable(DummyCancelableEvent event) {
            event.cancel();
        }
    }

    public static class DummyResultListener implements SListener {

        @SEventListener
        public void onResult(DummyResultEvent event) {
            event.setResult("Hello from listener");
        }
    }
}
