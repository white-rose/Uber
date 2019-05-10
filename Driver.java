package uberjava;

import uberjava.vehicle.Car;
import uberjava.UberStatistics;
import uberjava.Session;
import uberjava.UberStatistics;
import uberjava.location.Location;
import uberjava.Rider;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.*;
import java.util.Random;
import java.util.*;

public class Driver {

    String name;
    boolean UberJavaXPremium;
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
    Map<String, Rider> riders = new HashMap<String, Rider>();

    private static final String baseDomain = "https://www.cs.usfca.edu/~dhalperin/";
    private static final String baseNextFare = baseDomain + "nextFare.cgi?driver=";
    private static final String baseRideNumber = baseDomain + "uberdistance.cgi?riderNumber=";
    private static final String baseReject = baseDomain + "reject.cgi?rideNumber=";
    private static final String baseRating = baseDomain + "rating.cgi?rideNumber=";

    private static final int numberOfMinutesInSession = 1440;
    private static final int numberOfMinutesAcceptableToAccept = 300;

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

      while (this.currentSession.numberOfMinutesElapsed < numberOfMinutesInSession && this.currentLocation.getName() != "San Fracisco") {
        getNextFare();
      }

      endSession();

      return this.currentSession.sessionNumber;

      // End Session Code here

    }

    private void getNextFare() {

      // Call API to get next fare
      String fareDetailsResponse = APIHelper.get(baseNextFare + this.name).replaceAll("<p>", "");
      String rideDetailsURL = fareDetailsResponse.substring(fareDetailsResponse.indexOf("\">") + 2, fareDetailsResponse.indexOf("</a>"));
      String rideNumber = fareDetailsResponse.substring(fareDetailsResponse.indexOf("#" + 1), fareDetailsResponse.indexOf("<br/")).replaceAll("#", "");

      // Get RiderDetails
      parseRiderAndAdd(fareDetailsResponse);

      // Call API to get more details for ride number
      String rideDetails = APIHelper.get(rideDetailsURL).replaceAll("<br />", "").replaceAll("</p>", "");
      String minutesParsed = APIHelper.parseMinutes(rideDetails);
      String minutes = minutesParsed.replaceAll("Minutes: ", "");
      // String minutes = rideDetails.substring(rideDetails.indexOf("Minutes: ") + 9).replaceAll("\\s","");
      String toLocation = rideDetails.substring(rideDetails.indexOf("To: ") + 4, rideDetails.indexOf("Distance:"));
      toLocation = toLocation.replaceAll("<br/>", "").trim();
      String numberOfMilesForFare = rideDetails.substring(rideDetails.indexOf("Distance: ") + 10, rideDetails.indexOf("miles")).replaceAll("\\s","");

      // Fares can only be accepted if it takes 300 minutes or less
      if (Integer.valueOf(minutes) <= numberOfMinutesAcceptableToAccept) {

        this.numberOfFares++;
        currentSession.numberOfMinutesElapsed += Integer.valueOf(minutes);
        currentLocation = Location.getByName(toLocation);
        totalMilesDriven += Integer.valueOf(numberOfMilesForFare);
        minutes += Integer.valueOf(minutes);
        totalNumberOfGoldStarsRecieved += APIHelper.getRatingForRide(fareDetailsResponse);
        totalAmountEarned += APIHelper.parseFare(fareDetailsResponse);

        System.out.println(name + " at end of Ride#" + rideNumber + ": total minutes = " + currentSession.numberOfMinutesElapsed +"; location = " + currentLocation);

      } else {
        // System.out.println(String.format("Ride# " + rideNumber + " has been rejected since it takes %s minutes", minutes));
        numberOfFaresRejected++;
        // Notify Dispatcher of rejected ride;
      }
    }

    private void parseRiderAndAdd(String fareDetailsResponse) {
      String riderName = APIHelper.parseRider(fareDetailsResponse).replaceAll("Rider: ", "").replaceAll("</b>", "");
      Rider rider = new Rider(riderName);

      if (this.riders.containsKey(rider.name)) {
        Rider riderFound = this.riders.get(rider.name);
        riderFound.numberOfRides++;
        this.riders.put(rider.name, riderFound);
      } else {
        rider.numberOfRides++;
      }

      riders.put(riderName, rider);
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
      sb.append("\t Total Hours & Minutes: " + this.currentSession.numberOfMinutesElapsed/60 + ":" + this.currentSession.numberOfMinutesElapsed%60 + "\n");
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
      // sb.append("\t Current Location: " + this.currentLocation + "\n");

      return sb.toString();
    }

}
