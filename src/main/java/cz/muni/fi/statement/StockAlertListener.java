package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.EventStreamGenerator;

/**
 * Created by vaculik on 21.10.15.
 */
public class StockAlertListener implements UpdateListener {

    private ListenerResults results;

    public StockAlertListener(ListenerResults results) {
        this.results = results;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        double avgPrice = EventStreamGenerator.roundDoubleTwoDecimal(
                Double.parseDouble(newEvents[0].get("avgPrice").toString()));
        System.out.println("STOCK ALERT: label: " + newEvents[0].get("label") + " price: "
                + newEvents[0].get("price") + " avgPrice: " + avgPrice);
        results.addResult(newEvents[0]);
    }
}
