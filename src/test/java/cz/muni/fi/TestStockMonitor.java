package cz.muni.fi;

import cz.muni.fi.event.Stock;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaculik on 24.10.15.
 */
public class TestStockMonitor {

    private StockMonitor monitor;

    @Before
    public void init() {
        monitor = new StockMonitor();
    }

    @After
    public void close() {
        monitor.destroyServiceProvider();
    }

    @Test
    public void streamWithoutAlertStock() {
        monitor.addStream(createDefaultStream("A"));
        monitor.start(0);

        assertEquals(0, monitor.getStockAlertResults().getNumOfResults());
    }

    @Test
    public void stockOutsideUpperLimit() {
        List<Object> stream = createDefaultStream("A");
        stream.add(new Stock(400, "A"));

        monitor.addStream(stream);
        monitor.start(0);

        assertEquals(1, monitor.getStockAlertResults().getNumOfResults());
    }

    @Test
    public void stockOutsideLowerLimit() {
        List<Object> stream = createDefaultStream("A");
        stream.add(new Stock(20, "A"));

        monitor.addStream(stream);
        monitor.start(0);

        assertEquals(1, monitor.getStockAlertResults().getNumOfResults());
    }

    @Test
    public void twoAlertsWithoutCausality() {
        List<Object> streamA = createDefaultStream("A");
        List<Object> streamB = createDefaultStream("B");
        streamA.add(new Stock(400, "A"));
        streamA.add(new Stock(500, "A"));

        monitor.addStream(streamA);
        monitor.addStream(streamB);
        monitor.start(0);

        assertEquals(2, monitor.getStockAlertResults().getNumOfResults());
        assertEquals(0, monitor.getAlertCausalityResults().getNumOfResults());
    }

    @Test
    public void twoAlertsWithCausality() {
        List<Object> streamA = createDefaultStream("A");
        List<Object> streamB = createDefaultStream("B");
        streamA.add(new Stock(400, "A"));
        streamA.add(new Stock(400, "B"));

        monitor.addStream(streamA);
        monitor.addStream(streamB);
        monitor.start(0);

        assertEquals(2, monitor.getStockAlertResults().getNumOfResults());
        assertEquals(1, monitor.getAlertCausalityResults().getNumOfResults());
    }

    private List<Object> createDefaultStream(String label) {
        List<Object> s = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            s.add(new Stock(200 + 5 * i, label));
        }
        return s;
    }

}
