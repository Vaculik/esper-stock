package cz.muni.fi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by vaculik on 9.10.15.
 */
class StreamsContainer {

    private static final Logger logger = LoggerFactory.getLogger(StreamsContainer.class);
    private List<List<Object>> streams;
    private int iterationIndex = 0;

    public StreamsContainer() {
        streams = new ArrayList<>();
    }

    public void addStream(List<Object> stream) {
        if (stream == null) {
            String msg = "Stream parameter is null.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }
        if (stream.isEmpty()) {
            String msg = "Stream parameter is empty.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }
        streams.add(stream);
    }

    public boolean hasNextEvent() {
        return !streams.isEmpty();
    }

    public Object getNextEvent() {
        if (!hasNextEvent()) {
            String msg = "StreamsContainer is empty.";
            logger.warn(msg);
            throw new NoSuchElementException(msg);
        }

        if (iterationIndex >= streams.size()) {
            iterationIndex = 0;
        }

        Object event = streams.get(iterationIndex).remove(0);
        if (streams.get(iterationIndex).isEmpty()) {
            streams.remove(iterationIndex);
        }

        iterationIndex++;
        return event;
    }
}
