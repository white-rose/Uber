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
      // Bae Sung drives a Lamborghini
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

      System.out.println("");

      // When your last Driver ends, the main method should print for each driver the following information:
      System.out.println("Driver Statistics for last session");
      System.out.println(driver1);
      System.out.println(driver2);
      System.out.println(driver3);

    }

}
