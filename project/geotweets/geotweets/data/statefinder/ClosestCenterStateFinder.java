/**
 * State Finder class that finds the closest state using euclidean distance to the nearest state center.
 */

package geotweets.data.statefinder;

import geotweets.records.Coordinate;
import geotweets.records.State;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ClosestCenterStateFinder implements StateFinder {
    private List<State> states;

    //Constructor takes in a list of states to compare against
    public ClosestCenterStateFinder(List<State> states) {
        if (states == null || states.size() == 0) {
            throw new IllegalArgumentException("Error: StateFinder input ArrayList may not be empty or null.");
        }
        this.states = states;
    }

    @Override
    //Finds the nearest state
    public State findState(Coordinate c) {
        State closest;
        closest = states
                .stream() //https://howtodoinjava.com/java8/java-stream-min/
                .min(Comparator.comparing(state -> c.euclideanDistanceTo(state.getCoordinates())))
                .get();
            return closest;
    }


}
