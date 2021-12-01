package dev.ricr;

public class DfaIf {

  private int currState;

  /**
   * Two ways of doing this one. If statement or switch case.
   * Edit lines 13 and 14 to swap between them.
   */
  public String checkWord (String word) {
    currState = 0;
    for (int i = 0; i < word.length(); i++) {
      checkWordsIf(word.charAt(i));
//      checkWordsSwitch(word.charAt(i));
    }

    return (currState == 4) ? "valid" : "not valid";
  }

  private void checkWordsIf (char next) {
    if (currState == 0) {
      if (next == 'G') {
        increaseState();
      }
    } else if (currState == 1) {
      if (next == 'G') {
        increaseState();
      } else if (next != 'O') {
        returnToStart();
      }
    } else if (currState == 2) {
      if (next == 'O') {
        decreaseState();
      } else if (next == 'E' || next == 'X') {
        returnToStart();
      } else if (next == 'L') {
        increaseState();
      }
    } else if (currState == 3) {
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
      case 0:
        switch (next) {
          case 'G':
            increaseState();
            break;
        }
        break;
      case 1:
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
      case 2:
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
      case 3:
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
    currState = 0;
  }


}
