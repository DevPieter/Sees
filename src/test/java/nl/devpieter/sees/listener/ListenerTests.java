package nl.devpieter.sees.listener;

import nl.devpieter.sees.Sees;
import nl.devpieter.sees.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListenerTests {

    @Test
    void testListenerIsCalled() {
        Sees sees = Sees.getInstance();
        TestUtils.DummyListener listener = new TestUtils.DummyListener();
        sees.subscribe(listener);

        sees.dispatch(new TestUtils.DummyEvent());

        Assertions.assertTrue(listener.called, "Listener method should be called");
    }

    @Test
    void testUnsubscribeListener() {
        Sees sees = Sees.getInstance();

        TestUtils.DummyListener listener = new TestUtils.DummyListener();
        sees.subscribe(listener);
        sees.unsubscribe(listener);

        listener.called = false;
        sees.dispatch(new TestUtils.DummyEvent());

        Assertions.assertFalse(listener.called, "Listener method should not be called after unsubscribe");
    }
}
