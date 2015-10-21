package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by vaculik on 21.10.15.
 */
public class AlertCausalityListener implements UpdateListener {

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        System.out.println("CAUSALITY FOUND: firstStock: " + newEvents[0].get("firstLabel") +
                " secondStock: " + newEvents[0].get("secondLabel"));
    }
}
