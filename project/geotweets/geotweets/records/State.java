package geotweets.records;

public class State {
    private String name; //i.e., West Virginia
    private Coordinate coordinates; //location of state center
    //constructor
    public State (String name, Coordinate coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }
    //getters
    public String getName() {
        return name;
    }
    public Coordinate getCoordinates() {
        return coordinates;
    }
}
