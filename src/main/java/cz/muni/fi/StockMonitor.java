package cz.muni.fi;

import com.espertech.esper.client.*;
import cz.muni.fi.event.Stock;
import cz.muni.fi.statement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by vaculik on 5.10.15.
 */
public class StockMonitor {

    private static final Logger logger = LoggerFactory.getLogger(StockMonitor.class);
    private static final long DEFAULT_DELAY = 50L;
    private StreamsContainer streamsContainer = new StreamsContainer();
    private EPServiceProvider serviceProvider;
    private final ListenerResults stockAlertResults;
    private final ListenerResults alertCausalityResults;


    public StockMonitor() {
        Configuration config = new Configuration();
        config.addEventType("Stock", Stock.class);
        serviceProvider = EPServiceProviderManager.getProvider(StockMonitor.class.getName(), config);

        StockAlertStatement stockAlertStatement = new StockAlertStatement(serviceProvider);
        stockAlertResults = new ListenerResults();
        stockAlertStatement.addListener(new StockAlertListener(stockAlertResults));

        AlertCausalityStatement alertCausalityStatement = new AlertCausalityStatement(serviceProvider);
        alertCausalityResults = new ListenerResults();
        alertCausalityStatement.addListener(new AlertCausalityListener(alertCausalityResults));
    }

    public void addStream(List<Object> stream) {
        streamsContainer.addStream(stream);
    }

    public void start() {
        start(DEFAULT_DELAY);
    }

    public void start(long delay) {
        EPRuntime runtime = serviceProvider.getEPRuntime();

        while(streamsContainer.hasNextEvent()) {
//            Stock s = (Stock) streamsContainer.getNextEvent();
//            System.out.println(s);
//            runtime.sendEvent(s);
            runtime.sendEvent(streamsContainer.getNextEvent());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when was processing event streams", e);
            }
//            System.out.println("RESULT: " + stockAlertResults.getNumOfResults());
        }
    }

    public ListenerResults getStockAlertResults() {
        return stockAlertResults;
    }

    public ListenerResults getAlertCausalityResults() {
        return alertCausalityResults;
    }

    public void closeServiceProvider() {
        serviceProvider.destroy();
    }
 }
