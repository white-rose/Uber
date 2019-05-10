package uberjava;

public class Rider {

  String name;
  String totalHoursMinutes;
  int numberOfRides;
  int totalDistance;
  Double amountPaid;

  Rider(String name) {
    this.name = name;
    this.numberOfRides = 1;
    this.totalDistance = 0;
    this.amountPaid = 0.0;
    this.totalHoursMinutes = "";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Statistics for Rider " + this.name + "\n");
    sb.append("\t Number of Rides: " + numberOfRides + "\n");
    sb.append("\t Amount Paid: " + amountPaid + "\n");
    sb.append("\t Hours & Minutes on Uberjava: " + totalHoursMinutes + "\n");
    sb.append("\t Distance on Uberjava: " + totalDistance + "\n");
    return sb.toString();
  }

}
