package dev.ricr;

public class NfaParallel {

  private int currentState;

  public void reset () {
    currentState = 1 << 0; // this value (1) represents the initial state set {q0}
  }

  /**
   * The transition function for the NFA is represented below as an array.
   * The transition starts at delta[state,pattern.charAt()]
   */
  private int[][] delta =
      {{1 << 0, 1 << 0 | 1 << 1},        // delta[q0, E,L,O,X] = {q0},  delta[q0, G] = {q0,q1}
          {0, 1 << 2},              // delta[q1, O] = {q1},  delta[q1, G] = {q2}
          {1 << 3, 0},                // delta[q2, L] = {q3}
          {1 << 4, 0}                 // delta[q3, E] = {q4}
      };

  public boolean acceptsWord (String in) {
    for (int i = 0; i < in.length(); i++) {

      if (currentState == 1 << 4) return true; // reaching final state, quit!!!

      char c = in.charAt(i);
      int nextSS = 0; // next state set, initially empty

      for (int s = 0; s <= 3; s++) { // for each state s
        if ((currentState & (1 << s)) != 0) { // if maybe in s
          try {
            if (c == 'G' && (currentState == delta[0][0])) {
              nextSS |= delta[0][1];
            } else if (c == 'G' && (currentState == delta[0][1] || currentState == delta[1][1])) {
              nextSS |= delta[1][1];
            } else if (c == 'O' && (currentState == delta[0][1]) /*1*/) {
//              nextSS |= delta[s][0];
            } else if (c == 'L' && currentState == delta[1][1]) {
              nextSS |= delta[s][0];
            } else if (c == 'E' && currentState == delta[2][0]) {
              nextSS |= delta[s][0];

            } else if (c == 'O' && (currentState == delta[1][1])) { // reset back 1 state
              nextSS |= delta[0][1];
              currentState = nextSS;
            } else {
              nextSS |= delta[0][0];
              reset();
            }
          } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
          }
        }
      }

      if (c != 'O')
        currentState = nextSS; // new state set after c
    }

    return (currentState & (1 << 4)) != 0;
  }

  /**
   * Start checking the word, starting from state 0 and position 0.
   */
  public String checkWord (String w) {
    this.reset();
    return acceptsWord(w) ? "valid" : "not valid";
  }

}
