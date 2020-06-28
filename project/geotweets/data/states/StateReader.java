/**
 * This abstract class describes a StateReader. We create an abstract class so we can add new concrete implementors
 * in case the implementation needs to change. Each implementor defines it's own constructor and readStates method.
 */

package geotweets.data.states;

import geotweets.records.State;

import java.util.List;

public abstract class StateReader {
    protected List<State> states; //states memo

    public StateReader() {
        states = null;
    }

    public List<State> getStates() {
        if (states == null) { //if states have not been populated...
            readStates(); //populate states
        }
        return states;
    }

    /**
     * This method is implemented by child classes to initialize and populate the states list variable.
     */
    protected abstract void readStates();
}
