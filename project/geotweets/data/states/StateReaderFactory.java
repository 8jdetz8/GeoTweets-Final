/**
 * This class is responsible for giving the user the correct StateReader for their input filename.
 */

package geotweets.data.states;

public class StateReaderFactory {
    /**
     * Factory method - takes in a file and returns the corresponding StateReader for that file
     * @param filename - the name of the input file, such as "states.csv" or "States.xlsx"
     * @return a StateReader object
     */
    public StateReader getStateReader(String filename) {
        if (filename.endsWith("csv")) {
            return new CSVStateReader(filename);
        }
        else {
            return null; //nvalid file name - return null
        }
    }
}
