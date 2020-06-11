package geotweets.presentation;

import geotweets.controller.HW1GeoTweetController;
import geotweets.records.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class HW1CommandLineUI {
    private HW1GeoTweetController controller;
    private boolean quit = false;
    private int program;
    private String search;
    private static final int NUM_CHOICES = 4;
    Scanner sc;

    /**
     * Constructor that passes command line arguments to the constructor.
     * @param tweetFilename
     * @param statesFilename
     */
    public HW1CommandLineUI(String tweetFilename, String statesFilename, int program, String search) {
        try {
            this.program = program;
            this.search = search;
            controller = new HW1GeoTweetController(tweetFilename, statesFilename);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        sc = new Scanner(System.in);
    }

    /**
     * Start up the command line system
     */
    public void start() {
        System.out.println("Welcome to the GeoTweets program!");
        System.out.println("Now loading...");
        controller.load(); //preloads tweet and state information into memory (optional, deleting this call will just
        //make the first operation slower
        while(!quit) { //repeat until quitting time
            System.out.println();//blank line for visual flair
            menu();
        }
        sc.close();
    }

    /**
     * Main menu selection
     */
    private void menu() {
        printOptions(); //print the choices
        processSelection();

    }

    private void processSelection() {
        int selection = this.program; //get the choice
        switch(selection) {
            case 1:
                displayStateTweetCounts();
                break;
            case 2:
                displayStateHashTags();
                break;
            case 3:
                displayPhraseTimeHashtags();
                break;
            case 4:
                quit = true;
                break;
            default:
                System.out.println("Selection is not valid, try again!");
        }
    }

    /**
     * Gets a phrase and prints out each day/hour it was found along with the number of times it was found.
     */
    private void displayPhraseTimeHashtags() {
        //get phrase
        String phrase = this.search;
        Map<String, Integer> phraseTweetMap = controller.getTweetsForPhrase(phrase);
        //check if any matches
        if (phraseTweetMap.isEmpty()) {
            System.out.println("Phrase not found.");
        } else {
            phraseTweetMap
                    .entrySet()
                    .stream()
                    .sorted(comparingByKey())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new)) //referenced my stream from earlier.
                    .forEach((K, V) -> {
                        try {
                            System.out.println(convertBackDate(K) + " " + V + " times");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
        }
        quit = true;
    }

            /**
             * Take in a state name and get the most popular hashtags from that state.
             * Then display the top 10 hashtags in order from most to least common.
             */
    private void displayStateHashTags() {
        String stateName = this.search; //get a state from the user
        if (!controller.isAStateName(stateName)) { //if the state doesn't exist
            System.out.println("Invalid state name. Returning to the main menu.");
            return; //end function call
        }
        //get the hash tag counts from the state
        Map<String, Integer> hashTagMap = controller.getHashTagsForState(stateName);
        //create an intermediate list for sorting purposes
        ArrayList<HashTagCount> hashTagCounts= new ArrayList<>();

        for (String hashTag : hashTagMap.keySet()) {
            hashTagCounts.add(new HashTagCount(hashTag, hashTagMap.get(hashTag)));
        }
        Collections.sort(hashTagCounts);

        for (int i = 0; i < Math.min(10, hashTagCounts.size()); i++) {
            HashTagCount htc = hashTagCounts.get(i);
            System.out.println((i + 1) + ". " + htc.getHashTag() + " - " + htc.count);
        }
        quit = true;
    }

    /**
     * Prompts user for a phrase to search for.
     * @return
     */
    /*
    private String getPhraseEntry() {
        System.out.println("Enter a phrase to search: ");
        return sc.nextLine().trim();
    }

     */
    /**
     * Prompts the user for a state name
     * @return
     */
    /*
    private String getStateEntry() {
        System.out.println("Enter the name of a state: ");
        return sc.nextLine().trim();
    }
*/
    /**
     * Prints out states in order of most tweets.
     */
    private void displayStateTweetCounts() { //https://javarevisited.blogspot.com/2017/09/java-8-sorting-hashmap-by-values-in.html
        Map<State, Integer> stateTweetMap = controller.getStateTweetCounts();
        AtomicInteger x = new AtomicInteger(1); //used for counting
        stateTweetMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) //sort keys in reverse order.
                .forEach((K) -> {
                    System.out.println(x.getAndIncrement() + ". " + K.getKey().getName() + " " + K.getValue());
                });
        quit = true;
        return;
    }
    /**
     * Simply prints the options available to the user.
     */
    private void printOptions() {
        System.out.println("Please make your selection:");
        System.out.println("1. Show the number of tweets per state.");
        System.out.println("2. Show the hashtags for a particular state.");
        System.out.println("3. Show the frequency of a particular word/phrase.");
        System.out.println("4. Quit the system");
    }

    /**
     * Tries to get an int selection for the menu from the user. If the user input is invalid, reprompts
     * @return
     */
   /* private int getUserSelection() {
        boolean hasValidSelection = false;
        int selection = -1;
        do {
            //remove whitespace around it
            String input = sc.nextLine().trim();
            try {
                selection = Integer.parseInt(input);
                if (selection > 0 && selection <= NUM_CHOICES) {
                    hasValidSelection = true; //entered a valid number between 1 up to and including NUM_CHOICES
                } else {
                    System.out.println("User error: Must enter a number 1 to " + NUM_CHOICES + "! Try again!");
                }
            } catch (NumberFormatException e) {
                System.out.println("User error: must enter a number! Try again!");
            }

        } while(!hasValidSelection); //keep going until the selection is valid
        return selection;
    }
*/
    /**
     * Takes in the date format that was used to sort and converts it back to the original format.
     * @param oldDate The date format used to sort
     * @return The original date format.
     * @throws ParseException If parse fails, Exit.
     */
    private String convertBackDate(String oldDate) throws ParseException {
        String oldFormat = "yyyy-MM-dd'T'HH':'mm";
        String newFormat = "yyyy-MM-dd HH':'mm"; //https://stackoverflow.com/questions/3469507/how-can-i-change-the-date-format-in-java
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date d = sdf.parse(oldDate);
        sdf.applyPattern(newFormat);
        String newDate = sdf.format(d);
        return newDate;
    }
    /**
     * Private class used to sort state-HashTagCount pairs in descending order
     */
    private static class HashTagCount implements Comparable<HashTagCount> {
        private String hashTag;
        private int count;

        private HashTagCount(String hashTag, int count) {
            this.hashTag = hashTag;
            this.count = count;
        }

        @Override
        public int compareTo(HashTagCount o) {
            return o.count - this.count;
        }

        public String getHashTag() {
            return hashTag;
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * Private class used to sort state-tweetCount pairs in descending order
     */
    private static class StateTweetCount implements Comparable<StateTweetCount> {
        private State state;
        private int count;
        private StateTweetCount(State state, int count) {
            this.state = state;
            this.count = count;
        }

        @Override
        public int compareTo(StateTweetCount o) {
            return o.getCount() - this.getCount();
        }

        public State getState() {
            return state;
        }

        public int getCount() {
            return count;
        }
    }
}
