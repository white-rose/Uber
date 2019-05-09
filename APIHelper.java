package uberjava;

import java.io.*;
import java.net.*;
import java.util.*;

public class APIHelper {

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
      System.out.println(status);

      br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String readLine;
      while ((readLine = br.readLine()) != null) {
        System.out.println(readLine);
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

}
