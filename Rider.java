package uberjava;

public class Rider {

  String name;
  Double amountPaid;
  String totalHoursMinutes;
  int totalDistance;

  Rider(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Statistics for Rider " + this.name;
  }

}
