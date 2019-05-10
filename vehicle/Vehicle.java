package uberjava.vehicle;

import uberjava.location.Location;

import uberjava.location.Location;

public abstract class Vehicle {

    private Location currentLocation;

    public Vehicle(Location startingLocation) {
      currentLocation = startingLocation;
    }

    boolean driveTo(Location newLocation) {
      if (currentLocation != newLocation) {
        currentLocation = newLocation;
        return true;
      }
      return false;
    }

}
