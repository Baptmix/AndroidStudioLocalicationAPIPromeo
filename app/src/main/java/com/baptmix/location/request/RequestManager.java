package com.baptmix.location.request;

import android.os.StrictMode;
import android.util.Log;

import com.baptmix.location.MainActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestManager {



    private static final String USER__AGENT = "Mozilla/5.0";
    public static final JsonParser parser = new JsonParser();


    //HTTP GET request
    public static void getWeather(double latitude, double longitude) throws Exception {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=14533254e36aae2dc2f7a6a1c69d6071");


        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        Log.d("BAPTMIX", result.toString());

        Reader reader = null;
        try {
            reader = new InputStreamReader(url.openStream());
            JsonObject data = RequestManager.parser.parse(reader).getAsJsonObject();
            String ville =  data.get("name").getAsString();

            JSONArray weather_array = new JSONArray(String.valueOf(data.getAsJsonArray("weather")));
            JSONObject weather_objet = new JSONObject(weather_array.getString(0));
            String temps = weather_objet.get("main").toString();
            String icon = weather_objet.get("icon").toString();
            MainActivity.result_city.setText(ville);
            MainActivity.result_weather.setText(temps);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void getWeather(Double latitude, Double longitude) {
        /*
        https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=14533254e36aae2dc2f7a6a1c69d6071
         */
    }
}
