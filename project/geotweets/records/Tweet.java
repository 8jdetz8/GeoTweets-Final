package geotweets.records;

import java.util.ArrayList;

public class Tweet {
    private Coordinate coordinates; //location where the tweet originates
    private String timeStamp; //The timestamp of when the tweet was sent
    private String text; //the content of the tweet
    private ArrayList<String> hashTagsMemo; //a memo of hashtags, populated after the first call to getHashTags

    //constructor
    public Tweet(Coordinate coordinates, String timeStamp, String text) {
        this.coordinates = coordinates;
        this.timeStamp = timeStamp;
        this.text = text;
        hashTagsMemo = null;
    }
    //simple getters
    public Coordinate getCoordinates() {
        return coordinates;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String text() {
        return text;
    }

    /**
     * Get the hashtags from a tweet. This uses a memo ArrayList to store so this function needs to parse a string
     * only once. After the first time parsing hashTags, it stores the hashTags in memory to be recalled later.
     * @return
     */
    public ArrayList<String> getHashTags() {
        //if we have populated hashTagsMemo before
        if (hashTagsMemo != null) {
            return hashTagsMemo;
        }
        //Otherwise, initialize the memo and populate it
        hashTagsMemo = new ArrayList<>();
        String[] textSplit = text.split("\\s+");
        for (String s : textSplit) { //for each token
            if (s.startsWith("#")) {//if a hashtag
                hashTagsMemo.add(s);
            }
        }
        return hashTagsMemo;
    }
}
