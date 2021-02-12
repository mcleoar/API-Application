import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class APIClass {

    public static void getItemInfo(String _id){
        // Create a HTTP Connection.
        String baseUrl = "https://secure.runescape.com/m=itemdb_oldschool";
        String endpoint = "/api/catalogue/detail.json?item=";
        String itemId = _id;
        //String apiKey = "";
        String urlString = baseUrl + endpoint + itemId;
        URL url;
        try {
            // Make the connection.
            url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
           // con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setRequestMethod("GET");


            // Examine the response code.
            int status = con.getResponseCode();
            if (status != 200) {
                System.out.printf("Error: Could not load item: " + status);
            } else {
                // Parsing input stream into a text string.
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Close the connections.
                in.close();
                con.disconnect();
                // Print out our complete JSON string.
                System.out.println("Output: " + content.toString());
                // Parse that object into a usable Java JSON object. Also parse that object into the objects it contains in the JSON.
                JSONObject obj = new JSONObject(content.toString());
                JSONObject item = obj.getJSONObject("item");
                JSONObject pricing = item.getJSONObject("day30");
                JSONObject current = item.getJSONObject("current");
                // Save the used variables into strings.
                String itemName = item.getString("name");
                String itemDesc = item.getString("description");
                String itemPrice = current.getString("price");
                String trend = pricing.getString("change");
                //Output the values received by the api
                System.out.println("Item Name: " + itemName);
                System.out.println(itemDesc);
                System.out.println("Current Price: " + itemPrice);
                System.out.println("Price change over 30 days " + trend);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return;
        }
    }
}