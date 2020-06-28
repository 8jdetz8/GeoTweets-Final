/**
 * This class implements StateReader by reading in a CSV file
 */

package geotweets.data.states;

import geotweets.records.Coordinate;
import geotweets.records.State;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVStateReader extends StateReader {
    private String stateFilename; //csv filename

    public CSVStateReader(String stateFilename) {
        super();
        this.stateFilename = stateFilename;
    }

    /**
     * Initializes and populates the parent "states" ArrayList
     */
    @Override
    protected void readStates() {  //https://mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
        states = new ArrayList<State>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(stateFilename)));
            states = br
                    .lines()
                    .map(line -> Arrays.asList(line.split(",")))
                    .map(list -> {String stateName = list.get(0).trim();
                    Double lat = Double.parseDouble(list.get(1));
                    Double longi = Double.parseDouble(list.get(2));
                    Coordinate cord = new Coordinate(lat, longi);
                    State newState = new State(stateName, cord); //create the new state item.
                    return newState; })
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
