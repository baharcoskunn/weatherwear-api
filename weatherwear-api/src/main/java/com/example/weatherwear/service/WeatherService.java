package com.example.weatherwear.service;

import com.example.weatherwear.model.WeatherInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherInfo getWeather(String cityName) {
        String endpoint = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey + "&units=metric";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("API response code: " + responseCode);
            }

            Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonString = new StringBuilder();
            while (scanner.hasNext()) {
                jsonString.append(scanner.nextLine());
            }

            JSONObject json = new JSONObject(jsonString.toString());

            String main = json.getJSONArray("weather").getJSONObject(0).getString("main");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            double feelsLike = json.getJSONObject("main").getDouble("feels_like");
            int humidity = json.getJSONObject("main").getInt("humidity");
            double windSpeed = json.getJSONObject("wind").getDouble("speed");

            return new WeatherInfo(main, description, feelsLike, humidity, windSpeed);

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Controller'da kontrol edeceğiz
        }
    }
}
