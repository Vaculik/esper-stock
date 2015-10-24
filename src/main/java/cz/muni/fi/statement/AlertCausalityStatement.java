package cz.muni.fi.statement;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

import java.util.Iterator;

/**
 * Created by vaculik on 21.10.15.
 */
public class AlertCausalityStatement {
    private EPStatement statement;
    private static final float TIME_INTERVAL = 1;

    public AlertCausalityStatement(EPServiceProvider provider) {
        String expr = "select s.label as firstLabel, a.label as secondLabel " +
                "from pattern[every s=StockAlert -> a=StockAlert(s.label != label) " +
                "where timer:within(" + TIME_INTERVAL + " sec)]";

        statement = provider.getEPAdministrator().createEPL(expr);
    }

    public void addListener(UpdateListener listener) {
        statement.addListener(listener);
    }

    public Iterator<UpdateListener> getAllListeners() {
        return statement.getUpdateListeners();
    }
}
