package nl.devpieter.sees.event;

import nl.devpieter.sees.Sees;
import nl.devpieter.sees.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CancelTests {

    @Test
    void testCancelableEvent() {
        Sees sees = Sees.getInstance();
        TestUtils.DummyCancelingListener cancelListener = new TestUtils.DummyCancelingListener();
        sees.subscribe(cancelListener);

        TestUtils.DummyCancelableEvent event = new TestUtils.DummyCancelableEvent();
        boolean cancelled = sees.dispatch(event);

        Assertions.assertTrue(cancelled, "Event should be marked as cancelled");
    }
}
