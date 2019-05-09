package uberjava;

import java.util.Random;
import java.lang.Integer;

public class Session {

  int sessionNumber;
  int numberOfMinutesElapsed;
  boolean started = false;

  Session() {
    Random rand = new Random();
    this.sessionNumber = rand.nextInt(Integer.SIZE - 1);
  }

}
