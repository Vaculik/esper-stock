package cz.muni.fi;


import cz.muni.fi.event.Stock;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by vaculik on 9.10.15.
 */
public class TestStreamsContainer {

    private StreamsContainer streamsContainer;
    private static final double PRICE = 100;

    @Before
    public void init() {
        streamsContainer = new StreamsContainer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEmptyStream() {
        streamsContainer.addStream(new LinkedList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullArgument() {
        streamsContainer.addStream(null);
    }

    @Test
    public void hasNextEmptyContainer() {
        assertFalse(streamsContainer.hasNextEvent());
    }

    @Test
    public void hasNextFilledContainer() {
        streamsContainer.addStream(createStream(1, "A"));
        assertTrue(streamsContainer.hasNextEvent());
    }

    @Test(expected = NoSuchElementException.class)
    public void getEventEmptyContainer() {
        streamsContainer.getNextEvent();
    }

    @Test
    public void getElementOneStream() {
        streamsContainer.addStream(createStream(3, "A"));
        Stock expectedStock = new Stock(PRICE, "A");
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedStock, streamsContainer.getNextEvent());
        }
        assertFalse(streamsContainer.hasNextEvent());
    }

    @Test
    public void getElementTwoStreams() {
        streamsContainer.addStream(createStream(3, "A"));
        streamsContainer.addStream(createStream(3, "B"));

        Stock expectedStockA = new Stock(PRICE, "A");
        Stock expectedStockB = new Stock(PRICE, "B");
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedStockA, streamsContainer.getNextEvent());
            assertEquals(expectedStockB, streamsContainer.getNextEvent());
        }
        assertFalse(streamsContainer.hasNextEvent());
    }


    private List<Object> createStream(int numOfEvents, String stockLabel) {
        List<Object> stream = new LinkedList<Object>();

        for (int i = 0; i < numOfEvents; i++) {
            stream.add(new Stock(PRICE, stockLabel));
        }
        return stream;
    }

}
