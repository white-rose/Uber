package uberjava;

import uberjava.Driver;
import uberjava.APIHelper;
import uberjava.location.Location;
import java.util.regex.*;
import java.util.*;
import uberjava.vehicle.Car;

public class UberJavaDriver {

  private static final String baseDomain = "https://www.cs.usfca.edu/~dhalperin/";
  private static final String baseNextFare = baseDomain + "nextFare.cgi?driver=";
  private static final String baseRideNumber = baseDomain + "uberdistance.cgi?riderNumber=";
  private static final String baseReject = baseDomain + "reject.cgi?rideNumber=";
  private static final String baseRating = baseDomain + "rating.cgi?rideNumber=";

  private static final String naismithDriver = "James Naismith";
  private static final String prateekDriver = "Prateek";
  private static final String baeSungDriver = "Bae Sung";

  private static final int numberOfMinutesInSession = 1440;
  private static final int numberOfMinutesAcceptableToAccept = 300;


    public static void main(String [] args) {

      // Get Riders and their cards
      Car lambo = new Car("Urus", 2019, "Lamborghini", 1000000, 10.0);
      Driver driver1 = new Driver(baeSungDriver, false, lambo);

      // James drives a 2018 Porsche 911 Turbo S Cabriole that cost $200,400
      Car porcheCabriole = new Car("Cabriole", 2018, "Porsche", 200400, 102.0);
      Driver driver2 = new Driver(naismithDriver, true, porcheCabriole);

      // Prateek drives a 2019 Tesla Roadster that cost $219,800.
      Car teslaRoadster = new Car("Roadster", 2019, "Tesla", 219800, 11.5);
      Driver driver3 = new Driver(prateekDriver, true, teslaRoadster);

      // Start session for drivers
      driver1.startSession();
      driver2.startSession();
      driver3.startSession();

      // Get Fares for Drivers

      // Get Fares for Driver until they have driven a session of 24 hours and end up in San Francisco
      while (driver1.currentSession.numberOfMinutesElapsed < numberOfMinutesInSession && driver1.currentLocation.getName() != "San Fracisco") {
        String fareDetails = APIHelper.get(baseNextFare + "Bae+Sung").replaceAll("<p>", "");
        String rideNumber = fareDetails.substring(fareDetails.indexOf("#" + 1), fareDetails.indexOf("<br/")).replaceAll("#", "");
        String rideDetailsURL = fareDetails.substring(fareDetails.indexOf("\">") + 2, fareDetails.indexOf("</a>"));
        String rideDetails = APIHelper.get(rideDetailsURL).replaceAll("<br />", "").replaceAll("</p>", "");
        // System.out.println("Ride Number: " + rideNumber);
        String minutes = rideDetails.substring(rideDetails.indexOf("Minutes: ") + 9).replaceAll("\\s","");
        String toLocation = rideDetails.substring(rideDetails.indexOf("To: ") + 4, rideDetails.indexOf("Distance:"));
        toLocation = toLocation.replaceAll("<br/>", "").trim();
        String numberOfMilesForFare = rideDetails.substring(rideDetails.indexOf("Distance: ") + 10, rideDetails.indexOf("miles")).replaceAll("\\s","");

        // Fares can only be accepted if it takes 300 minutes or less
        if (Integer.valueOf(minutes) <= numberOfMinutesAcceptableToAccept) {

          driver1.numberOfFares++;
          driver1.currentSession.numberOfMinutesElapsed += Integer.valueOf(minutes);
          driver1.currentLocation = Location.getByName(toLocation);
          driver1.totalMilesDriven += Integer.valueOf(numberOfMilesForFare);
          driver1.minutes += Integer.valueOf(minutes);

          // ratingUrl = baseDomain + ratingUrl;
          // System.out.println("Rating URL: " + ratingUrl);

          driver1.totalNumberOfGoldStarsRecieved += getRatingForRide(fareDetails);
          driver1.totalAmountEarned += parseFare(fareDetails);
          // Driver gets %75 of profit
          // driver1.totalAmountEarned += Double.valueOf(fareEarned)*.75;
        } else {
          System.out.println(String.format("Ride# " + rideNumber + " has been rejected since it takes %s minutes", minutes));
          driver1.numberOfFaresRejected++;
          // Notify Dispatcher
        }

        System.out.println(driver1.name + " at end of Ride#" + rideNumber + ": total minutes = " + driver1.currentSession.numberOfMinutesElapsed +"; location = " + driver1.currentLocation);
      }

      // Get Fares for Drivers

      System.out.println("");

      // When your last Driver ends, the main method should print for each driver the following information:
      System.out.println("Driver Statistics for last session");
      System.out.println(driver1);
      // System.out.println(driver2);
      // System.out.println(driver3);

    }

    static int getRatingForRide(String fareDetails) {

      String urlRegex = "rating(.*)</a><br/>";
      Matcher m2 = Pattern.compile(urlRegex).matcher(fareDetails);
      final List<String> matches2 = new ArrayList<>();
      while (m2.find()) {
          matches2.add(m2.group(0));
      }

      String ratingUrl = matches2.get(0).split("\"")[0];
      ratingUrl = baseDomain + ratingUrl;

      String response = APIHelper.get(ratingUrl);
      String findStr = "golden-star";
      int lastIndex = 0;
      int ratings = 0;
      while(lastIndex != -1){
          lastIndex = response.indexOf(findStr,lastIndex);

          if(lastIndex != -1){
              ratings++;
              lastIndex += findStr.length();
          }
      }
      return ratings;
    }

    static Double parseFare(String fareResponse) {

      String dollarAmountRegex = "((-)?(\\$){1}(-)?\\d+.\\d+)";

      Pattern p = Pattern.compile(dollarAmountRegex);
      Matcher matcher = p.matcher(fareResponse);
      while (matcher.find()) {
        String dollarAmount = matcher.group();
        return Double.valueOf(dollarAmount.substring(dollarAmount.indexOf("$") + 1));
      }

      return 0.0;
    }

}
