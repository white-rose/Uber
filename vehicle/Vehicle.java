package uberjava.vehicle;

import uberjava.Location;

public abstract class Vehicle {

    String currentLocation;

    public Vehicle() {
      
    }

    public Vehicle(String startingLocation) {

    }

    public Vehicle(String name, int year, String make) {

    }

    public Vehicle(String name, int year, String make, Location location) {

    }

    boolean driveTo(Location newLocation) {
      return false;
    }

}
