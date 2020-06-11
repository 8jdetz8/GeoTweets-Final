package geotweets.data.tweets;

import geotweets.records.Coordinate;
import geotweets.records.Tweet;

import java.io.*;
import java.util.ArrayList;

public class HW1TweetReader extends TweetReader {
    private String tweetFilename; //tweet file name

    //constructor
    public HW1TweetReader(String tweetFilename) {
        this.tweetFilename = tweetFilename;
    }

    /**
     * This function reads the text file passed in through the constructor and populates the parent
     * 'tweets' ArrayList
     */
    @Override
    protected void readTweets() {
        tweets = new ArrayList<>();
        try {
            //open a buffered reader for the file
            BufferedReader br = new BufferedReader(new FileReader(new File(tweetFilename)));

            //read the first line
            String line = br.readLine();
            while (line != null) { //loop until the end of the fine
                String tweetText = line;
                if (tweetText.length() < 10) { //given the format of the tweet, we can safely assume this is a blank line we want to ignore
                    continue;
                }
                //handles multi-line tweets
                boolean tweetContinuation = true;
                while (tweetContinuation) {
                    line = br.readLine();
                    if (line == null || line.startsWith("[")) {
                        tweetContinuation = false;
                    } else {
                        tweetText += "\n" + line;
                    }
                }

                Tweet newTweet = readLine(tweetText);

                // add to output list and move to next line
                tweets.add(newTweet);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Tweet readLine(String tweetText) {
        String[] ls = tweetText.split("\t"); //split on tab

        //handle coordinates which look like [1.2345, 5.67890]
        String[] coordinates = ls[0].substring(1, ls[0].length()-1).split(","); //remove brackets and split on comma
        double latitude = Double.parseDouble(coordinates[0].trim());
        double longitude = Double.parseDouble(coordinates[1].trim());

        //build the coordinates object
        Coordinate c = new Coordinate(latitude, longitude);

        //get the timestamp and tweet text
        String timeStamp = ls[2].trim();
        String text = ls[3].trim();

        //build the tweet object
        return new Tweet(c, timeStamp, text);
    }
}
