package dev.ricr;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

/**
 * Alphabet = E, G, L, O, X
 * <p>
 * This DFA machine should be able to match / verify strings of the alphabet (shown above) such that
 * word has a sub-word that starts with G and ends with GLE. This sub-word should also contain any number of O's
 * including no O's in between.
 * <p>
 * Example: GLE and EXGGLLE would not be accepted but GOOGLE and GGLE would be.
 */

public class AutomatonOne {

  private int currState = 1;

  ArrayList<String> wordsBuffer = new ArrayList<>();
  ArrayList<String> validWords = new ArrayList<>();
  ArrayList<String> nonValidWords = new ArrayList<>();

  private void checkWords () {

    for (String word : wordsBuffer) {
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

  private List<String> printValidWords () {
    return validWords;
  }

  private List<String> printNonValidWords () {
    return nonValidWords;
  }

  private void importFile () {
    File file = new File(System.getProperty("user.dir") + "/words.txt");

    if (!file.exists()) {
      System.out.println("The file you are trying to read does not exist.");
      return;
    }

    readFile(file);
  }

  private void readFile (File file) {
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        wordsBuffer.add(scanner.nextLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main (String[] args) {
    AutomatonOne main = new AutomatonOne();
    main.importFile();
    main.checkWords();
  }

}
