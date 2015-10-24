package cz.muni.fi;

import cz.muni.fi.event.Stock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;



/**
 * Created by vaculik on 16.10.15.
 */
public class TestEventStreamGenerator {

    private static final String stockLabel = "S";
    private static final int numOfEvents = 10;
    private static final int changeIndex = 5;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void nullStockLabelGenerateStream() {
        EventStreamGenerator.generateStockStream(null, numOfEvents, changeIndex);
    }

    @Test
    public void illegalNumOfEvents() {
        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(stockLabel, -10, changeIndex);

        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(stockLabel, 0, changeIndex);

        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(-10, changeIndex);

        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(0, changeIndex);
    }

    @Test
    public void illegalChangeIndex() {
        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(stockLabel, numOfEvents, -1);

        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(stockLabel, numOfEvents, -1);

        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(numOfEvents, -1);

        thrown.expect(IllegalArgumentException.class);
        EventStreamGenerator.generateStockStream(numOfEvents, -1);
    }

    @Test
    public void validParameters() {

        // Test marginal valid values of parameters, should not throw an exception
        EventStreamGenerator.generateStockStream(stockLabel, 1, 0);
        EventStreamGenerator.generateStockStream(1, 0);
    }

    @Test
    public void rightNumOfEvents() {
        List<Object> stream = EventStreamGenerator.generateStockStream(stockLabel, numOfEvents, changeIndex);
        assertEquals(numOfEvents, stream.size());

        stream = EventStreamGenerator.generateStockStream(numOfEvents, changeIndex);
        assertEquals(numOfEvents, stream.size());
    }

    @Test
    public void rightStockLabel() {
        List<Object> stream = EventStreamGenerator.generateStockStream(stockLabel, numOfEvents, changeIndex);
        for (Object o : stream) {
            Stock s = (Stock) o;
            assertEquals(stockLabel, s.getLabel());
        }
    }

    @Test
    public void priceInterval() {
        List<Object> stream = EventStreamGenerator.generateStockStream(stockLabel, numOfEvents, changeIndex);
        for (Object o : stream) {
            Stock s = (Stock) o;
            assertThat(s.getPrice(), allOf(greaterThanOrEqualTo(EventStreamGenerator.MIN_PRICE),
                    lessThanOrEqualTo(EventStreamGenerator.MAX_PRICE)));
        }
        stream = EventStreamGenerator.generateStockStream(numOfEvents, changeIndex);
        for (Object o : stream) {
            Stock s = (Stock) o;
            assertThat(s.getPrice(), allOf(greaterThanOrEqualTo(EventStreamGenerator.MIN_PRICE),
                    lessThanOrEqualTo(EventStreamGenerator.MAX_PRICE)));
        }
    }

    @Test
    public void differentDefaultLabels() {
        List<Object> stream1 = EventStreamGenerator.generateStockStream(1, changeIndex);
        List<Object> stream2 = EventStreamGenerator.generateStockStream(1, changeIndex);
        Stock s1 = (Stock) stream1.get(0);
        Stock s2 = (Stock) stream2.get(0);
        assertNotEquals(s1.getLabel(), s2.getLabel());
    }
}
