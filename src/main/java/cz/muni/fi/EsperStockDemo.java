package cz.muni.fi;

import cz.muni.fi.event.Stock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public class EsperStockDemo
{
    public static void main( String[] args )
    {
        int numOfStocks = 100;
        int changeIndex = 10;

        if (args.length == 2) {
            int arg1, arg2;

            try {
                arg1 = Integer.parseInt(args[0]);
                arg2 = Integer.parseInt(args[1]);
            } catch(NumberFormatException ex) {
                System.out.println(ex);
                printHelp();
                return;
            }
            if (arg1 <= 0 || arg2 <= 0) {
                printHelp();
                return;
            }
            numOfStocks = arg1;
            changeIndex = arg2;
        } else if (args.length != 0) {
            printHelp();
            return;
        }

        List<Object> stream1 = EventStreamGenerator.generateStockStream(numOfStocks, changeIndex);
        List<Object> stream2 = EventStreamGenerator.generateStockStream(numOfStocks, changeIndex);

        StreamsContainer streams = new StreamsContainer();
        streams.addStream(stream1);
        streams.addStream(stream2);

        while (streams.hasNextEvent()) {
            System.out.println(streams.getNextEvent());
        }

//        for (Object stock : stream1) {
//            Stock s = (Stock) stock;
//            System.out.println(s.toString());
//        }
//        for (Object stock : stream2) {
//            Stock s = (Stock) stock;
//            System.out.println(s.toString());
//        }

    }

    private static void printHelp() {
        System.out.println("PRINT HELP");
    }
}
