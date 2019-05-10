package uberjava;

import uberjava.vehicle.Car;
import uberjava.UberStatistics;
import uberjava.Session;
import uberjava.UberStatistics;
import uberjava.location.Location;
import java.util.Random;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class Driver {

    String name;
    boolean UberJavaXPremium;
    int drivingTime;
    int numberOfFares;
    int numberOfFaresRejected;
    int totalMilesDriven;
    int hours;
    int minutes;
    int totalNumberOfGoldStarsRecieved;
    double totalAmountEarned;
    double operationCosts;
    Location currentLocation;
    Car car;
    UberStatistics uberStatistics;
    Session currentSession;

    Driver(String name, boolean isPremiumDriver, Car car) {
      this.name = name;
      this.currentLocation = Location.getByName("San Francisco");
      this.uberStatistics = new UberStatistics();
      this.UberJavaXPremium = isPremiumDriver;
      this.car = car;
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
      DecimalFormat df2 = new DecimalFormat("#.##");
      StringBuilder sb = new StringBuilder();
      if (UberJavaXPremium) {
        sb.append(this.name + "[UberXPremium] Statistics for Session#0" + "\n");
      } else {
        sb.append(this.name + "[Not UberXPremium] Statistics for Session#0" + "\n");
      }
      sb.append("\t Number of Rides: " + this.numberOfFares + "\n");
      sb.append("\t Rides rejected: " + this.numberOfFaresRejected + "\n");
      sb.append("\t Total Hours & Minutes: " + this.minutes/60 + ":" + this.minutes%60 + "\n");
      sb.append("\t Total Miles: " + this.totalMilesDriven + "\n");
      int averageRating = this.totalNumberOfGoldStarsRecieved / this.numberOfFares;
      sb.append("\t Average Rating: " + averageRating + "\n");
      sb.append("\t Total $ Paid: $" + df2.format(totalAmountEarned) + "\n");
      sb.append("\t Net Amount Earned: $" + df2.format((totalAmountEarned*.75)) + "\n");
      sb.append("\t Operation Costs: \n");
      sb.append("\t\t Ownership: \n");
      sb.append("\t\t Operating: \n");
      sb.append("\t\t Gas: \n");
      sb.append("\t\t Tolls: \n");
      sb.append("\t\t Amenities: \n");
      sb.append("\t\t Total Cost of Operation: \n");
      sb.append("\t Effectively Hourly Rate: \n");

      return sb.toString();
    }

}
