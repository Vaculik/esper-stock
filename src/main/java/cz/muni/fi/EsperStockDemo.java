package cz.muni.fi;

import cz.muni.fi.event.Stock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class EsperStockDemo
{
    public static void main( String[] args )
    {
        int numOfStocks = 100;
        int minPrice = 10;
        int maxPrice = 250;
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
        } else if (args.length == 4) {
            int[] arguments = new int[4];

            try {
                for (int i = 0; i < 4; i++) {
                    arguments[i] = Integer.parseInt(args[i]);
                }
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                printHelp();
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (arguments[i] <= 0) {
                    printHelp();
                    return;
                }
            }
            if (arguments[2] >= arguments[3]) {
                printHelp();
                return;
            }
            numOfStocks = arguments[0];
            changeIndex = arguments[1];
            minPrice = arguments[2];
            maxPrice = arguments[3];
        } else if (args.length != 0) {
            printHelp();
            return;
        }

//        List<Object> stream1 = EventStreamGenerator.generateStockStream(numOfStocks, minPrice, maxPrice, changeIndex);
//        List<Object> stream2 = EventStreamGenerator.generateStockStream(numOfStocks, minPrice, maxPrice, changeIndex);
//
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
