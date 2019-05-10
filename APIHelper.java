package uberjava;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class APIHelper {

  private static final String baseDomain = "https://www.cs.usfca.edu/~dhalperin/";
  private static final String baseNextFare = baseDomain + "nextFare.cgi?driver=";
  private static final String baseRideNumber = baseDomain + "uberdistance.cgi?riderNumber=";
  private static final String baseReject = baseDomain + "reject.cgi?rideNumber=";
  private static final String baseRating = baseDomain + "rating.cgi?rideNumber=";

  public static String get(String urlString) {
    URL url = null;
    try {
      url = new URL(urlString);
    } catch (MalformedURLException e) {
      System.out.println("Bad URL " + urlString + " error= " + e.getMessage());
      return null;
    }
    BufferedReader br = null;
    HttpURLConnection httpConn = null;
    try {
      URLConnection conn = url.openConnection();
      httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setReadTimeout(1000);

      int status = httpConn.getResponseCode();
      // System.out.println(status);

      br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String readLine;
      while ((readLine = br.readLine()) != null) {
        // System.out.println(readLine);
        sb.append(readLine);
      }
      return sb.toString();
    } catch (IOException e) {
      System.out.println("Cannot establish connection to " + urlString + " error= " + e.getMessage());
      e.printStackTrace();
      return null;
    } catch (IllegalStateException e) {
      System.out.println("Got Error reply on" + urlString + " error= " + e.getMessage());
      e.printStackTrace();
      return null;
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          // Do nothing here
        }
        if (httpConn != null) {
          httpConn.disconnect();
        }
      }
    }
  }

  static int getRatingForRide(String rideResponse) {

    String urlRegex = "rating(.*)</a><br/>";
    Matcher m2 = Pattern.compile(urlRegex).matcher(rideResponse);
    final List<String> matches2 = new ArrayList<>();
    while (m2.find()) {
        matches2.add(m2.group(0));
    }

    String ratingUrl = matches2.get(0).split("\"")[0];
    ratingUrl = baseDomain + ratingUrl;

    String response = APIHelper.get(ratingUrl);
    String findStr = "golden-star";
    int lastIndex = 0;
    int ratings = 0;
    while(lastIndex != -1){
        lastIndex = response.indexOf(findStr,lastIndex);

        if(lastIndex != -1){
            ratings++;
            lastIndex += findStr.length();
        }
    }
    return ratings;
  }

  static Double parseFare(String fareResponse) {
    String dollarAmountRegex = "((-)?(\\$){1}(-)?\\d+.\\d+)";
    Pattern p = Pattern.compile(dollarAmountRegex);
    Matcher matcher = p.matcher(fareResponse);
    while (matcher.find()) {
      String dollarAmount = matcher.group();
      return Double.valueOf(dollarAmount.substring(dollarAmount.indexOf("$") + 1));
    }

    return 0.0;
  }

}
