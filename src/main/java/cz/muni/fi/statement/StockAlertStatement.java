package cz.muni.fi.statement;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.EventStreamGenerator;

/**
 * Created by vaculik on 21.10.15.
 */
public class StockAlertStatement {
    private EPStatement statement;

    public StockAlertStatement(EPServiceProvider provider) {
        double range = (EventStreamGenerator.MAX_PRICE - EventStreamGenerator.MIN_PRICE) / 10;
        String expr = "insert into StockAlert " +
                "select label, price, avg(price) as avgPrice " +
                "from Stock.std:groupwin(label).win:length(5) " +
                "group by label " +
                "having price > avg(price) + " + range + " or price < avg(price) - " + range;

        statement = provider.getEPAdministrator().createEPL(expr);
    }

    public void addListener(UpdateListener listener) {
        statement.addListener(listener);
    }
}
