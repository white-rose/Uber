package uberjava;

import java.util.*;
import java.lang.Math;

public class UberStatistics {

  Stack<Session> sessions = new Stack<Session>();

  UberStatistics() {

  }

  @Override
  public String toString() {
    return "Number of Rides: " + sessions.size();
  }

}
