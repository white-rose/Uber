package uberjava.vehicle;

import uberjava.vehicle.Vehicle;
import uberjava.location.Location;
import java.util.ArrayList;
import java.util.List;

public class Car extends Vehicle {

  private String name;
  private String make;
  private int year;
  private int purchaseCost;
  private double milesPerGallon;
  private boolean parked;
  private static List<Car> myCars = new ArrayList<>();

  public Car(String name, int year, String make, int purchaseCost, double milesPerGallon) {
    super(Location.getByName("San Fracisco"));
    this.name = name;
    this.year = year;
    this.make = make;
    this.parked = true;
    this.purchaseCost = purchaseCost;
    this.milesPerGallon = milesPerGallon;
  }


}
