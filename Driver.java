package uberjava;

import uberjava.vehicle.Car;
import uberjava.UberStatistics;
import uberjava.Session;
import uberjava.UberStatistics;
import java.util.Random;

public class Driver {

    String name;
    Location currentLocation;
    boolean UberJavaXPremium;
    int drivingTime;
    int numberOfFares;
    int totalMilesDriven;
    int hours;
    int minutes;
    double totalAmountEarned;
    Car car;
    UberStatistics uberStatistics;
    Session currentSession;

    Driver(String name) {
      this.name = name;
      this.currentLocation = new Location("San Francisco");
      this.uberStatistics = new UberStatistics();
    }

    int startSession() throws IllegalStateException {
        if (this.currentSession != null)
          throw new IllegalStateException();
        this.currentSession = new Session();
        this.currentSession.started = true;
        return this.currentSession.sessionNumber;
    }

    int endSession() throws IllegalStateException {
        if (this.currentSession.started == false) {
          if (this.uberStatistics.sessions.pop() == null)
            throw new IllegalStateException();
        } else {
          this.currentSession.started = false;
          this.uberStatistics.sessions.push(this.currentSession);
        }
        return this.currentSession.sessionNumber;
    }

    int getSessionMinutes() throws IllegalStateException {
      if (this.currentSession.numberOfMinutesElapsed < 0)
        throw new IllegalStateException();
      // Returns minutes since the start of the first session
      return this.currentSession.numberOfMinutesElapsed;
    }

    UberStatistics getSessionStatistics(int sessionNumber) {
        return this.uberStatistics;
    }

    UberStatistics getSessionStatistics() {
      return this.uberStatistics;
    }

    Location getCurrentLocation() {
      return this.currentLocation;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.name + " Statistics for Session#0" + "\n");
      sb.append("Number of Rides: " + this.numberOfFares + "\n");
      sb.append("Rides rejected: \n");
      sb.append("Total Hours & Minutes: " + this.hours + ":" + this.minutes + "\n");
      sb.append("Total Miles: " + this.totalMilesDriven + "\n");
      sb.append("Average Rating: \n");
      sb.append("Total $ Paid: \n");
      sb.append("Net Amount Earned: \n");
      sb.append("Operation Costs: \n");
      sb.append("Effectively Hourly Rate: \n");

      return sb.toString();
    }

}
