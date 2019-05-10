package uberjava.vehicle;

import uberjava.location.Location;
import uberjava.ValueUndefinedException;

public abstract class Vehicle {

    private Location currentLocation;
    private double initialCost;
    private double milesPerGallon;

    public Vehicle(Location startingLocation) {
      this.currentLocation = startingLocation;
      this.initialCost = 0.0;
      this.milesPerGallon = 0.0;
    }

    boolean driveTo(Location newLocation) {
      if (currentLocation != newLocation) {
        currentLocation = newLocation;
        return true;
      }
      return false;
    }

    double getInitialCost() throws ValueUndefinedException {
      if (initialCost == 0)
        throw new ValueUndefinedException();
      return initialCost;
    }

    double getMilesPerGallon() throws ValueUndefinedException {
      if (milesPerGallon == 0)
        throw new ValueUndefinedException();
      return milesPerGallon;
    }

}
