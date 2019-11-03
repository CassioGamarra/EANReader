/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eanreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author gamarra
 */
public class EANReader {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws JSONException, IOException {
        JSONObject json = readJsonFromUrl("https://api.cosmos.bluesoft.com.br/gtins/7891910000197.json");
        System.out.println(json.toString());
        System.out.println(json.get("avg_price"));
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        
        String token = "";
        URL url1 = new URL(url);
        HttpURLConnection conn =  (HttpURLConnection) url1.openConnection();
        conn.setRequestProperty("X-Cosmos-Token", token);
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");   
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
        } finally {
          conn.getInputStream().close();
        }
    }
}
