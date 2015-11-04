package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.EventStreamGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vaculik on 21.10.15.
 */
public class StockAlertListener implements UpdateListener {

    private ResultsListener results;
    private static final Logger logger = LoggerFactory.getLogger(StockAlertListener.class);

    public StockAlertListener(ResultsListener results) {
        this.results = results;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        double avgPrice = EventStreamGenerator.roundDoubleTwoDecimal(
                Double.parseDouble(newEvents[0].get("avgPrice").toString()));
        logger.info("STOCK ALERT: label: " + newEvents[0].get("label") + "\tprice: "
                + newEvents[0].get("price") + "\tavgPrice: " + avgPrice);
        results.addResult(newEvents[0]);
    }
}
