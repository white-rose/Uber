package uberjava;

public class Rider {

  String name;
  int numberOfRides;
  Double amountPaid;
  String totalHoursMinutes;
  int totalDistance;

  Rider(String name) {
    this.name = name;
    this.numberOfRides = 1;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Statistics for Rider " + this.name + "\n");
    sb.append("\t Number of Rides: " + numberOfRides + "\n");
    sb.append("\t Amount Paid: " + numberOfRides + "\n");
    sb.append("\t Hours & Minutes on Uberjava: " + numberOfRides + "\n");
    sb.append("\t Distance on Uberjava: " + numberOfRides + "\n");
    return sb.toString();
  }

}
