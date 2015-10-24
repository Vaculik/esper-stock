package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.event.Stock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vaculik on 21.10.15.
 */
public class AlertCausalityListener implements UpdateListener {

    private ListenerResults results;

    public AlertCausalityListener(ListenerResults results) {
        this.results = results;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        System.out.println("CAUSALITY FOUND: firstStock: " + newEvents[0].get("firstLabel") +
                " secondStock: " + newEvents[0].get("secondLabel"));
        results.addResult(newEvents[0]);
    }

}
