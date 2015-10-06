package cz.muni.fi;

import cz.muni.fi.event.Stock;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vaculik on 2.10.15.
 */
public final class EventStreamGenerator {

    private static Random random = new Random(System.currentTimeMillis());
    private static int labelNumber = 1;
    private static final String DEFAULT_LABEL = "STOCK";


    private EventStreamGenerator() {
    }


    public static List<Object> generateStockStream(int numOfEvents, double minPrice, double maxPrice, int changeIndex) {
        return generateStockStream(getDefaultLabel(), numOfEvents, minPrice, maxPrice, changeIndex);
    }


    public static List<Object> generateStockStream(String stockLabel, int numOfEvents,
                                                         double minPrice, double maxPrice, int changeIndex) {
        List<Object> eventStream = new LinkedList<Object>();
        double price = minPrice + (maxPrice - minPrice) * random.nextDouble();
        double priceChange = random.nextDouble() * changeIndex;
        int signOfChange = random.nextBoolean() ? 1 : -1;

        eventStream.add(new Stock(roundPrice(price), stockLabel));

        for(int i = 1; i < numOfEvents; i++) {
            signOfChange *= random.nextInt(4) == 0 ? -1 : 1;
            priceChange = priceChange * 0.5 + random.nextDouble() * changeIndex;

            price += priceChange * signOfChange;
            if (price < minPrice || price > maxPrice) {
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


    private static String getDefaultLabel() {
        return DEFAULT_LABEL + "_" + labelNumber++;
    }
}
