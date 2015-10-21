package cz.muni.fi;

import com.espertech.esper.client.*;
import com.espertech.esper.client.scopetest.SupportUpdateListener;
import cz.muni.fi.event.Stock;
import cz.muni.fi.statement.AlertCausalityListener;
import cz.muni.fi.statement.AlertCausalityStatement;
import cz.muni.fi.statement.StockAlertListener;
import cz.muni.fi.statement.StockAlertStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vaculik on 5.10.15.
 */
public class StockMonitor {

    private static final Logger logger = LoggerFactory.getLogger(StockMonitor.class);
    private static final long DEFAULT_DELAY = 50L;
    private StreamsContainer streamsContainer = new StreamsContainer();
    private EPServiceProvider serviceProvider;


    public StockMonitor() {
        Configuration config = new Configuration();
        config.addEventType("Stock", Stock.class);
        serviceProvider = EPServiceProviderManager.getProvider(StockMonitor.class.getName(), config);
        setStatements();
    }

    public void addStream(List<Object> stream) {
        streamsContainer.addStream(stream);
    }

    public void start() {
        EPRuntime runtime = serviceProvider.getEPRuntime();

        while(streamsContainer.hasNextEvent()) {
            runtime.sendEvent(streamsContainer.getNextEvent());
            try {
                Thread.sleep(DEFAULT_DELAY);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when was processing event streams", e);
            }
        }
    }

    private void setStatements() {
        new StockAlertStatement(serviceProvider).addListener(new StockAlertListener());
        new AlertCausalityStatement(serviceProvider).addListener(new AlertCausalityListener());
    }
}
