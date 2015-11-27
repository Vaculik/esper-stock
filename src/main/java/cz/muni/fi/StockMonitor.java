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
    private static final long DEFAULT_DELAY = 100L;
    private StreamsContainer streamsContainer = new StreamsContainer();
    private EPServiceProvider serviceProvider;
    private final ResultsListener stockAlertResults;
    private final ResultsListener alertCausalityResults;


    public StockMonitor() {
        serviceProvider = EPServiceProviderManager.getProvider(StockMonitor.class.getName());

        logger.debug("Create StockAlertStatement and add appropriate listeners.");
        StockAlertStatement stockAlertStatement = new StockAlertStatement(serviceProvider);
        stockAlertResults = new ResultsListener();
        stockAlertStatement.addListener(new StockAlertListener(stockAlertResults));

        logger.debug("Create AlertCausalityStatement and add appropriate listeners.");
        AlertCausalityStatement alertCausalityStatement = new AlertCausalityStatement(serviceProvider);
        alertCausalityResults = new ResultsListener();
        alertCausalityStatement.addListener(new AlertCausalityListener(alertCausalityResults));
    }

    public void addStream(List<Object> stream) {
        logger.debug("Add stream of size " + stream.size() + ".");
        streamsContainer.addStream(stream);
    }

    public void start() {
        start(DEFAULT_DELAY);
    }

    public void start(long delay) {
        if (delay < 0) {
            String msg = "Parameter delay cannot be negative.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        logger.debug("Get EPRuntime and start processing.");
        EPRuntime runtime = serviceProvider.getEPRuntime();

        while(streamsContainer.hasNextEvent()) {
            Stock s = (Stock) streamsContainer.getNextEvent();
            logger.debug(s.toString());
            runtime.sendEvent(s);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when was processing event streams", e);
            }
        }
        logger.info("Number of StockAlert: " + stockAlertResults.getNumOfResults());
        logger.info("Number of AlertCausality: " + alertCausalityResults.getNumOfResults());
    }

    public ResultsListener getStockAlertResults() {
        return stockAlertResults;
    }

    public ResultsListener getAlertCausalityResults() {
        return alertCausalityResults;
    }

    public void destroyServiceProvider() {
        serviceProvider.destroy();
    }
 }
