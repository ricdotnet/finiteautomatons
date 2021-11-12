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
 * <p>
 * So far there are two ways here, if statements and switch cases.
 * Edit lines 35-36 to swap between methods.
 */
public class AutomatonOne {

  private int currState = 1;

  ArrayList<String> wordsBuffer = new ArrayList<>();
  ArrayList<String> validWords = new ArrayList<>();
  ArrayList<String> nonValidWords = new ArrayList<>();

  /**
   * Main Loop
   */
  private void mainLoop () {
    for (String word : wordsBuffer) {
      for (int i = 0; i < word.length(); i++) {

//        checkWordsIf(word.charAt(i));
        checkWordsSwitch(word.charAt(i));
      }

      if (currState == 5) {
        validWords.add(word);
      } else {
        nonValidWords.add(word);
      }

      returnToStart();
    }
  }

  /**
   * For Loop Example
   */
  private void checkWordsIf (char next) {
    if (currState == 1) {
      if (next == 'G') {
        increaseState();
      }
    } else if (currState == 2) {
      if (next == 'G') {
        increaseState();
      } else if (next != 'O') {
        returnToStart();
      }
    } else if (currState == 3) {
      if (next == 'O') {
        decreaseState();
      } else if (next == 'E' || next == 'X') {
        returnToStart();
      } else if (next == 'L') {
        increaseState();
      }
    } else if (currState == 4) {
      if (next == 'E') {
        increaseState();
      } else {
        returnToStart();
      }
    }
  }


  /**
   * Switch Case Example
   */
  private void checkWordsSwitch (char next) {
    switch (currState) {
      case 1:
        switch (next) {
          case 'G':
            increaseState();
            break;
        }
        break;
      case 2:
        switch (next) {
          case 'G':
            increaseState();
            break;
          case 'O':
            break;
          default:
            returnToStart();
            break;
        }
        break;
      case 3:
        switch (next) {
          case 'G':
            break;
          case 'O':
            decreaseState();
            break;
          case 'L':
            increaseState();
            break;
          default:
            returnToStart();
            break;
        }
        break;
      case 4:
        switch (next) {
          case 'E':
            increaseState();
            break;
          default:
            returnToStart();
            break;
        }
        break;
      default:
        //stay where it is
    }
  }


  /**
   * State Modifiers
   */
  private void increaseState () {
    currState++;
  }

  private void decreaseState () {
    currState--;
  }

  private void returnToStart () {
    currState = 1;
  }


  /**
   * Final Lists
   */
  private List<String> printValidWords () {
    return validWords;
  }

  private List<String> printNonValidWords () {
    return nonValidWords;
  }


  /**
   * IO Helpers
   */
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


  /**
   * Run It!!
   */
  public static void main (String[] args) {
    AutomatonOne main = new AutomatonOne();
    main.importFile();
//    main.checkWordsForLoop();
//    main.checkWordsSwitch();
    main.mainLoop();

    // print both list of words
    System.out.println("Valid words: " + main.printValidWords());
    System.out.println("Non valid words: " + main.printNonValidWords());
  }
}
