package cz.muni.fi;

import cz.muni.fi.event.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vaculik on 5.10.15.
 */
public class StockMonitor {

    private static final Logger logger = LoggerFactory.getLogger(StockMonitor.class);
    private static final long DEFAULT_DELAY = 50L;


    public StockMonitor() {

    }

    public void addStream(List<Object> stream) {

    }

    public void start() {

    }

    private static class StreamsContainer {

        private static final Logger logger = LoggerFactory.getLogger(StreamsContainer.class);
        private List<List<Object>> streams;
        private static int iterationIndex = 0;

        public StreamsContainer() {
            streams = new ArrayList<List<Object>>();
        }

        public void addStream(List<Object> stream) {
            if (stream == null) {
                logger.warn("Stream parameter is null.");
                return;
            }
            if (stream.isEmpty()) {
                logger.warn("Stream parameter is empty.");
                return;
            }
            streams.add(stream);
        }

        public boolean hasNextEvent() {
            return !streams.isEmpty();
        }

        public Object getNextEvent() {
            return null;
        }
    }
}
