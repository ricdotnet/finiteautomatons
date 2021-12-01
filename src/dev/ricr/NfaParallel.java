package dev.ricr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NfaParallel {

  private int stateSet;
  public void reset() {
    stateSet = 1<<0; // this value (1) represents the initial state set {q0}
  }

  /**
   * The transition function for the NFA is represented below as an array.
   * The transition starts at delta[state,pattern.charAt()]
   */
  private int[][] delta =
      {{1 << 0, 1<<0|1<<1},        // delta[q0, E,L,O,X] = {q0},  delta[q0, G] = {q0,q1}
          {0, 1 << 2},              // delta[q1, O] = {q1},  delta[q1, G] = {q2}
          {1 << 3, 0},                // delta[q2, L] = {q3}
          {1 << 4, 0}                 // delta[q3, E] = {q4}
      };

  public boolean acceptsWord(String in) {
    for (int i = 0; i < in.length(); i++) {

      if (stateSet == 1<<4) return true; // reaching final state, quit!!!

      char c = in.charAt(i);
      int nextSS = 0; // next state set, initially empty

      for (int s = 0; s <= 3; s++) { // for each state s
        if ((stateSet & (1<<s)) != 0) { // if maybe in s
          try {
            if (c == 'G' && (stateSet == 1<<0)) {
              nextSS |= delta[0][1];
            } else
            if (c == 'G' && (stateSet == (1<<0|1<<1) || stateSet == 1<<2)) {
              nextSS |= delta[1][1];
            } else

            if (c == 'O' && (stateSet == (1<<0|1<<1)) /*1*/) {
//              nextSS |= delta[s][0];
            } else

            if (c == 'L' && stateSet == 1<<2) {
              nextSS |= delta[s][0];
            } else

            if (c == 'E' && stateSet == 1<<3) {
              nextSS |= delta[s][0];

            } else if (c == 'O' && (stateSet == (1<<2))) { // reset back 1 state
              nextSS |= delta[0][1];
              stateSet = nextSS;
            }

            else {
              nextSS |= delta[0][0];
              reset();
            }
//            nextSS |= delta[s][c-'0'];
          } catch (ArrayIndexOutOfBoundsException ex) {
            // in effect, nextSS |= 0
            return false;
          }
        }
      }

      if (c != 'O')
        stateSet = nextSS; // new state set after c
    }

    return (stateSet & (1<<4)) != 0;
  }

  /**
   * Start checking the word, starting from state 0 and position 0.
   */
  public String checkWord (String w) {
//    return (stateSet & (1<<4)) != 0;
    this.reset();
    return acceptsWord(w) ? "valid" : "not valid";
  }

//  public static void main (String[] args) throws IOException {
//    NfaParallel main = new NfaParallel();
//
//    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//    String w = in.readLine();
//
//    main.reset();
//    main.acceptsNext(w);
//    System.out.println(main.checkWord());
//  }

}
