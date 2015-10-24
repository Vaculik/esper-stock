package cz.muni.fi.statement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vaculik on 24.10.15.
 */
public class ListenerResults {

    private Set<Object> results = new HashSet<>();

    public void addResult(Object r) {
        results.add(r);
//        System.out.println("ADD RESULT: numOfResults " + results.size());
    }

    public Set<Object> getAllResults() {
        return Collections.unmodifiableSet(results);
    }

    public int getNumOfResults() {
        return results.size();
    }
}
