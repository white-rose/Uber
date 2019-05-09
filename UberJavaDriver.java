package uberjava;

import uberjava.Driver;
import uberjava.APIHelper;

public class UberJavaDriver {

  private static final String baseNextFare = "https://www.cs.usfca.edu/~dhalperin/nextFare.cgi?driver=";
  private static final String baseRideNumber = "https://www.cs.usfca.edu/~dhalperin/uberdistance.cgi?riderNumber=";
  private static final String baseReject = "https://www.cs.usfca.edu/~dhalperin/reject.cgi?rideNumber=";
  private static final String baseRating = "https://www.cs.usfca.edu/~dhalperin/rating.cgi?rideNumber=";
  private static final String baseRideDetails = "https://www.cs.usfca.edu/~dhalperin/";

  private static final String naismithDriver = "Naismith";
  private static final String prateekDriver = "Prateek";
  private static final String baeSungDriver = "Bae Sung";


    public static void main(String [] args) {

      // Get Riders

      Driver driver1 = new Driver(naismithDriver);
      Driver driver2 = new Driver(prateekDriver);
      Driver driver3 = new Driver(baeSungDriver);

      // Start session for drivers
      driver1.startSession();
      driver2.startSession();
      driver3.startSession();

      // Get Fares for Drivers

      // API call to get fare details
      // while (driver1.currentSession.numberOfMinutesElapsed < 1440) {
      while (driver1.currentSession.numberOfMinutesElapsed < 1140) {
        String fareDetails = APIHelper.get(baseNextFare + "Bae+Sung");
        fareDetails = fareDetails.replaceAll("<p>", "");
        // System.out.println(fareDetails);
        String rideNumber = fareDetails.substring(fareDetails.indexOf("#" + 1), fareDetails.indexOf("<br/")).replaceAll("#", "");

        String rideDetailsURL = fareDetails.substring(fareDetails.indexOf("\">") + 2, fareDetails.indexOf("</a>"));
        String rideDetails = APIHelper.get(rideDetailsURL).replaceAll("<br />", "");
        rideDetails = rideDetails.replaceAll("</p>", "");

        // System.out.println("Ride Number: " + rideNumber);
        String minutes = rideDetails.substring(rideDetails.indexOf("Minutes: ") + 9).replaceAll("\\s","");
        String toLocation = rideDetails.substring(rideDetails.indexOf("To: ") + 4, rideDetails.indexOf("Distance:")).replaceAll("\\s","").replaceAll("<br/>", "");
        String numberOfMilesForFare = rideDetails.substring(rideDetails.indexOf("Distance: ") + 10, rideDetails.indexOf("miles")).replaceAll("\\s","");

        driver1.numberOfFares++;
        driver1.currentSession.numberOfMinutesElapsed += Integer.valueOf(minutes);
        driver1.currentLocation = new Location(toLocation);
        driver1.totalMilesDriven += Integer.valueOf(numberOfMilesForFare);

        // System.out.println(String.format("%d minutes has elapsed for current session ", driver1.currentSession.numberOfMinutesElapsed));

        System.out.println(driver1.name + " at end of Ride#" + rideNumber + ": total minutes = " + driver1.currentSession.numberOfMinutesElapsed +"; location = " + driver1.currentLocation);
      }

      // while (driver2.currentSession.numberOfMinutesElapsed < 1140) {
      //   String fareDetails2 = APIHelper.get(baseNextFare + prateekDriver);
      //   fareDetails2 = fareDetails2.replaceAll("<p>", "");
      //   System.out.println(fareDetails2);
      //   String rideNumber2 = fareDetails2.substring(fareDetails2.indexOf("#" + 1), fareDetails2.indexOf("<br/")).replaceAll("#", "");
      //
      //   String rideDetailsURL2 = fareDetails2.substring(fareDetails2.indexOf("\">") + 2, fareDetails2.indexOf("</a>"));
      //   String rideDetails2 = APIHelper.get(rideDetailsURL2).replaceAll("<br />", "");
      //   rideDetails2 = rideDetails2.replaceAll("</p>", "");
      //
      //   System.out.println("Ride Number: " + rideNumber2);
      //   System.out.println("Ride Details: " + rideDetails2);
      //   String minutes2 = rideDetails2.substring(rideDetails2.indexOf("Minutes: ") + 9).replaceAll("\\s","");
      //   System.out.println("Minutes for fare: " + minutes2);
      //
      //   // Uberx Driver has different fare status
      //   //driver1.currentSession.numberOfMinutesElapsed += Integer.valueOf(minutes2);
      //   System.out.println(String.format("%d minutes has elapsed for current session ", driver2.currentSession.numberOfMinutesElapsed));
      // }

      // while (driver1.currentSession.numberOfMinutesElapsed < 1140) {
      //   String fareDetails = APIHelper.get(baseNextFare + "Bae+Sung");
      //   fareDetails = fareDetails.replaceAll("<p>", "");
      //   System.out.println(fareDetails);
      //   String rideNumber = fareDetails.substring(fareDetails.indexOf("#" + 1), fareDetails.indexOf("<br/")).replaceAll("#", "");
      //
      //   String rideDetailsURL = fareDetails.substring(fareDetails.indexOf("\">") + 2, fareDetails.indexOf("</a>"));
      //   String rideDetails = APIHelper.get(rideDetailsURL).replaceAll("<br />", "");
      //   rideDetails = rideDetails.replaceAll("</p>", "");
      //
      //   System.out.println("Ride Number: " + rideNumber);
      //   System.out.println("Ride Details: " + rideDetails);
      //   String minutes = rideDetails.substring(rideDetails.indexOf("Minutes: ") + 9).replaceAll("\\s","");
      //   System.out.println("Minutes for fare: " + minutes);

      //   driver1.currentSession.numberOfMinutesElapsed += Integer.valueOf(minutes);
      //   System.out.println(String.format("%d minutes has elapsed for current session ", driver1.currentSession.numberOfMinutesElapsed));
      // }
      // Get Fares for Drivers

      // System.out.println(APIHelper.get(baseNextFare + "Prateek"));
      // System.out.println(APIHelper.get(baseNextFare + "Naismith"));

      // When your last Driver ends, the main method should print for each driver the following information:
      System.out.println("Driver Statistics for last session");
      System.out.println(driver1);
      System.out.println(driver2);
      System.out.println(driver3);

    }

    public void getWebPageData() {

    }

}
