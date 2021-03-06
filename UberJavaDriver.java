package uberjava;

import uberjava.Driver;
import uberjava.APIHelper;
import uberjava.location.Location;
import uberjava.Rider;
import uberjava.vehicle.Car;
import java.util.regex.*;
import java.util.*;

public class UberJavaDriver {

  private static final String naismithDriver = "James+Naismith";
  private static final String prateekDriver = "Prateek";
  private static final String baeSungDriver = "Bae+Sung";

    public static void main(String [] args) {

      // Get Riders and their cards
      // Bae Sung drives a Lamborghini
      Car lambo = new Car("Camaro", 2019, "Chevrolet", 40000, 10.0);
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

      System.out.println("\n");
      System.out.println("Rider Statistics");
      for (Map.Entry<String,Rider> rider : driver1.riders.entrySet())
            System.out.println(rider.getValue());
      for (Map.Entry<String,Rider> rider : driver2.riders.entrySet())
                  System.out.println(rider.getValue());
      for (Map.Entry<String,Rider> rider : driver3.riders.entrySet())
            System.out.println(rider.getValue());

    }

}
