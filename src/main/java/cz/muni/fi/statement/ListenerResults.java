package cz.muni.fi.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vaculik on 24.10.15.
 */
public class ListenerResults {

    private static final Logger logger = LoggerFactory.getLogger(ListenerResults.class);
    private Set<Object> results = new HashSet<>();

    public void addResult(Object r) {
        logger.debug("Add result, actual result size is " + results.size() + ".");
        results.add(r);
    }

    public Set<Object> getAllResults() {
        return Collections.unmodifiableSet(results);
    }

    public int getNumOfResults() {
        return results.size();
    }
}
