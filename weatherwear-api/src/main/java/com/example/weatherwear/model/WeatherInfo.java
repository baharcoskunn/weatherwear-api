package com.example.weatherwear.model;

public class WeatherInfo {
    private String description;
    private String mainWeather;
    private double feelsLikeTemp;
    private int humidity;
    private double windSpeed;

    public WeatherInfo(String mainWeather, String description, double feelsLikeTemp, int humidity, double windSpeed) {
        this.mainWeather = mainWeather;
        this.description = description;
        this.feelsLikeTemp = feelsLikeTemp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public String getDescription() {
        return description;
    }

    public double getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
