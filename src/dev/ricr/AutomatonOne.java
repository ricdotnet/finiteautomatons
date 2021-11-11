package dev.ricr;

import java.util.ArrayList;
import java.util.List;

public class AutomatonOne {

    String[] words = {
        "GOOGLE",
        "GOOGLLE",
        "XXOOOXXXEELLLGOOGLEEEEEXXXXLLLOOOO",
        "GOOOOOOOOOOOOOOOOOOOGGGGLLGGGOOOOOOGLEEEEEEXXXXO",
        "GLE",
        "GOOOOOGGGGLEO",
        "EGLOXGLE",
        "EGOLGLEGGLE"
    };

    /**
     * Alphabet = E, G, L, O, X
     */
//  String word = "GOOOOOOOOOOOOOOOOOOOGGGGLLGGGOOOOOOGLEEEEEEXXXXO";
    private int currState = 1;

    ArrayList<String> validWords = new ArrayList<>();
    ArrayList<String> nonValidWords = new ArrayList<>();

    private void checkWords () {

        for(String word : words) {
            for (int i = 0; i < word.length(); i++) {

                if (currState == 1) {
                    if (word.charAt(i) == 'G') {
                        increaseState();
                    }
                } else if (currState == 2) {
                    if (word.charAt(i) == 'G') {
                        increaseState();
                    } else if (word.charAt(i) != 'O') {
                        returnToStart();
                    }
                } else if (currState == 3) {
                    if (word.charAt(i) == 'O') {
                        decreaseState();
                    } else if (word.charAt(i) == 'E' || word.charAt(i) == 'X') {
                        returnToStart();
                    } else if (word.charAt(i) == 'L') {
                        increaseState();
                    }
                } else if (currState == 4) {
                    if (word.charAt(i) == 'E') {
                        increaseState();
                    } else {
                        returnToStart();
                    }
                }

            }

            if (currState == 5) {
//        System.out.println("Valid word!!!!");
                validWords.add(word);
            } else {
//        System.out.println("Word not valid.... :(");
                nonValidWords.add(word);
            }

            // reset the state after each word
            returnToStart();
        }

        // print both list of words
        System.out.println("Valid words: " + printValidWords());
        System.out.println("Non valid words: " + printNonValidWords());

    }

    private void increaseState () {
        currState++;
    }

    private void decreaseState () {
        currState--;
    }

    private void returnToStart () {
        currState = 1;
    }

    private List<String> printValidWords() {
        return validWords;
    }

    private List<String> printNonValidWords() {
        return nonValidWords;
    }

    public static void main (String[] args) {
        AutomatonOne main = new AutomatonOne();
        main.checkWords();
    }

}
