/**
 * This class provides a menu of options to the UI layer to interact with the data layer. Effectively, each "menu choice"
 * in the UI maps to one controller function.
 */

package geotweets.controller;

import geotweets.data.statefinder.ClosestCenterStateFinder;
import geotweets.data.statefinder.ClosestCircleStateFinder;
import geotweets.data.statefinder.StateFinder;
import geotweets.data.states.StateReader;
import geotweets.data.states.StateReaderFactory;
import geotweets.data.tweets.HW1TweetReader;
import geotweets.data.tweets.TweetReader;
import geotweets.records.State;
import geotweets.records.Tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

public class HW1GeoTweetController {
    private TweetReader tweetReader; //The class responsible for getting tweets
    private StateReader stateReader; //The class responsible for getting states
    private StateFinder stateFinder; //The class responsible for finding the state for a coordinate

    //Constructor
    public HW1GeoTweetController(String tweetFilename, String stateFilename) {
        //create tweet reader
        tweetReader = new HW1TweetReader(tweetFilename);

        //create state reader
        StateReaderFactory srf = new StateReaderFactory();
        stateReader = srf.getStateReader(stateFilename);

        //create state finder
        stateFinder = new ClosestCenterStateFinder(stateReader.getStates());
    }

    /**
     * Takes in a phrase and returns a map of each time by the hour that phrase was found.
     * @param phrase Phrase user searched for
     * @return Map of date found and number of times found.
     */
    public Map<String, Integer> getTweetsForPhrase(String phrase) {
        Map<String, Integer> phraseTweetCount = new HashMap<>();
        for (Tweet t : tweetReader.getTweets()) {
            if (t.text().toLowerCase().contains(phrase.toLowerCase())) {
                String time = parseDateTime(t.getTimeStamp());
                if (phraseTweetCount.containsKey(time)) {
                    phraseTweetCount.put(time, phraseTweetCount.get(time) + 1);
                }
                else {
                    phraseTweetCount.put(time, 1);
                }
            }
        }
        return phraseTweetCount;
    }

    /**
     * Removes minutes and seconds for sorting purposes, converts to a sortable date format.
     * @param timeStamp Timestamp of the tweet passed in
     * @return A sortable date time format.
     */
    public String parseDateTime(String timeStamp) {
        timeStamp = timeStamp.replaceAll(":[0-9][0-9]:[0-9][0-9]", ":00:00");
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" ) ; //Used https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
        LocalDateTime otherTime = LocalDateTime.parse( timeStamp , f ); //to figure out how to sort by date.
        return otherTime.toString();
    }
     public Map<State, Integer> getStateTweetCounts() {
        Map<State, Integer> stateTweetCounts = new HashMap<>();
        stateTweetCounts = tweetReader.getTweets()
                .stream()
                .map(tweet -> {return stateFinder.findState(tweet.getCoordinates());})
                .collect(Collectors.groupingBy(Function.identity(), summingInt(e -> 1))); //https://stackoverflow.com/questions/29122394/word-frequency-count-java-8
        return stateTweetCounts;
    }

    public Map<String, Integer> getHashTagsForState(String stateName) {
        Map<String, Integer> hashTagCounts = new HashMap<>();
        for (Tweet t : tweetReader.getTweets()) {
            if (stateFinder.findState(t.getCoordinates()).getName().equalsIgnoreCase(stateName)) {
                List<String> hashTags = t.getHashTags();
                for (String hashTag : hashTags) {
                    if (hashTagCounts.containsKey(hashTag)) {
                        hashTagCounts.put(hashTag, hashTagCounts.get(hashTag) + 1);
                    } else {
                        hashTagCounts.put(hashTag, 1);
                    }
                }
            }
        }

        return hashTagCounts;
    }

    /**
     * Force the TweetReader and StateReader to load in their data into memory.
     */
    public void load() {
        tweetReader.getTweets();
        stateReader.getStates();
    }

    public boolean isAStateName(String s) {
        List<State> states = stateReader.getStates();
        for (State state : states) {
            if (state.getName().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
