package cz.muni.fi;

import cz.muni.fi.event.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vaculik on 2.10.15.
 */
public final class EventStreamGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EventStreamGenerator.class);
    private static Random random = new Random(System.currentTimeMillis());
    private static int labelNumber = 1;
    private static final String BASE_LABEL = "STOCK";
    public static double MIN_PRICE = 10;
    public static double MAX_PRICE = 500;


    private EventStreamGenerator() {
    }


    public static List<Object> generateStockStream(int numOfEvents, int changeIndex) {
        return generateStockStream(getBaseLabel(), numOfEvents, changeIndex);
    }


    public static List<Object> generateStockStream(String stockLabel, int numOfEvents, int changeIndex) {
        if (stockLabel == null) {
            String msg = "Parameter stockLabel is null.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }
        if (numOfEvents <= 0) {
            String msg = "Parameter numOfEvents is not positive.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }
        if (changeIndex < 0) {
            String msg = "Parameter changeIndex is negative.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        List<Object> eventStream = new LinkedList<Object>();
        double price = MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble();
        double priceChange = random.nextDouble() * changeIndex;
        int signOfChange = random.nextBoolean() ? 1 : -1;

        eventStream.add(new Stock(roundPrice(price), stockLabel));

        for(int i = 1; i < numOfEvents; i++) {
            signOfChange *= random.nextInt(4) == 0 ? -1 : 1;
            priceChange = priceChange * 0.5 + random.nextDouble() * changeIndex;

            price += priceChange * signOfChange;
            if (price < MIN_PRICE || price > MAX_PRICE) {
                signOfChange *= -1;
                price += 2 * priceChange * signOfChange;
            }

            eventStream.add(new Stock(roundPrice(price), stockLabel));
        }

        return eventStream;
    }

    private static double roundPrice(double value) {
        value = Math.round(value * 100);
        return value / 100;
    }


    private static String getBaseLabel() {
        return BASE_LABEL + "_" + labelNumber++;
    }
}
