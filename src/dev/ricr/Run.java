package dev.ricr;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Alphabet = E, G, L, O, X
 * <br>
 * <br>
 * This DFA machine should be able to match / verify strings of the alphabet (shown above) such that
 * word has a sub-word that starts with G and ends with GLE. This sub-word should also contain any number of O's
 * including no O's in between.
 * <br>
 * <br>
 * Example: GLE and EXGGLLE would not be accepted but GOOGLE and GGLE would be.
 * <br>
 * <br>
 * Transitions look like:
 * q5 is final state, and if we reach q5 the word is accepted.
 * <br>
 * <br>
 * δ(q1, E,L,X,O) => q1 <br>
 * δ(q1, G) => q2 <br>
 * <br>
 * δ(q2, E,L,X) => q1 <br>
 * δ(q2, O) => q2 <br>
 * δ(q2, G) => q3 <br>
 * <br>
 * δ(q3, E,X) => q1 <br>
 * δ(q3, O) => q2 <br>
 * δ(q3, G) => q3 <br>
 * δ(q3, L) => q4 <br>
 * <br>
 * δ(q4, L,O,X) => q1 <br>
 * δ(q4, G) => q2 <br>
 * δ(q4, E) => q5
 */

public class Run {

  DfaTable dfaTable = new DfaTable();
  DfaIf dfaIf = new DfaIf();

  // Nfa example
  NfaParallel nfaParallel = new NfaParallel();
  Nfa nfa = new Nfa();

  /**
   * IO Helpers
   */
  private void importFileAndRead () {
    File file = new File(System.getProperty("user.dir") + "/words.txt");

    if (!file.exists()) {
      System.out.println("The file you are trying to read does not exist.");
      return;
    }

    readFile(file);
  }

  /**
   * Please edit lines 68 and 69 to swap between both methods.
   *
   * @param file File
   */
  private void readFile (File file) {
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String w = scanner.nextLine();
//        String result = dfaTable.checkWord(w);
//        String result = dfaIf.checkWord(w);
//        String result = nfaParallel.checkWord(w);
        String result = nfa.checkWord(w);

        System.out.println(w + ": " + result);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main (String[] args) {
    Run main = new Run();
    main.importFileAndRead();
  }

}
