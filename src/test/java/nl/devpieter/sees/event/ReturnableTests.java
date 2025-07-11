package nl.devpieter.sees.event;

import nl.devpieter.sees.Sees;
import nl.devpieter.sees.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReturnableTests {

    @Test
    void testReturnableEvent() {
        Sees sees = Sees.getInstance();
        TestUtils.DummyResultListener listener = new TestUtils.DummyResultListener();
        sees.subscribe(listener);

        TestUtils.DummyResultEvent event = new TestUtils.DummyResultEvent();
        String result = sees.dispatchWithResult(event);

        Assertions.assertEquals("Hello from listener", result, "Event should return the expected result");
    }
}
