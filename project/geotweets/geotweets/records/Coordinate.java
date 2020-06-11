package geotweets.records;

import static java.lang.Math.PI;

public class Coordinate {
    private double longitude, latitude;
    //constructor
    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * Euclidean distance from one coordinate to another
     * @param c
     * @return
     */
    public double euclideanDistanceTo(Coordinate c) {
        double dy = this.latitude - c.latitude;
        double dx = this.longitude - c.longitude;
        return Math.sqrt((dy * dy) + (dx * dx));
    }

    /**
     * Great circle distance from one coordinate to another
     * @param c
     * @return
     */
    public double greatCircleDistanceTo(Coordinate c) {
        double diffLong = this.longitude*PI/180 - c.longitude*PI/180;
        double diffLat = this.latitude*PI/180 - c.latitude*PI/180;

        double centralAngle = 2*Math.asin(Math.sqrt(Math.sin(diffLat/2)*(Math.sin(diffLat/2))+Math.cos(this.latitude*PI/180)*Math.cos(c.latitude*PI/180)
                *(Math.sin(diffLong/2)*(Math.sin(diffLong/2)))));
        return centralAngle;
    }
}
