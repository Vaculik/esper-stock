package cz.muni.fi;


import java.util.List;

/**
 *
 *
 */
public class EsperStockDemo
{
    private static int numOfStocks = 100;
    private static int changeIndex = 20;
    private static boolean noDelay = false;

    public static void main( String[] args )
    {
        if (args.length == 1 && args[0].equals("--help")) {
            System.out.println(getHelp());
            return;
        }
        if (!parseArgs(args)) {
            System.out.println("Invalid arguments!");
            System.out.println(getHelp());
            return;
        }

        List<Object> stream1 = EventStreamGenerator.generateStockStream(numOfStocks, changeIndex);
        List<Object> stream2 = EventStreamGenerator.generateStockStream(numOfStocks, changeIndex);

        StockMonitor monitor = new StockMonitor();
        monitor.addStream(stream1);
        monitor.addStream(stream2);
        if (noDelay) {
            monitor.start(0);
        } else {
            // Start monitor with default delay
            monitor.start();
        }
        monitor.destroyServiceProvider();
    }

    private static boolean parseArgs(String[] args) {
        if (args.length == 0) {
            return true;
        }

        if (args.length == 1) {
            return parseNoDelay(args[0]);
        }

        if (args.length == 2) {
            return parseValueArgs(args[0], args[1]);
        }

        if (args.length == 3) {
            return (parseNoDelay(args[0]) && parseValueArgs(args[1], args[2]));
        }

        return false;
    }


    private static boolean parseNoDelay(String arg) {
        if (arg.equals("-n") || arg.equals("--no-delay")) {
            noDelay = true;
            return true;
        }
        return false;
    }


    private static boolean parseValueArgs(String arg1, String arg2) {
        int value1;
        int value2;
        try {
            value1 = Integer.parseInt(arg1);
            value2 = Integer.parseInt(arg2);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (value1 <= 0 || value2 <= 0) {
            return false;
        }
        numOfStocks = value1;
        changeIndex = value2;
        return true;
    }


    private static String getHelp() {
        String help = "EsperStockDemo [-n] [value1 value2]\n\n";
        help += "Options:\n";
        help += "\t-n, --no-delay\tprocess events with no delay\n";
        help += "\tvalue1\t\t\tnumber of events, positive integer value\n";
        help += "\tvalue2\t\t\tstock price change index, positive integer value\n\n";
        help += "No arguments is equivalent to EsperStockDemo 100 20\n";
        return help;
    }
}
