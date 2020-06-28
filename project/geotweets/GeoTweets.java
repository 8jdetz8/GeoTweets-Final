//Revisions done by #jde4zt
package geotweets;

import geotweets.presentation.HW1CommandLineUI;

import java.io.File;

public class GeoTweets {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Invalid Arguments - you must have at least two for the Tweet and State filenames!");
            System.exit(0);
        }
        else
        {
            HW1CommandLineUI clui = new HW1CommandLineUI(args[0], args[1], Integer.parseInt((args[2])), args[3]);
            //make sure files exist
            File t = new File(args[0]), s = new File(args[1]);
            if (!t.exists()) {
                System.out.println("Tweet File does not exist. Correct your arguments test and try again!");
            } else if (!s.exists()) {
                System.out.println("State File does not exist. Correct your arguments and try again!");
            } else {
                clui.start();
            }
        }
        /*
        else {
            HW1CommandLineUI clui = new HW1CommandLineUI(args[0], args[1], Integer.parseInt((args[2])), args[3]);
            //make sure files exist
            File t = new File(args[0]), s = new File(args[1]);
            if (!t.exists()) {
                System.out.println("Tweet File does not exist. Correct your arguments and try again!");
            } else if (!s.exists()) {
                System.out.println("State File does not exist. Correct your arguments and try again!");
            } else {
                clui.start();
            }
        } */
    }
}
