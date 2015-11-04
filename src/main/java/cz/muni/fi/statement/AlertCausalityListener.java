package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vaculik on 21.10.15.
 */
public class AlertCausalityListener implements UpdateListener {

    private static final Logger logger = LoggerFactory.getLogger(AlertCausalityListener.class);
    private ResultsListener results;

    public AlertCausalityListener(ResultsListener results) {
        this.results = results;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        logger.info("CAUSALITY FOUND: firstStock: " + newEvents[0].get("firstLabel") +
                ", secondStock: " + newEvents[0].get("secondLabel"));
        results.addResult(newEvents[0]);
    }

}
