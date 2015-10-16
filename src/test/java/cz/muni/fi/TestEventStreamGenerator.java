package cz.muni.fi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void illegalNumOfEventsGenerateStream() {
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
    public void illegalChangeIndexGenerateStream() {
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
    public void validParametersGenerateStream() {

        // Test marginal valid values of parameters, should not throw an exception
        EventStreamGenerator.generateStockStream(stockLabel, 1, 0);
        EventStreamGenerator.generateStockStream(1, 0);
    }

}
