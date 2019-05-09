package uberjava;

public class Location {

	String locations[] = {"Death Valley", "Fresno", "Los Angeles", "Redding Sacramento", "San Bernadino", "San Diego", "San Francisco", "San Jose", "Yosemite"};

	private String name;
	private double latitude;

	Location(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
