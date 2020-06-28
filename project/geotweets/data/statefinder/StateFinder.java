/**
 * Abstract definition of closest state finder. This will allow us to update how this is done later.
 */

package geotweets.data.statefinder;

import geotweets.records.Coordinate;
import geotweets.records.State;

import java.util.Optional;

public interface StateFinder {
    public abstract State findState(Coordinate c);
}
